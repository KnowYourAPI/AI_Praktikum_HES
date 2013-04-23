package test.unit;

import static org.junit.Assert.*;
import hes.auftragMgmt.Angebot;
import hes.auftragMgmt.Auftrag;
import hes.auftragMgmt.AuftragMgmtFassade;
import hes.auftragMgmt.IAuftragMgmt;
import hes.kundeMgmt.AdressTyp;
import hes.kundeMgmt.Adresse;
import hes.kundeMgmt.IKundeMgmt;
import hes.kundeMgmt.Kunde;
import hes.kundeMgmt.KundeMgmtFassade;
import hes.lieferungMgmt.Lieferung;
import hes.produktMgmt.IProduktMgmt;
import hes.produktMgmt.Produkt;
import hes.produktMgmt.ProduktMgmtFassade;
import hes.produktMgmt.Warenausgangsmeldung;
import hes.rechnungMgmt.IRechnungMgmt;
import hes.rechnungMgmt.Rechnung;
import hes.rechnungMgmt.RechnungMgmtFassade;
import hes.rechnungMgmt.Zahlungseingang;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RechnungMgmtTest {
	
	private SessionFactory sessionFactory;
	private SchemaExport schemaExport;
	
	//Komponenten
	private IRechnungMgmt rechnungMgmt;
	private IAuftragMgmt auftragMgmt;
	private IKundeMgmt kundeMgmt;
	private IProduktMgmt produktMgmt;
	
	//Testdaten:
	//Testkunde
	String name;
	String strasse;
	String hausnummer;
	String postleitzahl;
	String ortsname;
	AdressTyp adresse;
	int kundeId;
	Kunde kunde;
	
	//Produkte:
	String produktName1, produktName2;
	int lagerbestand1, lagerbestand2;
	float preis1, preis2;
	Produkt produkt1, produkt2;
	
	//Testangebot:
	Angebot angebot;
	int produkt1Menge, produkt2Menge;
	float angebotGesamtpreis;
	
	//Testauftrag:
	Auftrag auftrag;
	
	@Before
	public void setup() {
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
		schemaExport = new SchemaExport(config);
		schemaExport.create(true, true);
		
		sessionFactory = config.buildSessionFactory();
		rechnungMgmt = new RechnungMgmtFassade();
		auftragMgmt = new AuftragMgmtFassade();
		kundeMgmt = new KundeMgmtFassade();
		produktMgmt = new ProduktMgmtFassade();
		
		//Notwendige Testdaten erzeugen
		//Den Testkunden erstellen:
		name = "Testkunde";
		strasse = "Musterweg";
		hausnummer = "42a";
		postleitzahl = "12345";
		ortsname = "Beispielstadt";
		adresse = new AdressTyp(strasse, hausnummer, postleitzahl, ortsname);
		
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		kundeId = kundeMgmt.erstelleKunde(name, adresse, session);
		kunde = kundeMgmt.getKunde(kundeId, session);
		
		//Produkte erstellen:
		produktName1 = "TestProdukt1";
		produktName2 = "TestProdukt2";
		lagerbestand1 = 50;
		lagerbestand2 = 100;
		preis1 = 50;
		preis2 = 100;
		produkt1 = produktMgmt.legeProduktAn(produktName1, lagerbestand1, preis1, session);
		produkt2 = produktMgmt.legeProduktAn(produktName2, lagerbestand2, preis2, session);
		
		//Angebot erstellen:
		angebot = auftragMgmt.erstelleAngebot(kunde, session);
		
		produkt1Menge = 2; //2 * 50€
		produkt2Menge = 1; //1 * 100€
		
		auftragMgmt.fuegeProduktZuAngebotHinzu(angebot, produkt1, produkt1Menge, session);
		auftragMgmt.fuegeProduktZuAngebotHinzu(angebot, produkt2, produkt2Menge, session);
		
		angebotGesamtpreis = angebot.getGesamtPreis();
		
		//Auftrag erstellen:
		auftrag = auftragMgmt.erstelleAuftrag(angebot, session);
		session.getTransaction().commit();
	}

	@Test
	public void testLegeRechnungAn() {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		//Erstelle und teste Rechnung 1:
		rechnungMgmt.legeRechnungAn(auftrag, session);
		
		int rechnungId1 = 1;
		Rechnung rechnung1 = (Rechnung) session.get(Rechnung.class, rechnungId1);
		
		assertTrue(rechnung1.getRechnungId() == rechnungId1);
		assertFalse(rechnung1.isIstBezahlt());
		assertTrue(rechnung1.getZahlungseingaenge().isEmpty());
		
		//Erstelle und teste Rechnung 2:
		rechnungMgmt.legeRechnungAn(null, session);
		
		int rechnungId2 = 2;
		Rechnung rechnung2 = (Rechnung) session.get(Rechnung.class, rechnungId2);
		session.getTransaction().commit();
		
		assertTrue(rechnung2.getRechnungId() == rechnungId2);
		assertFalse(rechnung2.isIstBezahlt());
		assertTrue(rechnung2.getZahlungseingaenge().isEmpty());
	}
	
	@Test
	public void testMeldeZahlungsEingang(){
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		//Erstelle Rechnung:
		Rechnung rechnung = rechnungMgmt.legeRechnungAn(auftrag, session);
		
		//Teste meldeZahlungseingang()
		rechnungMgmt.meldeZahlungsEingang(rechnung.getRechnungId(), angebotGesamtpreis, session);
		
		assertTrue(rechnung.isIstBezahlt());
		session.getTransaction().commit();
	}
	
	@Test
	public void testGetAuftragId() {
		fail("Not yet implemented");
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		session.getTransaction().commit();
	}
	
	@After
	public void teardown() {
		schemaExport.drop(true, true);		
	}

}