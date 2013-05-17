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
import hes.kundeMgmt.AdressTyp;
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

public class HESStarter {
	
	private static final String HES_NAME = "HES1";
	private static final long PING_WARTZEIT_IN_MILLISEKUNDEN = 2000;
	private static final String MONITOR_NAME = "monitor";
	private static final String MONITOR_SERVER = "localhost";

	public static void main(String[] args) {
		
		IHESAWKFassade fassade = startup(HES_NAME, PING_WARTZEIT_IN_MILLISEKUNDEN, MONITOR_NAME, MONITOR_SERVER);
		
		System.out.println("HES-Server steht nun bereit Client-Anfragen entgegen zu nehmen...");
		
		//Simpler Testaufruf:
//		String name = "Max Mustermann";
//		AdressTyp adresse = new AdressTyp("Musterweg", "42a", "12345", "Beispielstadt");
//		
//		System.err.println("Neuer Kunde, Id:" + fassade.legeKundeAn(name, adresse));

	}
	
	public static IHESAWKFassade startup(String hesName, long pingWarteZeitInMillisekunden, String monitorName, String monitorServer) {
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
		//Wenn einkommentiert, loescht dieser Befehl
		//alle bestehenden Tabellen und erstellt neue
		//aus den Annotations
		SchemaExport schemaExport = new SchemaExport(config);
		schemaExport.create(true, true);
		
		SessionFactory sessionFactory = config.buildSessionFactory();
		
		//Dependency Injection:
		IAuftragMgmt auftragMgmt = new AuftragMgmtFassade();
		IKundeMgmt kundeMgmt = new KundeMgmtFassade();
		IRechnungMgmt rechnungMgmt = new RechnungMgmtFassade();
		IProduktMgmt produktMgmt = new ProduktMgmtFassade();
		ILieferungMgmt lieferungMgmt = new LieferungMgmtFassade();
		IHESAWKFassade fassade = new HESAWKFassadeImpl(auftragMgmt, kundeMgmt,
				rechnungMgmt, produktMgmt, lieferungMgmt, sessionFactory);
		HESStatusReporter statusReporter = new HESStatusReporter(monitorServer, monitorName, pingWarteZeitInMillisekunden);
		
		try {
			IHESRemoteAWKFassadeServer fassadeServer = new HESRemoteAWKFassadeServer(fassade, statusReporter, hesName);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		//Beispielaufruf
		//Evtl den Namen spaeter als Kommandokonsolenparameter uebergeben
		
		return fassade;
	}

}
