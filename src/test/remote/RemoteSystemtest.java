package test.remote;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.List;

import hes.auftragMgmt.Angebot;
import hes.auftragMgmt.Auftrag;
import hes.auftragMgmt.AuftragMgmtFassade;
import hes.auftragMgmt.AuftragTyp;
import hes.auftragMgmt.IAuftragMgmt;
import hes.fassade.HESAWKFassadeImpl;
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
import hes.transporteingangAdapter.ITransporteingangAdapter;
import hes.transporteingangAdapter.TransporteingangAdapterImpl;
import hes.zahlungseingangAdapter.IZahlungseingangAdapter;
import hes.zahlungseingangAdapter.ZahlungseingangAdapterImpl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Before;
import org.junit.Test;

public class RemoteSystemtest {
	
	private static final String URL_PREFIX = "rmi://";
	private static final String REDUNDANZ_MGMT_SERVER = "localhost";
	private static final String REDUNDANZ_MGMT_NAME = "redundanzmgmt";
	
	//Hibernate
	SessionFactory sessionFactory;
	
	//HES-Komponenten:
	private IHESRemoteAWKFassadeServer hesFassade;
	
	//Testdaten:
	//Testkunde
	private String testKundeName;
	private String testKundeStrasse;
	private String testKundeHausnummer;
	private String testKundePostleitzahl;
	private String testKundeOrtsname;
	private AdressTyp testKundeAdresse;
	private int testKundeId;
	
	//Testprodukte
	private int testProdukt1Id, testProdukt2Id;
	private String testProdukt1Name, testProdukt2Name;
	private int testProdukt1Lagerbestand, testProdukt2Lagerbestand;
	private float testProdukt1Preis, testProdukt2Preis;
	
	@Before
	public void setup() throws MalformedURLException, RemoteException, NotBoundException {
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
//		schemaExport.create(true, true);
		
		sessionFactory = config.buildSessionFactory();
		
		//Dependency Injection:
		String redundanzMgmtUrl = URL_PREFIX + REDUNDANZ_MGMT_SERVER + "/" + REDUNDANZ_MGMT_NAME;
		hesFassade = (IHESRemoteAWKFassadeServer) Naming.lookup(redundanzMgmtUrl);

		testKundeStrasse = "Musterweg";
		testKundeHausnummer = "42a";
		testKundePostleitzahl = "12345";
		testKundeOrtsname = "Beispielstadt";

		testKundeName = "TestKunde";
		testKundeAdresse = new AdressTyp(testKundeStrasse, testKundeHausnummer, testKundePostleitzahl, testKundeOrtsname);
		testKundeId = hesFassade.legeKundeAn(testKundeName, testKundeAdresse);
		
		testProdukt1Name = "TestProdukt1";
		testProdukt2Name = "TestProdukt2";
		testProdukt1Lagerbestand = 10;
		testProdukt2Lagerbestand = 20;
		testProdukt1Preis = 50;
		testProdukt2Preis = 100;
		
		testProdukt1Id = hesFassade.legeProduktAn(testProdukt1Name, testProdukt1Lagerbestand, testProdukt1Preis);
		testProdukt2Id = hesFassade.legeProduktAn(testProdukt2Name, testProdukt2Lagerbestand, testProdukt2Preis);
		
	}

	@Test
	public void testLastenheftszenario() throws RemoteException {
		/**
		 * Szenario, Punkte 1 & 2
		 *  Der Callcenter-Agent erstellt ein Angebot: 
		 * */
		
		int angebotId = hesFassade.erstelleAngebot(testKundeId);
		
		//Produkte zum Angebot hinzufuegen
		int produkt1Menge = 10;
		int produkt2Menge = 20;
		float auftragGesamtpreis = produkt1Menge * testProdukt1Preis
								 + produkt2Menge * testProdukt2Preis;
		
		hesFassade.fuegeProduktZuAngebotHinzu(angebotId, testProdukt1Id, produkt1Menge);
		hesFassade.fuegeProduktZuAngebotHinzu(angebotId, testProdukt2Id, produkt2Menge);
		
		/**
		 * Szenario Punkte 3 & 4:
		 * Der Callcenter-Agent erstellt aus dem Angebot einen Auftrag
		 * */
		
		AuftragTyp auftragTyp = hesFassade.erstelleAuftrag(angebotId);
		
		/**
		 * Szenario Punkt 5a
		 * (Lagerbestand ist vorerst immer ausreichend)
		 * 
		 * 5a I.   Erzeugung von Warenausgangsmeldungen (Wird erst in v2 implementiert)
		 * 5a II.  Erstellung von Lieferung und Transportauftrag 
		 * 5a III. Anlegen einer Rechnung
		 * */
		
		//5a II: Pruefen ob wirklich eine dazugehoerige Lieferung angelegt wird
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Lieferung l where l.auftrag.auftragId = :auftragId");
		query.setParameter("auftragId", auftragTyp.getAuftragId());
		List<?> queryResults = query.list();
		
		assertFalse(queryResults.isEmpty());
		assertFalse(queryResults.size() > 1);
		
		Lieferung lieferung = (Lieferung)queryResults.get(0);
		int lieferungNr = lieferung.getLieferungId();
		
		//5a III: Pruefen ob wirklich eine dazugehoerige Rechnung angelegt wird
		query = session.createQuery("from Rechnung r where r.auftrag.auftragId = :auftragId");
		query.setParameter("auftragId", auftragTyp.getAuftragId());
		queryResults = query.list();
		session.getTransaction().commit();
		
		assertFalse(queryResults.isEmpty());
		assertFalse(queryResults.size() > 1);
		
		Rechnung rechnung = (Rechnung)queryResults.get(0);
		int rechnungNr = rechnung.getRechnungId();
		
		/**
		 * Szenario Punkt 6:
		 * Der Auftrag wird ausgeliefert.
		 * Der Versand markiert die Lieferung als erfolgt.
		 * */
		
		assertFalse(lieferung.isLieferungErfolgt());
		
		/**
		 * Szenario Punkt 7:
		 * Der Kunde Begleicht die Rechnung.
		 * Das HES erhaelt einen Zahlungseingang fuer die Rechnung
		 * */
		
		assertFalse(rechnung.isIstBezahlt());
			
	}

}
