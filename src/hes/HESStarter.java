package hes;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import hes.auftragMgmt.Angebot;
import hes.auftragMgmt.Auftrag;
import hes.auftragMgmt.AuftragMgmtFassade;
import hes.auftragMgmt.IAuftragMgmt;
import hes.fassade.FassadeImpl;
import hes.fassade.IFassade;
import hes.kundeMgmt.AdressTyp;
import hes.kundeMgmt.Adresse;
import hes.kundeMgmt.IKundeMgmt;
import hes.kundeMgmt.Kunde;
import hes.kundeMgmt.KundeMgmtFassade;
import hes.lieferungMgmt.ILieferungMgmt;
import hes.lieferungMgmt.LieferungMgmtFassade;
import hes.produktMgmt.IProduktMgmt;
import hes.produktMgmt.ProduktMgmtFassade;
import hes.rechnungMgmt.IRechnungMgmt;
import hes.rechnungMgmt.RechnungMgmtFassade;

public class HESStarter {

	public static void main(String[] args) {
		
		
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
		config.configure("hibernate.cfg.xml");
		//Wenn einkommentiert, loescht dieser Befehl
		//alle bestehenden Tabellen und erstellt neue
		//aus den Annotations
		SchemaExport schemaExport = new SchemaExport(config);
		schemaExport.create(true, true);
		
		SessionFactory sessionFactory = config.buildSessionFactory();
		
		//Dependency Injection:
		IAuftragMgmt auftragMgmt = new AuftragMgmtFassade();
		IKundeMgmt kundeMgmt = new KundeMgmtFassade();
		IRechnungMgmt rechnungMgmt = new RechnungMgmtFassade();
		IProduktMgmt produktMgmt = new ProduktMgmtFassade();
		ILieferungMgmt lieferungMgmt = new LieferungMgmtFassade();
		IFassade fassade = new FassadeImpl(auftragMgmt, kundeMgmt,
				rechnungMgmt, produktMgmt, lieferungMgmt, sessionFactory);
		
		String name = "Julian";
		AdressTyp adresse = new AdressTyp("Starweg", "86a", "22926", "Ahrensburg");
		
		System.out.println("Neuer Kunde, Id:" + fassade.legeKundeAn(name, adresse));

	}

}
