package hes.rechnungMgmt;

import org.hibernate.Session;

import hes.auftragMgmt.Auftrag;

public class RechnungMgmtFassade implements IRechnungMgmt {
	
	private RechnungRepository rechnungRepository;
	
	public RechnungMgmtFassade() {
		this.rechnungRepository = new RechnungRepository();
	}

	@Override
	public Rechnung legeRechnungAn(Auftrag auftrag, Session session) {
		Rechnung rechnung = new Rechnung(auftrag);
		rechnungRepository.speichereRechnung(rechnung, session);
		return rechnung;
	}

	@Override
	public boolean meldeZahlungsEingang(int rechnungId, float betrag, Session session) {
		Rechnung rechnung = rechnungRepository.ladeRechnung(rechnungId, session);
		
		if(rechnung != null) {
			boolean istRechnungBezahlt = rechnung.addiereZahlungseingang(betrag);
			rechnungRepository.speichereRechnung(rechnung, session);
			return istRechnungBezahlt;
		} else
			return false;
	}

	@Override
	public int getAuftragId(int rechnungId, Session session) {
		Rechnung rechnung = rechnungRepository.ladeRechnung(rechnungId, session);
		
		if(rechnung != null)
			return rechnung.getAuftragId();
		else
			return -1;
	}

}
