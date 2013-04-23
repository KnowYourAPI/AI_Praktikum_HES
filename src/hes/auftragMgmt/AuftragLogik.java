package hes.auftragMgmt;


import hes.produktMgmt.Produkt;

import org.hibernate.Session;

public class AuftragLogik {

	private AngebotRepository angebotRepository;
	private AuftragRepository auftragRepository;
	
	public AuftragLogik(AngebotRepository angebotRepository, AuftragRepository auftragRepository) {
		this.angebotRepository = angebotRepository;
		this.auftragRepository = auftragRepository;
	}
	
	public Angebot fuegeProduktZuAngebotHinzu(Angebot angebot, Produkt produkt, int menge, Session session) {
			angebot.fuegeProduktHinzu(produkt, menge);
			angebotRepository.aktualisiereAngebot(angebot, session);
			return angebot;
	}
	
	public Angebot entferneProduktAusAngebot(Angebot angebot, Produkt produkt, Session session) {		
		angebot.entferneProdukt(produkt);
		angebotRepository.aktualisiereAngebot(angebot, session);
		return angebot;
	}
	
	
	
}
