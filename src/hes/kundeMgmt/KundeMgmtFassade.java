package hes.kundeMgmt;

import org.hibernate.Session;


public class KundeMgmtFassade implements IKundeMgmt {
	
	private KundeRepository kundeRepository;
	
	public KundeMgmtFassade() {
		this.kundeRepository = new KundeRepository();
	}

	@Override
	public KundeTyp getKunde(int kundeId, Session session) {
		return kundeRepository.getKunde(kundeId, session);
	}

	@Override
	public int erstelleKunde(String name, AdressTyp adresse, Session session) {
		return kundeRepository.erstelleKunde(name, adresse, session);
	}

	@Override
	public int getKundeId(String firmenName, Session session) {
		return kundeRepository.getKundeId(firmenName, session);
	}

}
