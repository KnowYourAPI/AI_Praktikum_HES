package hes.fassade;

import hes.auftragMgmt.Angebot;
import hes.auftragMgmt.AngebotTyp;
import hes.auftragMgmt.Auftrag;
import hes.auftragMgmt.AuftragTyp;
import hes.auftragMgmt.IAuftragMgmt;
import hes.kundeMgmt.AdressTyp;
import hes.kundeMgmt.IKundeMgmt;
import hes.kundeMgmt.Kunde;
import hes.lieferungMgmt.ILieferungMgmt;
import hes.produktMgmt.IProduktMgmt;
import hes.produktMgmt.Produkt;
import hes.produktMgmt.ProduktTyp;
import hes.rechnungMgmt.IRechnungMgmt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HESAWKFassadeImpl implements IHESAWKFassade {
	
	//HES-Komponenten:
	private IAuftragMgmt auftragMgmt;
	private IKundeMgmt kundeMgmt;
	private IRechnungMgmt rechnungMgmt;
	private IProduktMgmt produktMgmt;
	private ILieferungMgmt lieferungMgmt;
	
	//Hibernate Session-Factory:
	private SessionFactory sessionFactory;
	
	public HESAWKFassadeImpl(IAuftragMgmt auftragMgmt, IKundeMgmt kundeMgmt,
			IRechnungMgmt rechnungMgmt, IProduktMgmt produktMgmt,
			ILieferungMgmt lieferungMgmt, SessionFactory sessionFactory) {
		this.auftragMgmt = auftragMgmt;
		this.kundeMgmt = kundeMgmt;
		this.rechnungMgmt = rechnungMgmt;
		this.produktMgmt = produktMgmt;
		this.lieferungMgmt = lieferungMgmt;
		this.sessionFactory = sessionFactory;
	}

	@Override
	public int legeKundeAn(String name, AdressTyp adresse) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		int kundeId = kundeMgmt.erstelleKunde(name, adresse, session);
		session.getTransaction().commit();
		return kundeId;
	}
	
	@Override
	public int legeProduktAn(String name, int lagerbestand, float preis) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Produkt produkt = produktMgmt.legeProduktAn(name, lagerbestand, preis, session);
		session.getTransaction().commit();
		return produkt.getProduktId();
	}

	@Override
	public int getKundeId(String firmenName) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		int kundeId = kundeMgmt.getKundeId(firmenName, session);
		session.getTransaction().commit();
		return kundeId;
	}

	@Override
	public int erstelleAngebot(int kundeId) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Kunde kunde = kundeMgmt.getKunde(kundeId, session);
		Angebot angebot = auftragMgmt.erstelleAngebot(kunde, session);
		kundeMgmt.verbindeKundeMitAngebot(kunde,angebot, session);
		session.getTransaction().commit();
		return angebot.getAngebotId();
	}

	@Override
	public List<ProduktTyp> getAlleProdukte() {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		List<Produkt> produktList = produktMgmt.getAlleProdukte(session);
		List<ProduktTyp> produktTypList = new ArrayList<ProduktTyp>();
		for(Produkt produkt : produktList) {
			produktTypList.add(produkt.getProduktTyp());
		}
		session.getTransaction().commit();
		return produktTypList;
	}

	@Override
	public AngebotTyp fuegeProduktZuAngebotHinzu(int angebotId, int produktId, int menge) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Angebot angebot = auftragMgmt.getAngebot(angebotId, session);
		Produkt produkt = produktMgmt.getProdukt(produktId, session);
		auftragMgmt.fuegeProduktZuAngebotHinzu(angebot, produkt, menge, session);
		produktMgmt.verbindeProduktMitAngebot(produkt, angebot, session);
		session.getTransaction().commit();
		return auftragMgmt.getAngebotTyp(angebot);
	}

	@Override
	public AngebotTyp entferneProduktAusAngebot(int angebotId, int produktId) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Angebot angebot = auftragMgmt.getAngebot(angebotId, session);
		Produkt produkt = produktMgmt.getProdukt(produktId, session);
		auftragMgmt.entferneProduktAusAngebot(angebot, produkt, session);
		produktMgmt.trenneProduktUndAngebot(produkt, angebot, session);
		session.getTransaction().commit();
		return auftragMgmt.getAngebotTyp(angebot);
	}

	@Override
	public AuftragTyp erstelleAuftrag(int angebotId) {		
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		Angebot angebot = auftragMgmt.getAngebot(angebotId, session);
		Auftrag auftrag = auftragMgmt.erstelleAuftrag(angebot, session);
		//Machen wir noch nicht, Produkte sollen nicht weniger werden, immer genuegend vorraetig 
//		produktMgmt.lagereAus(bestellListe, session);
		lieferungMgmt.erstelleLieferung(auftrag, session);
		rechnungMgmt.legeRechnungAn(auftrag, session);
		session.getTransaction().commit();
		return auftragMgmt.getAuftragTyp(auftrag);
	}

	@Override
	public void meldeWareneingang(int produktId, int produktMenge, Date datum,
			String lieferantenName, Object lieferschein) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		produktMgmt.meldeWareneingang(produktId, produktMenge, datum,
				lieferantenName, lieferschein, session);
		session.getTransaction().commit();
	}

	@Override
	public int getAuftragId(int rechnungId) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		int auftragId = rechnungMgmt.getAuftragId(rechnungId, session);
		session.getTransaction().commit();
		return auftragId;
	}

	@Override
	public void markiereLieferungAlsErfolgt(int lieferungId) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		lieferungMgmt.markiereLieferungAlsErfolgt(lieferungId, session);
		session.getTransaction().commit();
	}
	
	@Override
	public void markiereAuftragAlsAbgeschlossen(int auftragId) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		auftragMgmt.markiereAuftragAlsAbgeschlossen(auftragId, session);
		session.getTransaction().commit();
	}

	@Override
	public boolean meldeZahlungseingang(int rechnungId, float betrag) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();
		boolean rechnungBezahlt = rechnungMgmt.meldeZahlungsEingang(rechnungId, betrag, session);
		session.getTransaction().commit();
		return rechnungBezahlt;
	}

}
