package hes.rechnungMgmt;

public interface IRechnungMgmt {

	/**
	 * @param auftragID Die des Auftrags, auf den sich die Rechnung bezieht
	 * @return Die Rechnungsnummer, der angelegten Rechnung
	 * */
	int legeRechnungAn(int auftragID);
	
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
	boolean meldeZahlungsEingang(int rechnungID, float betrag);
	
	/**
	 * @param rechnungID Die ID der Rechnung, deren zugehoeriger Auftrag gesucht wird
	 * @return Die ID des Auftrags, auf den sich die Rechnung mit der rechnungID bezieht
	 * */
	int getAuftragID(int rechnungID);
	
}
