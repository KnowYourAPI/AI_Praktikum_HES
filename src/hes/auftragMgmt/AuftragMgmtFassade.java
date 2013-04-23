package hes.auftragMgmt;

import hes.kundeMgmt.Kunde;

import java.util.List;

public class AuftragMgmtFassade implements IAuftragMgmt {

	@Override
	public Angebot erstelleAngebot(Kunde kundenId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Angebot fuegeProduktZuAngebotHinzu(int angebotId, int produktId,
			int menge) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Angebot entferneProduktAusAngebot(int angebotId, int produktId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Auftrag erstelleAuftrag(int angebotId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Auftrag> getNichtAbgeschlosseneAuftraege(int produktID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void markiereAuftragAlsAbgeschlossen(int auftragId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AngebotTyp getAngebotTyp(Angebot angebot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuftragTyp getAuftragTyp(Auftrag auftrag) {
		// TODO Auto-generated method stub
		return null;
	}

}
