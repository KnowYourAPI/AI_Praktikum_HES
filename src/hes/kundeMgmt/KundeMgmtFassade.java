package hes.kundeMgmt;

import hes.auftragMgmt.Angebot;

import org.hibernate.Session;


public class KundeMgmtFassade implements IKundeMgmt {
	
	private KundeRepository kundeRepository;
	
	public KundeMgmtFassade() {
		this.kundeRepository = new KundeRepository();
	}

	@Override
	public Kunde getKunde(int kundeId, Session session) {
		return kundeRepository.ladeKunde(kundeId, session);
	}

	@Override
	public int erstelleKunde(String name, AdressTyp adresse, Session session) {
		return kundeRepository.erstelleKunde(name, adresse, session);
	}

	@Override
	public int getKundeId(String firmenName, Session session) {
		return kundeRepository.getKundeId(firmenName, session);
	}

	@Override
	public void verbindeKundeMitAngebot(Kunde kunde, Angebot angebot, Session session) {
		kunde.addAngebot(angebot);
		kundeRepository.aktualisiereKunde(kunde, session);
	}

}
