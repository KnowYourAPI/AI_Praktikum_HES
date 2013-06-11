package hes.lieferungMgmt;

import java.util.Date;

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
	 * @param ausgangsdatum Das Ausgangsdatum, an dem die Lieferung beauftragt wurde
	 * @param lieferdatum Das Lieferdatum, an dem die Lieferung erfolgt
	 * @param transportdienstleister Der Transportdienstleister, der die Lieferung durchfuehrt
	 * */
	void markiereLieferungAlsErfolgt(int lieferungId, Date ausgangsdatum, Date lieferdatum, String transportdienstleister, Session session);
	
	
}