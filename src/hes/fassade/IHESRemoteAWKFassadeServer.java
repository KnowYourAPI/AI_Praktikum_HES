package hes.fassade;

import hes.auftragMgmt.AngebotTyp;
import hes.auftragMgmt.AuftragTyp;
import hes.kundeMgmt.AdressTyp;
import hes.produktMgmt.ProduktTyp;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

public interface IHESRemoteAWKFassadeServer extends Remote {
	
	/**
	 * @param name Der Name des anzulegenden Kunden
	 * @param adresse Die Adresse des anzulegenden Kunden
	 * @return Die ID des soeben angelegten Kunden
	 * */
	int legeKundeAn(String name, AdressTyp adresse) throws RemoteException;
	
	/**
	 * @param name Der Name des Produktes, das angelegt werden soll
	 * @param lagerbestand Der Lagerbestand des Produktes bei dessen Anlegung in der Datenbank
	 * @return Die ID des gerade angelegten Produkts
	 * */
	int legeProduktAn(String name, int lagerbestand, float preis) throws RemoteException;
	
	/**
	 * @param firmenName Der Name der Kundenfirma, deren ID zurueckgegeben werden soll
	 * @return Die ID des Kunden mit dem angegebenen Firmennamen, ansonsten -1
	 * */
	int getKundeId(String firmenName) throws RemoteException;
	
	/**
	 * @param kundenId Die ID des Kunden, fuer den das Angebot erstellt werden soll.
	 * @return Die ID des erstellten Angebots.
	 * */
	int erstelleAngebot(int kundenId) throws RemoteException;
	
	/**
	 * @return eine Liste aller in der Datenbank vorhandenen Produkte
	 */
	List<ProduktTyp> getAlleProdukte() throws RemoteException;
	
	/**
	 * @param angebotId Die ID des Angebots, zu dem das Produkt hinzugefuegt werden soll.
	 * @param produktId Die ID des hinzuzufuegenden Produkts.  
	 * @param menge Die gewuenschte Menge des hinzuzufuegenden Produkts.
	 * @return Das AngebotsTypobjekt, das die Information ueber das veraenderte Angebot enthaelt.
	 * */
	AngebotTyp fuegeProduktZuAngebotHinzu(int angebotId, int produktId, int menge) throws RemoteException;
	
	/**
	 * @param angebotId Die ID des Angebots, aus dem ein Produkt entfernt werden soll.
	 * @param produktId Die ID des zu entfernenden Produkts.
 	 * @return Das AngebotsTypobjekt, das die Information ueber das veraenderte Angebot enthaelt.
	 * */
	AngebotTyp entferneProduktAusAngebot(int angebotId, int produktId) throws RemoteException;
	
	/**
	 * @param angebotId Die ID des Angebots, aus dem ein Auftrag erstellt werden soll.
	 * @return Die ID des erstellten Auftrags. 
	 * */
	AuftragTyp erstelleAuftrag(int angebotId) throws RemoteException;
	
	/**
	 * LISTENER(?) -> waren gehen analog ein
	 * Erstellt eine Wareneingangsmeldung fuer die angegebene Bestellung
	 * @param produktId Die ID des Produkts, das geliefert wurde
	 * @param produktMenge Die Liefermenge, des Produkts
	 * @param datum Datum des Wareneingangs
	 * @param lieferantenName Name des Lieferanten, der das Produkt geliefert Hat
	 * @param lieferschein Eine Repraesentation des Lieferscheins(Erstmal nur ein Object)
	 * */
	void meldeWareneingang(int produktId, int produktMenge, Date datum, String lieferantenName, Object lieferschein) throws RemoteException;
	
	//Aufruf auf IRechnungMgmt
	/**
	 * @param rechnungId Die ID der Rechnung, deren zugehoeriger Auftrag gesucht wird
	 * @return Die ID des Auftrags, auf den sich die Rechnung mit der rechnungID bezieht
	 * */
	int getAuftragId(int rechnungId) throws RemoteException;
	
	/**
	 * @param lieferungId Die ID der Lieferung, die als erfolgt markiert werden soll
	 * @param ausgangsdatum Das Ausgangsdatum, an dem die Lieferung beauftragt wurde
	 * @param lieferdatum Das Lieferdatum, an dem die Lieferung erfolgt
	 * @param transportdienstleister Der Transportdienstleister, der die Lieferung durchfuehrt
	 * */
	void markiereLieferungAlsErfolgt(int lieferungId, Date ausgangsdatum, Date lieferdatum, String transportdienstleister) throws RemoteException;

	/**
	 * @param auftragId Die ID des als abgeschlossen zu markierenden Auftrags.
	 * */
	void markiereAuftragAlsAbgeschlossen(int auftragId) throws RemoteException;
	
	/**
	 * @param rechnungId Die ID der Rechnung, fuer die ein Zahlungseingang gemeldet werden soll
	 * @param betrag Die Hoehe des Zahlungseingangs
	 * @return True, wenn die Rechnung beglichen wurde, sonst false
	 * */
	boolean meldeZahlungseingang(int rechnungId, float betrag) throws RemoteException;

}
