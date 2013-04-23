package hes.produktMgmt;

import hes.auftragMgmt.Angebot;

import java.util.List;

import org.hibernate.Session;

import util.IntIntTuple;

public class ProduktRepository {

	public Produkt legeProduktAn(String name, int lagerbestand, float preis, Session session) {
		session.beginTransaction();
		Produkt produkt = new Produkt(name, lagerbestand, preis);
		session.save(produkt);
		session.getTransaction().commit();
		return produkt;
	}
	
	public List<Produkt> getAlleProdukte(Session session) {
		session.beginTransaction();
		List<Produkt> produktList = session.createQuery("from Produkt").list();
		session.getTransaction().commit();
		return produktList;
	}
	
	public boolean produkteVorraetig(List<IntIntTuple> bestellListe, Session session) {
		session.beginTransaction();
		for (IntIntTuple produktIdMenge : bestellListe) {
			Produkt produkt = (Produkt)session.get(Produkt.class, produktIdMenge.getProduktId());
			if(produkt == null || produkt.getLagerbestand() < produktIdMenge.getMenge()) {
				session.getTransaction().commit();
				//return false;
				
				//Fur das 2.Praktukum nehen wir an, dass immer genuegend Produkte vorraetig sind, daher:
				return true;
			}
		}
		session.getTransaction().commit();
		return true;
	}
	
	public void lagereAus(List<IntIntTuple> bestellListe, Session session) {
		//TODO
		//auslagern und Wareinausgangsmeldung erstellen!!!
	}
	
	public Produkt getProdukt(int produktId, Session session) {
		session.beginTransaction();
		Produkt produkt = (Produkt) session.get(Produkt.class, produktId);
		session.getTransaction().commit();
		return produkt;
	}
	
	public void verbindeProduktMitAngebot(Produkt produkt, Angebot angebot, Session session){
		session.beginTransaction();
		produkt.addAngebot(angebot);
		session.update(produkt);
		session.getTransaction().commit();
	}
	
	
	public void trenneProduktUndAngebot(Produkt produkt, Angebot angebot, Session session){
		session.beginTransaction();
		produkt.entferneAngebot(angebot);
		session.update(produkt);
		session.getTransaction().commit();
	}

	
}
