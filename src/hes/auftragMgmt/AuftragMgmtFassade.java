package hes.auftragMgmt;

import hes.kundeMgmt.Kunde;
import hes.produktMgmt.Produkt;

import java.util.List;

public class AuftragMgmtFassade implements IAuftragMgmt {

	AngebotRepository angebotRepository;
	AuftragRepository auftragRepository;
	
	public AuftragMgmtFassade() {
		angebotRepository = new AngebotRepository();
		auftragRepository = new AuftragRepository();
	}
	
	@Override
	public Angebot erstelleAngebot(Kunde kunde) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Angebot fuegeProduktZuAngebotHinzu(Angebot angebot, Produkt produkt,
			int menge) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Angebot entferneProduktAusAngebot(Angebot angebot, Produkt produkt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Auftrag erstelleAuftrag(Angebot angebot) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Auftrag> getNichtAbgeschlosseneAuftraege(Produkt produkt) {
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
