package test.unit;

import static org.junit.Assert.*;

import java.util.List;

import hes.auftragMgmt.Angebot;
import hes.auftragMgmt.AngebotTyp;
import hes.auftragMgmt.Auftrag;
import hes.auftragMgmt.AuftragMgmtFassade;
import hes.auftragMgmt.AuftragTyp;
import hes.auftragMgmt.IAuftragMgmt;
import hes.kundeMgmt.AdressTyp;
import hes.kundeMgmt.Adresse;
import hes.kundeMgmt.Kunde;
import hes.lieferungMgmt.Lieferung;
import hes.produktMgmt.Produkt;
import hes.produktMgmt.Warenausgangsmeldung;
import hes.rechnungMgmt.Rechnung;
import hes.rechnungMgmt.Zahlungseingang;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Before;
import org.junit.Test;

public class AuftragMgmtTest {

	IAuftragMgmt auftragMgmt;
	SessionFactory sessionFactory;
	SchemaExport schemaExport;
	
	String name1 = "Anton";
	String name2 = "Bert";
	String strasse1 = "Musterweg";
	String hausnummer1 = "42a";
	String plz1 = "12345";
	String stadt1 = "Beispielstadt";
	String strasse2 = "Rabestrasse";
	String hausnummer2 = "17";
	String plz2 = "33345";
	String stadt2 = "Göttingen";
	AdressTyp adressTyp1 = new AdressTyp(strasse1, hausnummer1, plz1, stadt1);
	AdressTyp adressTyp2 = new AdressTyp(strasse2, hausnummer2, plz2, stadt2);
	
	@Before
	public void setup() {
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

		auftragMgmt = new AuftragMgmtFassade();
		
	}
	
	@Test
	public void testAngebotUndAuftragAnlegen() {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		Kunde kunde1 = new Kunde(name1, adressTyp1);
		session.save(kunde1);
		Kunde kunde2 = new Kunde(name2, adressTyp2);
		session.save(kunde2);
		
		Angebot angebot1 = auftragMgmt.erstelleAngebot(kunde1, session);
		Angebot angebot2 = auftragMgmt.erstelleAngebot(kunde2, session);
		Auftrag auftrag1 = auftragMgmt.erstelleAuftrag(angebot1, session);
		Auftrag auftrag2 = auftragMgmt.erstelleAuftrag(angebot2, session);
		
		Angebot an1 = (Angebot)session.get(Angebot.class, 1);
		Auftrag au1 = (Auftrag)session.get(Auftrag.class, 1);
		
		assertEquals(an1, angebot1);
		assertTrue(an1.getGesamtPreis() < 2);
		assertTrue(an1.getProdukte().isEmpty());
		assertEquals(au1, auftrag1);
		assertFalse(au1.isIstAbgeschlossen());
		assertEquals(au1.getAuftragId(),1);
		assertFalse(auftrag2.equals(au1));
		session.getTransaction().commit();
	}
	
	@Test
	public void testProduktZuAngebotHinzufuegenUndEntfernen() {

		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		Kunde kunde1 = new Kunde(name1, adressTyp1);
		session.save(kunde1);
		Kunde kunde2 = new Kunde(name2, adressTyp2);
		session.save(kunde2);
		
		Angebot angebot1 = auftragMgmt.erstelleAngebot(kunde1, session);

		
		Produkt produkt1 = new Produkt("Chrysantheme", 10, 5.0F);
		session.save(produkt1);
		Produkt produkt2 = new Produkt("Maiglöckchen", 20, 2.5F);
		session.save(produkt2);
		Produkt produkt3 = new Produkt("Krokus", 25, 0.5F);		
		session.save(produkt3);
		session.getTransaction().commit();
		
		int menge1 = 20;
		int menge2 = 40;
		
		session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		
		auftragMgmt.fuegeProduktZuAngebotHinzu(angebot1, produkt1, menge1, session);
		auftragMgmt.fuegeProduktZuAngebotHinzu(angebot1, produkt2, menge2, session);
		
		assertEquals(angebot1.getProdukte().size(), 2);
		assertTrue(angebot1.getGesamtPreis() > (5.0*19 + 2.5*40) && angebot1.getGesamtPreis() < (5.0*20 + 2.5*41));
		
		auftragMgmt.fuegeProduktZuAngebotHinzu(angebot1, produkt3, menge1, session);
		assertEquals(angebot1.getProdukte().size(), 3);
		
		auftragMgmt.fuegeProduktZuAngebotHinzu(angebot1, produkt3, menge1, session);
		assertEquals(angebot1.getProdukte().size(), 3);
		
		auftragMgmt.entferneProduktAusAngebot(angebot1, produkt1, session);
		assertEquals(angebot1.getProdukte().size(), 2);
		assertTrue(angebot1.getGesamtPreis() > (2.5*40 + 0.5*39) && angebot1.getGesamtPreis() < (2.5*40 + 0.5*41));
		
		session.getTransaction().commit();
	}
	
	//@Test
	public void testAbgeschlosseneAuftraege() {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		Kunde kunde1 = new Kunde(name1, adressTyp1);
		session.save(kunde1);
		Kunde kunde2 = new Kunde(name2, adressTyp2);
		session.save(kunde2);
		
		Produkt produkt1 = new Produkt("Chrysantheme", 10, 5.0F);
		session.save(produkt1);
		
		Angebot angebot1 = auftragMgmt.erstelleAngebot(kunde1, session);
		auftragMgmt.fuegeProduktZuAngebotHinzu(angebot1, produkt1, 7, session);
		Angebot angebot2 = auftragMgmt.erstelleAngebot(kunde2, session);
		auftragMgmt.fuegeProduktZuAngebotHinzu(angebot1, produkt1, 10, session);
		Angebot angebot3= auftragMgmt.erstelleAngebot(kunde2, session);

		
		Auftrag auftrag1 = auftragMgmt.erstelleAuftrag(angebot1, session);
		auftragMgmt.erstelleAuftrag(angebot2, session);
		auftragMgmt.erstelleAuftrag(angebot3, session);

		
		List<Auftrag> listeNichtAbgeschlossen = auftragMgmt.getNichtAbgeschlosseneAuftraege(produkt1, session);
		assertEquals(listeNichtAbgeschlossen.size(),2);
		assertFalse(auftrag1.isIstAbgeschlossen());
		
		auftragMgmt.markiereAuftragAlsAbgeschlossen(auftrag1.getAuftragId(), session);
		assertTrue(auftrag1.isIstAbgeschlossen());
		
		listeNichtAbgeschlossen = auftragMgmt.getNichtAbgeschlosseneAuftraege(produkt1, session);
		assertEquals(listeNichtAbgeschlossen.size(),1);
		
		session.getTransaction().commit();
	}
	
	@Test 
	public void testGetAuftragAngebotUndTypen() {
		
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		Kunde kunde1 = new Kunde(name1, adressTyp1);
		session.save(kunde1);
		Kunde kunde2 = new Kunde(name2, adressTyp2);
		session.save(kunde2);
		
		Produkt produkt1 = new Produkt("Chrysantheme", 10, 5.0F);
		session.save(produkt1);
		
		Angebot angebot1 = auftragMgmt.erstelleAngebot(kunde1, session);
		Angebot angebot2 = auftragMgmt.erstelleAngebot(kunde2, session);
		Auftrag auftrag1 = auftragMgmt.erstelleAuftrag(angebot1, session);
		Auftrag auftrag2 = auftragMgmt.erstelleAuftrag(angebot2, session);
		
		
		AngebotTyp angebotTyp1a = auftragMgmt.getAngebotTyp(angebot1);
		AngebotTyp angebotTyp1b = auftragMgmt.getAngebotTyp(angebot1);
		AngebotTyp angebotTyp2a = auftragMgmt.getAngebotTyp(angebot2);
		
		assertEquals(angebotTyp1a,angebotTyp1b);
		assertFalse(angebotTyp1a.equals(angebotTyp2a));
		
		AuftragTyp auftragTyp1a = auftragMgmt.getAuftragTyp(auftrag1);
		AuftragTyp auftragTyp1b = auftragMgmt.getAuftragTyp(auftrag1);
		AuftragTyp auftragTyp2a = auftragMgmt.getAuftragTyp(auftrag2);

		assertEquals(auftragTyp1a,auftragTyp1b);
		assertFalse(auftragTyp1a.equals(auftragTyp2a));
		

		Angebot an1 =  auftragMgmt.getAngebot(angebot1.getAngebotId(), session);
		Auftrag auf1 = auftragMgmt.getAuftrag(auftrag1.getAuftragId(), session);
		
		assertEquals(an1, angebot1);
		assertEquals(auf1, auftrag1);
		
		
		auftragMgmt.markiereAuftragAlsAbgeschlossen(auftrag2.getAuftragId(), session);
		Auftrag auf2 = auftragMgmt.getAuftrag(auftrag2.getAuftragId(), session);
		assertTrue(auf2.equals(auftrag2));
		AuftragTyp auftragTyp2b = auftragMgmt.getAuftragTyp(auftrag2);
		assertFalse(auftragTyp2a.equals(auftragTyp2b));
		
		session.getTransaction().commit();
	}
	

}
