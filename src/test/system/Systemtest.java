package test.system;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import hes.auftragMgmt.Angebot;
import hes.auftragMgmt.Auftrag;
import hes.auftragMgmt.AuftragMgmtFassade;
import hes.auftragMgmt.AuftragTyp;
import hes.auftragMgmt.IAuftragMgmt;
import hes.fassade.HESAWKFassadeImpl;
import hes.fassade.IHESAWKFassade;
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
import hes.transportsystemAdapter.ITransportSystemAdapter;
import hes.transportsystemAdapter.TransportsystemAdapterImpl;
import hes.zahlungseingangAdapter.IZahlungseingangAdapter;
import hes.zahlungseingangAdapter.ZahlungseingangAdapterImpl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Before;
import org.junit.Test;

public class Systemtest {
	
	//Hibernate
	SessionFactory sessionFactory;
	
	//HES-Komponenten:
	private IHESAWKFassade hesFassade;
	private IAuftragMgmt auftragMgmt;
	private IProduktMgmt produktMgmt;
	private IKundeMgmt kundeMgmt;
	private ILieferungMgmt lieferungMgmt;
	private IRechnungMgmt rechnungMgmt;
	private ITransportSystemAdapter transporteingangFassade;
	private IZahlungseingangAdapter zahlungseingangFassade;
	
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
	public void setup() {
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
		
		sessionFactory = config.buildSessionFactory();
		
		//Dependency Injection:
		auftragMgmt = new AuftragMgmtFassade();
		kundeMgmt = new KundeMgmtFassade();
		rechnungMgmt = new RechnungMgmtFassade();
		produktMgmt = new ProduktMgmtFassade();
		lieferungMgmt = new LieferungMgmtFassade();
		hesFassade = new HESAWKFassadeImpl(auftragMgmt, kundeMgmt,
				rechnungMgmt, produktMgmt, lieferungMgmt, sessionFactory);
		transporteingangFassade = new TransportsystemAdapterImpl(hesFassade);
		zahlungseingangFassade = new ZahlungseingangAdapterImpl(hesFassade);

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
	public void testLastenheftszenario() {
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
		
		//Dieser Aufruf findet im 'richtigen' Einsatz dann
		//ueber das System des TP-Dienstleisters statt
		transporteingangFassade.markiereLieferungAlsErfolgt(lieferungNr);
		
		session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		lieferung = (Lieferung)session.get(Lieferung.class, rechnungNr);
		session.getTransaction();
		
		assertTrue(lieferung.isLieferungErfolgt());
		
		/**
		 * Szenario Punkt 7:
		 * Der Kunde Begleicht die Rechnung.
		 * Das HES erhaelt einen Zahlungseingang fuer die Rechnung
		 * */
		
		assertFalse(rechnung.isIstBezahlt());
		
		float haelfteGesamtpreis = auftragGesamtpreis/2;
		
		zahlungseingangFassade.meldeZahlungsEingang(rechnungNr, haelfteGesamtpreis);
		
		assertFalse(rechnung.isIstBezahlt());

		zahlungseingangFassade.meldeZahlungsEingang(rechnungNr, auftragGesamtpreis);
		
		session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		rechnung = (Rechnung)session.get(Rechnung.class, rechnungNr);
		
		assertTrue(rechnung.isIstBezahlt());
		
		/**
		 * Szenario Punkt 8:
		 * Der Auftrag wird durch die Buchhaltung als abgeschlossen markiert
		 * */
		
		session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Auftrag auftrag = auftragMgmt.getAuftrag(auftragTyp.getAuftragId(), session);
		session.getTransaction().commit();
		
		assertTrue(auftrag.isIstAbgeschlossen());
			
	}

}
