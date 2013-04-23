package hes.produktMgmt;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import util.IntIntTuple;

public class ProduktMgmtFassade implements IProduktMgmt {

	private ProduktRepository produktRepository;
	private ProduktLogik produktLogik;
	
	public ProduktMgmtFassade() {
		produktRepository = new ProduktRepository();
		produktLogik = new ProduktLogik(produktRepository);
	}

	@Override
	public Produkt legeProduktAn(String name, int lagerbestand, float preis, Session session) {
		return produktRepository.legeProduktAn(name, lagerbestand, preis, session);
	}
	
	@Override
	public List<Produkt> getAlleProdukte(Session session) {
		return produktRepository.getAlleProdukte(session);
	}

	
	@Override
	public boolean lagereAus(List<IntIntTuple> bestellListe, Session session) {
		return produktLogik.lagereAus(bestellListe, session);
	}

	@Override
	public void meldeWareneingang(int produktID, int produktMenge, Date datum,
			String lieferantenName, Object lieferschein, Session session) {
		produktLogik.meldeWareneingang(produktID, produktMenge, datum, lieferantenName, lieferschein, session);

	}

	@Override
	public List<Bestellung> getAusstehendeBestellungen(Session session) {
		return produktLogik.getAusstehendeBestellungen(session);
	}

	@Override
	public Produkt getProdukt(int produktId, Session session) {
		return produktRepository.getProdukt(produktId, session);
	}

	

}
