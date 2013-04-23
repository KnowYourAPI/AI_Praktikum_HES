package test.unit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import hes.auftragMgmt.Angebot;
import hes.auftragMgmt.Auftrag;
import hes.kundeMgmt.AdressTyp;
import hes.kundeMgmt.Adresse;
import hes.kundeMgmt.Kunde;
import hes.produktMgmt.IProduktMgmt;
import hes.produktMgmt.Produkt;
import hes.produktMgmt.ProduktMgmtFassade;
import hes.produktMgmt.Warenausgangsmeldung;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Before;
import org.junit.Test;

import util.IntIntTuple;

public class ProduktMgmtTest {
	
	
	IProduktMgmt produktMgmt;
	SessionFactory sessionFactory;
	SchemaExport schemaExport;
	
	String name1, name2, name3;
	int lagerbstand1, lagerbstand2, lagerbstand3;
	float preis1, preis2, preis3;
	Produkt produkt1, produkt2, produkt3;
	Angebot angebot1;
	Kunde kunde1;
	
	@Before
	public void setup() {
		
		AnnotationConfiguration config = new AnnotationConfiguration();
		
		config.addAnnotatedClass(Produkt.class);
		config.addAnnotatedClass(Auftrag.class);
		config.addAnnotatedClass(Angebot.class);
		config.addAnnotatedClass(Adresse.class);
		config.addAnnotatedClass(Kunde.class);
		config.addAnnotatedClass(Warenausgangsmeldung.class);

		config.configure("hibernate.cfg.xml");
		
		//Wenn einkommentiert, loescht dieser Befehl
		//alle bestehenden Tabellen und erstellt neue
		//aus den Annotations
		schemaExport = new SchemaExport(config);
		schemaExport.create(true, true);
		
		name1 = "Chrysantheme"; 
		name2 = "Maiglöckchen";
		name3 = "Krokus";
		lagerbstand1 = 10;
		lagerbstand2 = 20;
		lagerbstand3 = 25;
		preis1 = 5.0F;
		preis2 = 2.5F;
		preis3 = 0.5F;
		
		
		sessionFactory = config.buildSessionFactory();
		
		produktMgmt = new ProduktMgmtFassade();
		
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		produkt1 = produktMgmt.legeProduktAn(name1, lagerbstand1, preis1, session);
		
		produkt2 = produktMgmt.legeProduktAn(name2, lagerbstand2, preis2, session);
		
		produkt3 = produktMgmt.legeProduktAn(name3, lagerbstand3, lagerbstand3, session);
		session.getTransaction().commit();
	}
	
	
	@Test
	public void testGetAlleProdukte() {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		assertEquals(produktMgmt.getAlleProdukte(session).get(0).getClass(), Produkt.class); 
		List<Produkt> produktList = produktMgmt.getAlleProdukte(session);
		assertTrue(produktList.size() == 3);
		System.out.println(produktList);
		assertEquals(produktList.get(0).getName(),name1);
		assertEquals(produktList.get(1).getLagerbestand(), lagerbstand2);
		
		produktMgmt.legeProduktAn("Tulpe", lagerbstand2, preis2, session);
		produktList = produktMgmt.getAlleProdukte(session);
		assertFalse(produktList.size() == 3);
		session.getTransaction().commit();
	}
	
	@Test
	public void testGetProdukt() {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		assertEquals(produktMgmt.getProdukt(1, session), produkt1);
		assertEquals(produktMgmt.getProdukt(2, session), produkt2);
		assertNull(produktMgmt.getProdukt(5, session));
		
		session.getTransaction().commit();
	}
	
	@Test
	public void TestVerbindeUndTrenneProduktUndAngebot(){
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		AdressTyp adressTyp1 = new AdressTyp("Neuer Weg", "1a", "07823", "Göttingen");
		Kunde kunde1 = new Kunde("Hans Mayer", adressTyp1);
		session.save(kunde1);
		Angebot angebot1 = new Angebot(kunde1);
		session.save(angebot1);
		produktMgmt.verbindeProduktMitAngebot(produkt1, angebot1, session);
		
		session.beginTransaction();
		Produkt p = (Produkt)session.get(Produkt.class, produkt1.getProduktId());
		assertTrue(p.getAngebote().contains(angebot1));
		session.getTransaction().commit();
		
		session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		produktMgmt.trenneProduktUndAngebot(produkt1, angebot1, session);
		p = (Produkt)session.get(Produkt.class, produkt1.getProduktId());
		assertFalse(p.getAngebote().contains(angebot1));
		session.getTransaction().commit();
	}
	
	@Test
	public void testLagereAus() {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<IntIntTuple> bestellListe = new ArrayList<IntIntTuple>();
		bestellListe.add(new IntIntTuple(1, 5));
		//Zum Praktikum2 kommt hier immer True zurueck!
		assertTrue(produktMgmt.lagereAus(bestellListe, session));
		session.getTransaction();
	}

}
