package hes.produktMgmt;


import hes.auftragMgmt.Angebot;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import util.IntIntTuple;

public interface IProduktMgmt {
	
	/**
	 * @param name Der Name des Produktes, das angelegt werden soll
	 * @param lagerbestand Der Lagerbestand des Produktes bei dessen Anlegung in der Datenbank
	 * @param preis Der Preis des Produkts
	 * @param session Die aktuelle Session
	 * @return Das gerade angelegte Produkt
	 * */
	Produkt legeProduktAn(String name, int lagerbestand, float preis, Session session);

	/**
	 * @param session Die aktuelle Session
	 * @return eine Liste aller in der Datenbank vorhandenen Produkte
	 */
	List<Produkt> getAlleProdukte(Session session);
	
	//TODO Noch nicht implementiert
	/**
	 * Falls die gewuenschten Mengen der angegebenen Produkte vorhanden sind, werden diese ausgelagert
	 * anderenfalls wird false zurueckgegeben und die fehlenden Produkte werden nachbestellt
	 * (Dabei sind Menge und Lieferant abhaengig vom Orderbuch)
	 * 
	 * @param bestellListe Liste von ProduktId - Menge - Paaren
	 * @param session Die aktuelle Session
	 * @return true falls alle uebergebenen Produkte in den angegebenen Mengen vorhanden sind,
	 * 		   sonst false
	 * */
	boolean lagereAus(List<IntIntTuple> bestellListe, Session session);
	
	//TODO Noch nicht implementiert
	/**
	 * Erstellt eine Wareneingangsmeldung fuer die angegebene Bestellung
	 * @param produktId Die ID des Produkts, das geliefert wurde
	 * @param produktMenge Die Liefermenge, des Produkts
	 * @param datum Datum des Wareneingangs
	 * @param lieferantenName Name des Lieferanten, der das Produkt geliefert Hat
	 * @param lieferschein Eine Repraesentation des Lieferscheins(Erstmal nur ein Object)
	 * @param session Die aktuelle Session
	 * */
	void meldeWareneingang(int produktId, int produktMenge, Date datum, String lieferantenName, Object lieferschein, Session session);
	
	//TODO Noch nicht implementiert
	/**
	 * @param session Die aktuelle Session
	 * @return Eine Liste mit Informationsobjekten aller ausstehenden Bestellungen.
	 * */
	List<Bestellung> getAusstehendeBestellungen(Session session);	
	
	/**
	 * @param produktId Die ProduktId des gesuchten Produkts
	 * @param session Die aktuelle Session.
	 * @return Das Produkt mit der ProduktId produktId.
	 * */
	Produkt getProdukt(int produktId, Session session);
	
	/**
	 * @param produkt Das Produkt, das mit dem Angebot verbunden werden soll
	 * @param angebot Das Angebot, das mit dem Produkt verbunden werden soll
	 * @param session Die aktuelle Session.
	 * */
	public void verbindeProduktMitAngebot(Produkt produkt, Angebot angebot, Session session);
	
	/**
	 * @param produkt Das Produkt, das von dem Angebot getrennt werden soll
	 * @param angebot Das Angebot, das von dem Produkt getrennt werden soll
	 * @param session Die aktuelle Session.
	 * */
	public void trenneProduktUndAngebot(Produkt produkt, Angebot angebot, Session session);
}