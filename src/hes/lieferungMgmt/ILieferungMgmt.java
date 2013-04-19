package hes.lieferungMgmt;

public interface ILieferungMgmt {

	/**
	 * Soll diese Methode automatisch oder ueber eine Benutzeroberflaeche aufgerufen werden
	 * -> automatisch
	 * 
	 * @param auftragID Die ID des Auftrags, zu dem die zu erstellende Lieferung gehoert
	 * @return Die ID des Lieferungsobjekts, fuer die Lieferung
	 * */
	int erstelleLieferungUndTransportauftrag(int auftragID);
	
	/**
	 * @param lieferungId Die ID der Lieferung, die als erfolgt markiert werden soll
	 * */
	void markiereLieferungAlsErfolgt(int lieferungId);
	
}