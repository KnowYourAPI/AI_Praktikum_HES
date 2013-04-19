package hes.kundeMgmt;

import util.AdressTyp;

public class KundeMgmtFassade implements IKundeMgmt {
	
	private KundeRepository kundeRepository;
	
	public KundeMgmtFassade() {
		this.kundeRepository = new KundeRepository();
	}

	@Override
	public IKunde getKunde(int kundeId) {
		return kundeRepository.getKunde(kundeId);
	}

	@Override
	public int erstelleKunde(String name, AdressTyp adresse) {
		return kundeRepository.erstelleKunde(name, adresse);
	}

	@Override
	public int getKundeId(String firmenName) {
		return kundeRepository.getKundeId(firmenName);
	}

}
