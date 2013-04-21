package hes.kundeMgmt;

import org.hibernate.Session;


public class KundeMgmtFassade implements IKundeMgmt {
	
	private KundeRepository kundeRepository;
	
	public KundeMgmtFassade() {
		this.kundeRepository = new KundeRepository();
	}

	@Override
	public KundeTyp getKunde(int kundeId) {
		return kundeRepository.getKunde(kundeId);
	}

	@Override
	public int erstelleKunde(String name, AdressTyp adresse, Session session) {
		return kundeRepository.erstelleKunde(name, adresse, session);
	}

	@Override
	public int getKundeId(String firmenName) {
		return kundeRepository.getKundeId(firmenName);
	}

}
