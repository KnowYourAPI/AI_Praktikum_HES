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

public class FassadeImpl implements IFassade {
	
	//HES-Komponenten:
	private IAuftragMgmt auftragMgmt;
	private IKundeMgmt kundeMgmt;
	private IRechnungMgmt rechnungMgmt;
	private IProduktMgmt produktMgmt;
	private ILieferungMgmt lieferungMgmt;
	
	//Hibernate Session-Factory:
	private SessionFactory sessionFactory;
	
	public FassadeImpl(IAuftragMgmt auftragMgmt, IKundeMgmt kundeMgmt,
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
		return kundeMgmt.erstelleKunde(name, adresse, session);
	}
	
	@Override
	public int legeProduktAn(String name, int lagerbestand, float preis) {
		Session session = sessionFactory.getCurrentSession();
		Produkt produkt = produktMgmt.legeProduktAn(name, lagerbestand, preis, session);
		return produkt.getProduktId();
	}

	@Override
	public int getKundeId(String firmenName) {
		Session session = sessionFactory.getCurrentSession();
		return kundeMgmt.getKundeId(firmenName, session);
	}

	@Override
	public int erstelleAngebot(int kundeId) {
		Session session = sessionFactory.getCurrentSession();
		Kunde kunde = kundeMgmt.getKunde(kundeId, session);
		session = sessionFactory.getCurrentSession();
		return auftragMgmt.erstelleAngebot(kunde, session).getAngebotId();
	}

	@Override
	public List<ProduktTyp> getAlleProdukte() {
		Session session = sessionFactory.getCurrentSession();
		List<Produkt> produktList = produktMgmt.getAlleProdukte(session);
		List<ProduktTyp> produktTypList = new ArrayList<ProduktTyp>();
		for(Produkt produkt : produktList) {
			produktTypList.add(produkt.getProduktTyp());
		}
		return produktTypList;
	}

	@Override
	public AngebotTyp fuegeProduktZuAngebotHinzu(int angebotId, int produktId, int menge) {
		Session session = sessionFactory.getCurrentSession();
		Angebot angebot = auftragMgmt.getAngebot(angebotId, session);
		session = sessionFactory.getCurrentSession();
		Produkt produkt = produktMgmt.getProdukt(produktId, session);
		session = sessionFactory.getCurrentSession();
		auftragMgmt.fuegeProduktZuAngebotHinzu(angebot, produkt, menge, session);
		return auftragMgmt.getAngebotTyp(angebot);
	}

	@Override
	public AngebotTyp entferneProduktAusAngebot(int angebotId, int produktId) {
		Session session = sessionFactory.getCurrentSession();
		Angebot angebot = auftragMgmt.getAngebot(angebotId, session);
		session = sessionFactory.getCurrentSession();
		Produkt produkt = produktMgmt.getProdukt(produktId, session);
		session = sessionFactory.getCurrentSession();
		auftragMgmt.entferneProduktAusAngebot(angebot, produkt, session);
		return auftragMgmt.getAngebotTyp(angebot);
	}

	@Override
	public AuftragTyp erstelleAuftrag(int angebotId) {		
		Session session = sessionFactory.getCurrentSession();
		Angebot angebot = auftragMgmt.getAngebot(angebotId, session);
		session = sessionFactory.getCurrentSession();
		Auftrag auftrag = auftragMgmt.erstelleAuftrag(angebot, session);
		return auftragMgmt.getAuftragTyp(auftrag);
	}

	@Override
	public void meldeWareneingang(int produktId, int produktMenge, Date datum,
			String lieferantenName, Object lieferschein) {
		Session session = sessionFactory.getCurrentSession();
		produktMgmt.meldeWareneingang(produktId, produktMenge, datum,
				lieferantenName, lieferschein, session);

	}

	@Override
	public int getAuftragId(int rechnungId) {
		Session session = sessionFactory.getCurrentSession();
		return rechnungMgmt.getAuftragId(rechnungId, session);
	}

	@Override
	public void markiereLieferungAlsErfolgt(int lieferungId) {
		Session session = sessionFactory.getCurrentSession();
		lieferungMgmt.markiereLieferungAlsErfolgt(lieferungId, session);
	}
	
	@Override
	public void markiereAuftragAlsAbgeschlossen(int auftragId) {
		Session session = sessionFactory.getCurrentSession();
		auftragMgmt.markiereAuftragAlsAbgeschlossen(auftragId, session);
	}

}
