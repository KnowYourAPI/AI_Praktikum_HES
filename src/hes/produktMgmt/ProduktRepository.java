package hes.produktMgmt;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import util.IntIntTuple;

public class ProduktRepository {

	public int legeProduktAn(String name, int lagerbestand, float preis, Session session) {
		session.beginTransaction();
		Produkt produkt1 = new Produkt(name, lagerbestand, preis);
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
	
	public boolean produkteVorraetig(List<IntIntTuple> bestellListe, Session session) {
		session.beginTransaction();
		for (IntIntTuple produktIdMenge : bestellListe) {
			Produkt produkt = (Produkt)session.get(Produkt.class, produktIdMenge.getProduktId());
			if(produkt == null || produkt.getLagerbestand() < produktIdMenge.getMenge()) {
				session.getTransaction().commit();
				return false;
			}
		}
		session.getTransaction().commit();
		return true;
	}
	
	public void lagereAus(List<IntIntTuple> bestellListe, Session session) {
		//TODO
		//auslagern und Wareinausgangsmeldung erstellen!!!
	}

	
}
