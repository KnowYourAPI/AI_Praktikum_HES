package hes.rechnungMgmt;

import org.hibernate.Session;

import hes.auftragMgmt.Auftrag;

public interface IRechnungMgmt {

	/**
	 * @param auftrag Der Auftrag, auf den sich die Rechnung bezieht
	 * @return Die angelegte Rechnung
	 * */
	Rechnung legeRechnungAn(Auftrag auftrag, Session session);
	
//	WIRD JETZT INTERN IN meldeZahlungsEingang AUFGERUFEN
//	/**
//	 * @param auftragId Die ID des Auftrags, dessen Rechnung ueberprueft werden soll.
//	 * @return True, falls die Rechnung fuer den Auftrag bezahlte wurde, sonst false.
//	 * */
//	boolean istRechnungFuerAuftragBezahlt(int auftragId);
	
	/**
	 * @param rechnungID Die ID der Rechnung, die (teilweise) beglichen wurde
	 * @param betrag Der eingegangene Betrag
	 * @return true, wenn die Rechnung beglichen wurde, false wenn noch weiteres Geld zu ueberweisen ist
	 * */
	boolean meldeZahlungsEingang(int rechnungId, float betrag, Session session);
	
	/**
	 * @param rechnungID Die ID der Rechnung, deren zugehoeriger Auftrag gesucht wird
	 * @return Die ID des Auftrags, auf den sich die Rechnung mit der rechnungID bezieht
	 * */
	int getAuftragId(int rechnungId, Session session);
	
}
