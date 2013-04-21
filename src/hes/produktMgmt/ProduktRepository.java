package hes.produktMgmt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import util.Tuple;

public class ProduktRepository {

	public int legeProduktAn(String name, int lagerbestand, Session session) {
		session.beginTransaction();
		Produkt produkt1 = new Produkt(name, lagerbestand);
		int produktId = produkt1.getProduktId();
		session.save(produkt1);
		session.getTransaction().commit();
		return produktId;
	}
	
	public List<ProduktTyp> getAlleProdukte(Session session) {
		session.beginTransaction();
		List<ProduktTyp> listProduktTyp = new ArrayList<ProduktTyp>();
		List<Produkt> produktList = session.createQuery("from Produkt").list();
		for(Produkt produkt : produktList) {
			listProduktTyp.add(produkt.getProduktTyp());
		}
		session.getTransaction().commit();
		return listProduktTyp;
	}
	
	public boolean lagereAus(List<Tuple<Integer, Integer>> bestellListe) {
		// TODO Auto-generated method stub
		return false;
	}


	public void meldeWareneingang(int produktID, int produktMenge, Date datum,
			String lieferantenName, Object lieferschein) {
		// TODO Auto-generated method stub
		
	}

	public List<BestellungTyp> getAusstehendeBestellungen() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
