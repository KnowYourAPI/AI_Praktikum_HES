package hes.auftragMgmt;

import hes.kundeMgmt.Kunde;
import hes.produktMgmt.Produkt;

import java.util.List;

import org.hibernate.Session;

public class AuftragMgmtFassade implements IAuftragMgmt {

	AngebotRepository angebotRepository;
	AuftragRepository auftragRepository;
	AuftragLogik auftragLogik;
	
	public AuftragMgmtFassade() {
		angebotRepository = new AngebotRepository();
		auftragRepository = new AuftragRepository();
		auftragLogik = new AuftragLogik(angebotRepository);
	}
	
	@Override
	public Angebot erstelleAngebot(Kunde kunde, Session session) {
		return angebotRepository.erstelleAngebot(kunde, session);
	}

	@Override
	public Angebot fuegeProduktZuAngebotHinzu(Angebot angebot, Produkt produkt,
			int menge, Session session) {
		return auftragLogik.fuegeProduktZuAngebotHinzu(angebot, produkt, menge, session);
	}

	@Override
	public Angebot entferneProduktAusAngebot(Angebot angebot, Produkt produkt, Session session) {		
		return auftragLogik.entferneProduktAusAngebot(angebot, produkt, session);
	}

	@Override
	public Auftrag erstelleAuftrag(Angebot angebot, Session session) {
		return auftragRepository.erstelleAuftrag(angebot, session);
	}

	@Override
	public List<Auftrag> getNichtAbgeschlosseneAuftraege(Produkt produkt, Session session) {
		return auftragRepository.getNichtAbgeschlosseneAuftraege(produkt, session);
	}

	@Override
	public void markiereAuftragAlsAbgeschlossen(int auftragId, Session session) {
		auftragRepository.markiereAuftragAlsAbgeschlossen(auftragId, session);		
	}
	
	@Override
	public AngebotTyp getAngebotTyp(Angebot angebot) {
		return angebotRepository.getAngebotTyp(angebot);
	}
	
	@Override
	public AuftragTyp getAuftragTyp(Auftrag auftrag) {
		return auftragRepository.getAuftragTyp(auftrag);
	}

	@Override
	public Angebot getAngebot(int angebotId, Session session) {
		return angebotRepository.getAngebot(angebotId, session);
	}

	@Override
	public Auftrag getAuftrag(int auftragId, Session session) {
		return auftragRepository.getAuftrag(auftragId, session);
	}
}
