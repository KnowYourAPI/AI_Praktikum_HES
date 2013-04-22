package hes.produktMgmt;


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
	 * @param session Die aktuelle Session.
	 * @return eine Liste aller in der Datenbank vorhandenen Produkte
	 */
	List<Produkt> getAlleProdukte(Session session);
	
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
	/**
	 * LISTENER(?) -> waren gehen analog ein
	 * Erstellt eine Wareneingangsmeldung fuer die angegebene Bestellung
	 * @param produktId Die ID des Produkts, das geliefert wurde
	 * @param produktMenge Die Liefermenge, des Produkts
	 * @param datum Datum des Wareneingangs
	 * @param lieferantenName Name des Lieferanten, der das Produkt geliefert Hat
	 * @param lieferschein Eine Repraesentation des Lieferscheins(Erstmal nur ein Object)
	 * @param session Die aktuelle Session
	 * */
	void meldeWareneingang(int produktId, int produktMenge, Date datum, String lieferantenName, Object lieferschein, Session session);
	
	/**
	 * @param session Die aktuelle Session
	 * @return Eine Liste mit Informationsobjekten aller ausstehenden Bestellungen.
	 * */
	List<BestellungTyp> getAusstehendeBestellungen(Session session);	
}