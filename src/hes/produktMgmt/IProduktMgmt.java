package hes.produktMgmt;


import java.util.Date;
import java.util.List;

import util.Tuple;

public interface IProduktMgmt {

	/**
	 * @return eine Liste aller in der Datenbank vorhandenen Produkte
	 */
	List<ProduktTyp> getAlleProdukte();
	
	/**
	 * Falls die gewuenschten Mengen der angegebenen Produkte vorhanden sind, werden diese ausgelagert
	 * anderenfalls wird false zurueckgegeben und die fehlenden Produkte werden nachbestellt
	 * (Dabei sind Menge und Lieferant abhaengig vom Orderbuch)
	 * 
	 * @param bestellListe Liste von ProduktId - Menge - Paaren
	 * @return true falls alle uebergebenen Produkte in den angegebenen Mengen vorhanden sind,
	 * 		   sonst false
	 * */
	boolean lagereAus(List<Tuple<Integer, Integer>> bestellListe);
	/**
	 * LISTENER(?) -> waren gehen analog ein
	 * Erstellt eine Wareneingangsmeldung fuer die angegebene Bestellung
	 * @param produktId Die ID des Produkts, das geliefert wurde
	 * @param produktMenge Die Liefermenge, des Produkts
	 * @param datum Datum des Wareneingangs
	 * @param lieferantenName Name des Lieferanten, der das Produkt geliefert Hat
	 * @param lieferschein Eine Repraesentation des Lieferscheins(Erstmal nur ein Object)
	 * */
	void meldeWareneingang(int produktID, int produktMenge, Date datum, String lieferantenName, Object lieferschein);
	
	/**
	 * @return Eine Liste mit Informationsobjekten aller ausstehenden Bestellungen.
	 * */
	List<BestellungTyp> getAusstehendeBestellungen();	
}