package hes.auftragMgmt;

import java.util.List;

public class AuftragMgmtFassade implements IAuftragMgmt {

	@Override
	public int erstelleAngebot(int kundenId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public AngebotTyp fuegeProduktZuAngebotHinzu(int angebotId, int produktId,
			int menge) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AngebotTyp entferneProduktAusAngebot(int angebotId, int produktId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuftragTyp erstelleAuftrag(int angebotId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AuftragTyp> getNichtAbgeschlosseneAuftraege(int produktID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void markiereAuftragAlsAbgeschlossen(int auftragId) {
		// TODO Auto-generated method stub
		
	}

}
