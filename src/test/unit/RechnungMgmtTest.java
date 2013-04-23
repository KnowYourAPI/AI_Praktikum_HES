package test.unit;

import static org.junit.Assert.*;
import hes.auftragMgmt.Angebot;
import hes.auftragMgmt.Auftrag;
import hes.kundeMgmt.Adresse;
import hes.kundeMgmt.Kunde;
import hes.lieferungMgmt.Lieferung;
import hes.produktMgmt.Produkt;
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
	private IRechnungMgmt rechnungMgmt;
	private SchemaExport schemaExport;
	
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
	}

	@Test
	public void testLegeRechnungAn() {
		//Erstelle und teste Rechnung 1:
		Session session = sessionFactory.getCurrentSession();
		rechnungMgmt.legeRechnungAn(null, session);
		
		session = sessionFactory.getCurrentSession();
		int rechnungId1 = 1;
		session.beginTransaction();
		Rechnung rechnung1 = (Rechnung) session.get(Rechnung.class, rechnungId1);
		session.getTransaction().commit();
		
		assertTrue(rechnung1.getRechnungId() == rechnungId1);
		assertFalse(rechnung1.isIstBezahlt());
		assertTrue(rechnung1.getZahlungseingaenge().isEmpty());
		
		//Erstelle und teste Rechnung 2:
		session = sessionFactory.getCurrentSession();
		rechnungMgmt.legeRechnungAn(null, session);
		
		session = sessionFactory.getCurrentSession();
		int rechnungId2 = 2;
		session.beginTransaction();
		Rechnung rechnung2 = (Rechnung) session.get(Rechnung.class, rechnungId2);
		session.getTransaction().commit();
		
		assertTrue(rechnung2.getRechnungId() == rechnungId2);
		assertFalse(rechnung2.isIstBezahlt());
		assertTrue(rechnung2.getZahlungseingaenge().isEmpty());
	}
	
	@Test
	public void testMeldeZahlungsEingang(){
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetAuftragId() {
		fail("Not yet implemented");
	}
	
	@After
	public void teardown() {
		schemaExport.drop(true, true);		
	}

}