package hes.produktMgmt;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import util.Tuple;

public class ProduktMgmtFassade implements IProduktMgmt {

	private ProduktRepository produktRepository;
	private ProduktLogik produktLogik;
	
	public ProduktMgmtFassade() {
		produktRepository = new ProduktRepository();
		produktLogik = new ProduktLogik();
	}

	@Override
	public int legeProduktAn(String name, int lagerbestand, float preis, Session session) {
		return produktRepository.legeProduktAn(name, lagerbestand, preis, session);
	}
	
	@Override
	public List<ProduktTyp> getAlleProdukte(Session session) {
		return produktRepository.getAlleProdukte(session);
	}

	@Override
	public boolean lagereAus(List<Tuple<Integer, Integer>> bestellListe) {
		return produktLogik.lagereAus(bestellListe);
	}

	@Override
	public void meldeWareneingang(int produktID, int produktMenge, Date datum,
			String lieferantenName, Object lieferschein) {
		produktLogik.meldeWareneingang(produktID, produktMenge, datum, lieferantenName, lieferschein);

	}

	@Override
	public List<BestellungTyp> getAusstehendeBestellungen() {
		return produktLogik.getAusstehendeBestellungen();
	}


}
