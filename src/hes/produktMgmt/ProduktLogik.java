package hes.produktMgmt;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import util.IntIntTuple;

public class ProduktLogik {

	private ProduktRepository produktRepository;
	
	public ProduktLogik(ProduktRepository produktRepository) {
		this.produktRepository = produktRepository;
	}
	
	public boolean lagereAus(List<IntIntTuple> bestellListe, Session session) {
		boolean produkteVorraetig = produktRepository.produkteVorraetig(bestellListe, session);
		if (produkteVorraetig){
			produktRepository.lagereAus(bestellListe, session);
		}
		return produkteVorraetig;
	}


	public void meldeWareneingang(int produktID, int produktMenge, Date datum,
			String lieferantenName, Object lieferschein, Session session) {
		// TODO Auto-generated method stub
		
	}

	public List<BestellungTyp> getAusstehendeBestellungen(Session session) {
		// TODO Auto-generated method stub
		return null;
	}
}
