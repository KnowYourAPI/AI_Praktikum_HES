package hes.lieferungMgmt;

import hes.auftragMgmt.Auftrag;

import org.hibernate.Session;

public interface ILieferungMgmt {

	/**
	 * Soll diese Methode automatisch oder ueber eine Benutzeroberflaeche aufgerufen werden?
	 * -> automatisch
	 * 
	 * @param auftrag Der Auftrag, zu dem die zu erstellende Lieferung gehoert
	 * @return Das Lieferungsobjekt, fuer die Lieferung
	 * */
	Lieferung erstelleLieferung(Auftrag auftrag, Session session);
	
	/**
	 * @param lieferungId Die ID der Lieferung, die als erfolgt markiert werden soll
	 * */
	void markiereLieferungAlsErfolgt(int lieferungId, Session session);
	
}