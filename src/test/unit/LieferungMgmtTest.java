package test.unit;

import static org.junit.Assert.*;

import java.util.Date;

import hes.auftragMgmt.Angebot;
import hes.auftragMgmt.Auftrag;
import hes.auftragMgmt.AuftragMgmtFassade;
import hes.auftragMgmt.IAuftragMgmt;
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
import hes.rechnungMgmt.Rechnung;
import hes.rechnungMgmt.Zahlungseingang;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Before;
import org.junit.Test;

public class LieferungMgmtTest {
	
	private SessionFactory sessionFactory;
	private SchemaExport schemaExport;
	
	//Komponenten
	private ILieferungMgmt lieferungMgmt;
	private IAuftragMgmt auftragMgmt;
	private IKundeMgmt kundeMgmt;
	private IProduktMgmt produktMgmt;
	
	//Testdaten:
	//Testkunde
	private String name;
	private String strasse;
	private String hausnummer;
	private String postleitzahl;
	private String ortsname;
	private AdressTyp adresse;
	private int kundeId;
	private Kunde kunde;
	
	//Produkte:
	private String produktName1, produktName2;
	private int lagerbestand1, lagerbestand2;
	private float preis1, preis2;
	private Produkt produkt1, produkt2;
	
	//Testangebot:
	private Angebot angebot1, angebot2;
	private int produkt1Menge, produkt2Menge;
	
	//Testauftrag:
	private Auftrag auftrag1, auftrag2;

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
		lieferungMgmt = new LieferungMgmtFassade();
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
		angebot1 = auftragMgmt.erstelleAngebot(kunde, session);
		
		produkt1Menge = 2;
		produkt2Menge = 1;
		
		auftragMgmt.fuegeProduktZuAngebotHinzu(angebot1, produkt1, produkt1Menge, session);
		auftragMgmt.fuegeProduktZuAngebotHinzu(angebot1, produkt2, produkt2Menge, session);
		
		
		angebot2 = auftragMgmt.erstelleAngebot(kunde, session);
		
		auftragMgmt.fuegeProduktZuAngebotHinzu(angebot2, produkt1, produkt1Menge, session);
		auftragMgmt.fuegeProduktZuAngebotHinzu(angebot2, produkt2, produkt2Menge, session);
		
		//Auftrag erstellen:
		auftrag1 = auftragMgmt.erstelleAuftrag(angebot1, session);
		auftrag2 = auftragMgmt.erstelleAuftrag(angebot2, session);
		session.getTransaction().commit();
	}
	
	@Test
	public void testErstelleLieferung() {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Lieferung lieferung1 = lieferungMgmt.erstelleLieferung(auftrag1, session);
		Lieferung lieferung2 = lieferungMgmt.erstelleLieferung(auftrag2, session);
		
		assertTrue(lieferung1.getLieferungId() == 1);
		assertTrue(lieferung2.getLieferungId() == 2);
		
		assertFalse(lieferung1.isLieferungErfolgt());
		assertFalse(lieferung2.isLieferungErfolgt());
		
		assertEquals(lieferung1.getAuftrag(), auftrag1);
		assertEquals(lieferung2.getAuftrag(), auftrag2);

		assertNull(lieferung1.getLieferdatum());
		assertNull(lieferung2.getLieferdatum());
		
		session.getTransaction().commit();
	}
	
	@Test
	public void testMarkiereLieferungAlsErfolgt() {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Lieferung lieferung1 = lieferungMgmt.erstelleLieferung(auftrag1, session);
		Lieferung lieferung2 = lieferungMgmt.erstelleLieferung(auftrag2, session);
		
		Date vorAuslieferung = new Date();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) { fail("Unterbrochen waehren Auslieferungssimulation");}
		
		lieferungMgmt.markiereLieferungAlsErfolgt(lieferung1.getLieferungId(), session);
		lieferungMgmt.markiereLieferungAlsErfolgt(lieferung2.getLieferungId(), session);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) { fail("Unterbrochen waehren Auslieferungssimulation");}
		
		Date nachAuslieferung = new Date();
		
		assertTrue(lieferung1.isLieferungErfolgt());
		assertTrue(lieferung2.isLieferungErfolgt());
		
		assertNotNull(lieferung1.getLieferdatum());
		assertNotNull(lieferung2.getLieferdatum());
		
		assertTrue(vorAuslieferung.before(lieferung1.getLieferdatum()));
		assertTrue(vorAuslieferung.before(lieferung2.getLieferdatum()));
		
		assertTrue(nachAuslieferung.after(lieferung1.getLieferdatum()));
		assertTrue(nachAuslieferung.after(lieferung2.getLieferdatum()));
		
		session.getTransaction().commit();
	}

}
