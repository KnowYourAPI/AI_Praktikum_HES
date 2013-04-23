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
		fail("Not yet implemented");
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