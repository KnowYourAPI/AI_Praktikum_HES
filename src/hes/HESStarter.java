package hes;

import java.io.IOException;
import java.rmi.RemoteException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import processing.core.PApplet;

import hes.auftragMgmt.Angebot;
import hes.auftragMgmt.Auftrag;
import hes.auftragMgmt.AuftragMgmtFassade;
import hes.auftragMgmt.IAuftragMgmt;
import hes.client.gui.DashboardGUI;
import hes.fassade.HESAWKFassadeImpl;
import hes.fassade.HESRemoteAWKFassadeServer;
import hes.fassade.HESStatusReporter;
import hes.fassade.IHESAWKFassade;
import hes.fassade.IHESRemoteAWKFassadeServer;
import hes.kundeMgmt.Adresse;
import hes.kundeMgmt.IKundeMgmt;
import hes.kundeMgmt.Kunde;
import hes.kundeMgmt.KundeMgmtFassade;
import hes.lieferungMgmt.ILieferungMgmt;
import hes.lieferungMgmt.Lieferung;
import hes.lieferungMgmt.LieferungMgmtFassade;
import hes.produktMgmt.IProduktMgmt;
import hes.produktMgmt.Produkt;
import hes.produktMgmt.ProduktMgmtFassade;
import hes.produktMgmt.Warenausgangsmeldung;
import hes.rechnungMgmt.IRechnungMgmt;
import hes.rechnungMgmt.Rechnung;
import hes.rechnungMgmt.RechnungMgmtFassade;
import hes.rechnungMgmt.Zahlungseingang;
import hes.redundanzMgmt.IRedundanzMgmt;
import hes.redundanzMgmt.RedundanzMgmtFassade;
import hes.transportsystemAdapter.ITransportSystemAdapter;
import hes.transportsystemAdapter.TransportsystemAdapterImpl;
import hes.zahlungseingangAdapter.IZahlungseingangAdapter;
import hes.zahlungseingangAdapter.ZahlungseingangAdapterImpl;

public class HESStarter {
	
	private static final long PING_WARTZEIT_IN_MILLISEKUNDEN = 2000;
	private static final String REDUNDANZ_MGMT_NAME = "redundanzmgmt";
	private static final String REDUNDANZ_MGMT_SERVER = "localhost";
	private static final String RESTLET_HOST = "localhost";
	private static final int RESTLET_PORT = 8183;
		
	public static void main(String[] args) throws IOException {

		//Dispatcher etc starten:
		IRedundanzMgmt redundanzMgmt = new RedundanzMgmtFassade(REDUNDANZ_MGMT_NAME);
		System.out.println("RedundanzMgmt-Komponente online und an den Namensdienst gebunden");
		
		//DashboardGUI starten und registrieren
		DashboardGUI dashboardGUI = new DashboardGUI();
		PApplet.runSketch(new String[]{"Dashboard"}, dashboardGUI);
		
		redundanzMgmt.addDispatcherObserver(dashboardGUI);
		redundanzMgmt.addMonitorObserver(dashboardGUI);
		
		
		//HE-Systeme starten:
		startup("HES1", PING_WARTZEIT_IN_MILLISEKUNDEN, true);
		startup("HES2", PING_WARTZEIT_IN_MILLISEKUNDEN, false);
		startup("HES3", PING_WARTZEIT_IN_MILLISEKUNDEN, false);

		IZahlungseingangAdapter zahlungseingangsAdapter = new ZahlungseingangAdapterImpl(redundanzMgmt);
		zahlungseingangsAdapter.startup("hapsar-queue", "localhost");
		
	}
	
	public static IHESRemoteAWKFassadeServer startup(String hesName, long pingWarteZeitInMillisekunden, boolean setupHibernate) throws RemoteException {
		/**
		 * HES Startup:
		 * 1. Hibernate einrichten
		 * 2. Dependency Injection
		 * 3. Loslegen
		 * */
		
		//Hibernate Setup:
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.addAnnotatedClass(Adresse.class);
		config.addAnnotatedClass(Kunde.class);
		config.addAnnotatedClass(Angebot.class);
		config.addAnnotatedClass(Auftrag.class);
		config.addAnnotatedClass(Produkt.class);
		config.addAnnotatedClass(Lieferung.class);
		config.addAnnotatedClass(Warenausgangsmeldung.class);
		config.addAnnotatedClass(Rechnung.class);
		config.addAnnotatedClass(Zahlungseingang.class);
		config.configure("hibernate.cfg.xml");
		
		//Wenn per flag aktiviert, loescht dieser Befehl
		//alle bestehenden Tabellen und erstellt neue
		//aus den Annotations
		if(setupHibernate) {
			SchemaExport schemaExport = new SchemaExport(config);
			schemaExport.create(true, true);
		}
		
		SessionFactory sessionFactory = config.buildSessionFactory();
		
		//Dependency Injection:
		IAuftragMgmt auftragMgmt = new AuftragMgmtFassade();
		IKundeMgmt kundeMgmt = new KundeMgmtFassade();
		IRechnungMgmt rechnungMgmt = new RechnungMgmtFassade();
		IProduktMgmt produktMgmt = new ProduktMgmtFassade();
		ITransportSystemAdapter transportSystemAdapter = new TransportsystemAdapterImpl(RESTLET_HOST, RESTLET_PORT);
		ILieferungMgmt lieferungMgmt = new LieferungMgmtFassade(transportSystemAdapter);
		IHESAWKFassade fassade = new HESAWKFassadeImpl(auftragMgmt, kundeMgmt,
				rechnungMgmt, produktMgmt, lieferungMgmt, sessionFactory);
		HESStatusReporter statusReporter = new HESStatusReporter(hesName, REDUNDANZ_MGMT_SERVER, REDUNDANZ_MGMT_NAME, PING_WARTZEIT_IN_MILLISEKUNDEN);
		
		IHESRemoteAWKFassadeServer fassadeServer = new HESRemoteAWKFassadeServer(fassade, statusReporter, hesName);
		System.err.println(hesName + " steht nun bereit Client-Anfragen entgegen zu nehmen...");
		return fassadeServer;
	}
}
