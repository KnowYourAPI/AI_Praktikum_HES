package hes.fassade;

import hes.auftragMgmt.AngebotTyp;
import hes.auftragMgmt.AuftragTyp;
import hes.auftragMgmt.IAuftragMgmt;
import hes.kundeMgmt.IKundeMgmt;
import hes.lieferungMgmt.ILieferungMgmt;
import hes.produktMgmt.IProduktMgmt;
import hes.produktMgmt.ProduktTyp;
import hes.rechnungMgmt.IRechnungMgmt;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import util.AdressTyp;

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
	public int legeProduktAn(String name, int lagerbestand) {
		return produktMgmt.legeProduktAn(name, lagerbestand);
	}

	@Override
	public int getKundeId(String firmenName) {
		return kundeMgmt.getKundeId(firmenName);
	}

	@Override
	public int erstelleAngebot(int kundenId) {
		return auftragMgmt.erstelleAngebot(kundenId);
	}

	@Override
	public List<ProduktTyp> getAlleProdukte() {
		return produktMgmt.getAlleProdukte();
	}

	@Override
	public AngebotTyp fuegeProduktZuAngebotHinzu(int angebotId, int produktId, int menge) {
		return auftragMgmt.fuegeProduktZuAngebotHinzu(angebotId, produktId, menge);
	}

	@Override
	public AngebotTyp entferneProduktAusAngebot(int angebotId, int produktId) {
		return auftragMgmt.entferneProduktAusAngebot(angebotId, produktId);
	}

	@Override
	public AuftragTyp erstelleAuftrag(int angebotId) {
		return auftragMgmt.erstelleAuftrag(angebotId);
	}

	@Override
	public void meldeWareneingang(int produktId, int produktMenge, Date datum,
			String lieferantenName, Object lieferschein) {
		
		produktMgmt.meldeWareneingang(produktId, produktMenge, datum,
				lieferantenName, lieferschein);

	}

	@Override
	public int getAuftragId(int rechnungId) {
		return rechnungMgmt.getAuftragID(rechnungId);
	}

	@Override
	public void markiereLieferungAlsErfolgt(int lieferungId) {
		lieferungMgmt.markiereLieferungAlsErfolgt(lieferungId);	
	}
	
	@Override
	public void markiereAuftragAlsAbgeschlossen(int auftragId) {
		auftragMgmt.markiereAuftragAlsAbgeschlossen(auftragId);
	}

}
