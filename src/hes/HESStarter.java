package hes;


import java.rmi.RemoteException;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import hes.auftragMgmt.Angebot;
import hes.auftragMgmt.Auftrag;
import hes.auftragMgmt.AuftragMgmtFassade;
import hes.auftragMgmt.IAuftragMgmt;
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
import hes.redundanzMgmt.RedundanzMgmtFassade;

public class HESStarter {
	
	private static final long PING_WARTZEIT_IN_MILLISEKUNDEN = 10000;
	private static final String REDUNDANZ_MGMT_NAME = "redundanzmgmt";
	private static final String REDUNDANZ_MGMT_SERVER = "localhost";

	public static void main(String[] args) throws RemoteException {

		//Dispatcher etc starten:
		new RedundanzMgmtFassade(REDUNDANZ_MGMT_NAME);
		System.out.println("RedundanzMgmt-Komponente online und an den Namensdienst gebunden");
		
		//2 HE-Systeme starten:
		startup("HES1", PING_WARTZEIT_IN_MILLISEKUNDEN, true);
		startup("HES2", PING_WARTZEIT_IN_MILLISEKUNDEN, false);

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
		ILieferungMgmt lieferungMgmt = new LieferungMgmtFassade();
		IHESAWKFassade fassade = new HESAWKFassadeImpl(auftragMgmt, kundeMgmt,
				rechnungMgmt, produktMgmt, lieferungMgmt, sessionFactory);
		HESStatusReporter statusReporter = new HESStatusReporter(hesName, REDUNDANZ_MGMT_SERVER, REDUNDANZ_MGMT_NAME, PING_WARTZEIT_IN_MILLISEKUNDEN);
		
		IHESRemoteAWKFassadeServer fassadeServer = new HESRemoteAWKFassadeServer(fassade, statusReporter, hesName);
		System.err.println(hesName + " steht nun bereit Client-Anfragen entgegen zu nehmen...");
		return fassadeServer;
	}

}
