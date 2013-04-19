package hes.produktMgmt;

import java.util.Date;
import java.util.List;

import util.Tuple;

public class ProduktMgmtFassade implements IProduktMgmt {

	@Override
	public List<ProduktTyp> getAlleProdukte() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean lagereAus(List<Tuple<Integer, Integer>> bestellListe) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void meldeWareneingang(int produktID, int produktMenge, Date datum,
			String lieferantenName, Object lieferschein) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<BestellungTyp> getAusstehendeBestellungen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int legeProduktAn(String name, int lagerbestand) {
		// TODO Auto-generated method stub
		return 0;
	}

}
