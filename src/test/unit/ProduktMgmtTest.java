package test.unit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import hes.auftragMgmt.Angebot;
import hes.auftragMgmt.Auftrag;
import hes.kundeMgmt.Adresse;
import hes.kundeMgmt.Kunde;
import hes.kundeMgmt.KundeMgmtFassade;
import hes.produktMgmt.IProduktMgmt;
import hes.produktMgmt.Produkt;
import hes.produktMgmt.ProduktMgmtFassade;
import hes.produktMgmt.ProduktTyp;
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
		produktMgmt.legeProduktAn(name1, lagerbstand1, preis1, session);
		
		session = sessionFactory.getCurrentSession();
		produktMgmt.legeProduktAn(name2, lagerbstand2, preis2, session);
		
		session = sessionFactory.getCurrentSession();
		produktMgmt.legeProduktAn(name3, lagerbstand3, lagerbstand3, session);
		
	}
	
	
	@Test
	public void testGetAlleProdukte() {
		Session session = sessionFactory.getCurrentSession();
		assertEquals(produktMgmt.getAlleProdukte(session).get(0).getClass(), ProduktTyp.class); 
		session = sessionFactory.getCurrentSession();
		List<ProduktTyp> produktTypList = produktMgmt.getAlleProdukte(session);
		assertTrue(produktTypList.size() == 3);
		System.out.println(produktTypList);
		assertEquals(produktTypList.get(0).getName(),name1);
		assertEquals(produktTypList.get(1).getLagerbestand(), lagerbstand2);
		
		session = sessionFactory.getCurrentSession();
		produktMgmt.legeProduktAn("Tulpe", lagerbstand2, preis2, session);
		session = sessionFactory.getCurrentSession();
		produktTypList = produktMgmt.getAlleProdukte(session);
		assertFalse(produktTypList.size() == 3);
	}
	
	@Test
	public void test() {
		List<IntIntTuple> bestellListe = new ArrayList<IntIntTuple>();
		bestellListe.add(new IntIntTuple(1, 5));
		Session session = sessionFactory.getCurrentSession();
		assertTrue(produktMgmt.lagereAus(bestellListe, session));
	}

}
