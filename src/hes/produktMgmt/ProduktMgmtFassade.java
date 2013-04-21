package hes.produktMgmt;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import util.Tuple;

public class ProduktMgmtFassade implements IProduktMgmt {

	private ProduktRepository produktRepository;

	@Override
	public int legeProduktAn(String name, int lagerbestand, Session session) {
		return produktRepository.legeProduktAn(name, lagerbestand, session);
	}
	
	@Override
	public List<ProduktTyp> getAlleProdukte(Session session) {
		return produktRepository.getAlleProdukte(session);
	}

	@Override
	public boolean lagereAus(List<Tuple<Integer, Integer>> bestellListe) {
		return produktRepository.lagereAus(bestellListe);
	}

	@Override
	public void meldeWareneingang(int produktID, int produktMenge, Date datum,
			String lieferantenName, Object lieferschein) {
		produktRepository.meldeWareneingang(produktID, produktMenge, datum, lieferantenName, lieferschein);

	}

	@Override
	public List<BestellungTyp> getAusstehendeBestellungen() {
		return produktRepository.getAusstehendeBestellungen();
	}


}
