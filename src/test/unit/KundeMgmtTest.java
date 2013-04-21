package test.unit;

import static org.junit.Assert.*;

import java.util.List;

import hes.auftragMgmt.Angebot;
import hes.auftragMgmt.Auftrag;
import hes.kundeMgmt.AdressTyp;
import hes.kundeMgmt.Adresse;
import hes.kundeMgmt.IKundeMgmt;
import hes.kundeMgmt.Kunde;
import hes.kundeMgmt.KundeMgmtFassade;
import hes.produktMgmt.Produkt;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class KundeMgmtTest {
	
	IKundeMgmt kundeMgmt;
	SessionFactory sessionFactory;
	SchemaExport schemaExport;

	//Daten fuer Kunden:
	String name, name2;
	String strasse, strasse2;
	String hausnummer, hausnummer2;
	String plz, plz2;
	String stadt, stadt2;
	AdressTyp adresse, adresse2;
	
	@Before
	public void setup() {
		
		//Hibernate Setup:
		AnnotationConfiguration config = new AnnotationConfiguration();
		config.addAnnotatedClass(Adresse.class);
		config.addAnnotatedClass(Kunde.class);
		config.addAnnotatedClass(Angebot.class);
		config.addAnnotatedClass(Auftrag.class);
		config.addAnnotatedClass(Produkt.class);
		config.configure("hibernate.cfg.xml");
		//Wenn einkommentiert, loescht dieser Befehl
		//alle bestehenden Tabellen und erstellt neue
		//aus den Annotations
		schemaExport = new SchemaExport(config);
		schemaExport.create(true, true);
		
		sessionFactory = config.buildSessionFactory();
		kundeMgmt = new KundeMgmtFassade();
		
		//Daten fuer Kunden1:
		
		name = "TestKunde1";
		strasse = "Musterweg";
		hausnummer = "42a";
		plz = "12345";
		stadt = "Beispielstadt";
		adresse = new AdressTyp(strasse, hausnummer, plz, stadt);
		
		//Daten fuer Kunden2:
		
		name2 = "TestKunde2";
		strasse2 = "Musterweg2";
		hausnummer2 = "42b";
		plz2 = "23456";
		stadt2 = "Beispielstadt2";
		adresse2 = new AdressTyp(strasse2, hausnummer2, plz2, stadt2);
		
	}
	
	@SuppressWarnings("rawtypes")
	@Test
	public void testErstelleKunde() {
		//Kunde 1 erstellen und testen:
		Session session = sessionFactory.getCurrentSession();
		kundeMgmt.erstelleKunde(name, adresse, session);
		
		session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Kunde where name = :name");
		query.setParameter("name", name);
		List resultList = query.list();
		session.getTransaction().commit();
		
		assertTrue(resultList.size() == 1);
		Kunde kunde = (Kunde)resultList.get(0);
		
		assertEquals(kunde.getName(), name);
		assertTrue(kunde.getKundeId() == 1);
		
		//Kunde 2 erstellen und testen:
		session = sessionFactory.getCurrentSession();
		kundeMgmt.erstelleKunde(name2, adresse2, session);
		
		session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Query query2 = session.createQuery("from Kunde where name = :name");
		query2.setParameter("name", name2);
		List resultList2 = query2.list();
		session.getTransaction().commit();
		
		assertTrue(resultList2.size() == 1);
		Kunde kunde2 = (Kunde)resultList2.get(0);
		
		assertEquals(kunde2.getName(), name2);
		assertTrue(kunde2.getKundeId() == 2);
	}
	
	@Test
	public void testGetKunde() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testGetKundeId() {
		fail("Not yet implemented");
	}
	
	@After
	public void teardown() {
		schemaExport.drop(true, true);		
	}

}
