package hes.fassade;

import hes.auftragMgmt.AngebotTyp;
import hes.produktMgmt.ProduktTyp;

import java.util.Date;
import java.util.List;

public interface IFassade {
	
	/**
	 * @param name Der Name des Produktes, das angelegt werden soll
	 * @param lagerbestand Der Lagerbestand des Produktes bei dessen Anlegung in der Datenbank
	 * @return Die ID des gerade angelegten Produkts
	 * */
	int legeProduktAn(String name, int lagerbestand);
	
	/**
	 * @param firmenName Der Name der Kundenfirma, deren ID zurueckgegeben werden soll
	 * @return Die ID des Kunden mit dem angegebenen Firmennamen, ansonsten -1
	 * */
	int getKundeId(String firmenName);
	
	/**
	 * @param kundenId Die ID des Kunden, fuer den das Angebot erstellt werden soll.
	 * @return Die ID des erstellten Angebots.
	 * */
	int erstelleAngebot(int kundenId);
	
	/**
	 * @return eine Liste aller in der Datenbank vorhandenen Produkte
	 */
	List<ProduktTyp> getAlleProdukte();
	
	/**
	 * @param angebotId Die ID des Angebots, zu dem das Produkt hinzugefuegt werden soll.
	 * @param produktId Die ID des hinzuzufuegenden Produkts.  
	 * @param menge Die gewuenschte Menge des hinzuzufuegenden Produkts.
	 * @return Das AngebotsTypobjekt, das die Information ueber das veraenderte Angebot enthaelt.
	 * */
	AngebotTyp fuegeProduktZuAngebotHinzu(int angebotId, int produktId, int menge);
	
	/**
	 * @param angebotId Die ID des Angebots, aus dem ein Produkt entfernt werden soll.
	 * @param produktId Die ID des zu entfernenden Produkts.
 	 * @return Das AngebotsTypobjekt, das die Information ueber das veraenderte Angebot enthaelt.
	 * */
	AngebotTyp entferneProduktAusAngebot(int angebotId, int produktId);
	
	/**
	 * LISTENER(?) -> waren gehen analog ein
	 * Erstellt eine Wareneingangsmeldung fuer die angegebene Bestellung
	 * @param produktId Die ID des Produkts, das geliefert wurde
	 * @param produktMenge Die Liefermenge, des Produkts
	 * @param datum Datum des Wareneingangs
	 * @param lieferantenName Name des Lieferanten, der das Produkt geliefert Hat
	 * @param lieferschein Eine Repraesentation des Lieferscheins(Erstmal nur ein Object)
	 * */
	void meldeWareneingang(int produktId, int produktMenge, Date datum, String lieferantenName, Object lieferschein);
	
	//Aufruf auf IRechnungMgmt
	/**
	 * @param rechnungId Die ID der Rechnung, deren zugehoeriger Auftrag gesucht wird
	 * @return Die ID des Auftrags, auf den sich die Rechnung mit der rechnungID bezieht
	 * */
	int getAuftragId(int rechnungId);
	
	/**
	 * @param auftragId Die ID des als abgeschlossen zu markierenden Auftrags.
	 * */
	void markiereAuftragAlsAbgeschlossen(int auftragId);

}