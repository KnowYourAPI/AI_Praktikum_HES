package hes.produktMgmt;

import hes.auftragMgmt.Angebot;

import java.util.List;

import org.hibernate.Session;

import util.IntIntTuple;

public class ProduktRepository {

	public Produkt legeProduktAn(String name, int lagerbestand, float preis, Session session) {
		Produkt produkt = new Produkt(name, lagerbestand, preis);
		session.save(produkt);
		return produkt;
	}
	
	public List<Produkt> getAlleProdukte(Session session) {
		List<Produkt> produktList = session.createQuery("from Produkt").list();
		return produktList;
	}
	
	public boolean produkteVorraetig(List<IntIntTuple> bestellListe, Session session) {
		for (IntIntTuple produktIdMenge : bestellListe) {
			Produkt produkt = (Produkt)session.get(Produkt.class, produktIdMenge.getProduktId());
			if(produkt == null || produkt.getLagerbestand() < produktIdMenge.getMenge()) {
				//return false;
				
				//Fur das 2.Praktukum nehen wir an, dass immer genuegend Produkte vorraetig sind, daher:
				return true;
			}
		}
		return true;
	}
	
	public void lagereAus(List<IntIntTuple> bestellListe, Session session) {
		//TODO
		//auslagern und Wareinausgangsmeldung erstellen!!!
	}
	
	public Produkt getProdukt(int produktId, Session session) {
		Produkt produkt = (Produkt) session.get(Produkt.class, produktId);
		return produkt;
	}
	
	public void verbindeProduktMitAngebot(Produkt produkt, Angebot angebot, Session session){
		produkt.addAngebot(angebot);
		session.update(produkt);
	}
	
	
	public void trenneProduktUndAngebot(Produkt produkt, Angebot angebot, Session session){
		produkt.entferneAngebot(angebot);
		session.update(produkt);
	}

	
}
