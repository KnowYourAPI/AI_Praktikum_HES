package hes.kundeMgmt;

import org.hibernate.Session;


public interface IKundeMgmt {

	/**
	 * @param kundeId Die ID des zu holenden Kunden.
	 * @return Den Kunden mit der angegebenen ID, sonst null. 
	 * */
	Kunde getKunde(int kundeId, Session session);
	
	/**
	 * @param name Der name des anzulegenden Kunden.
	 * @param adresse Die Adresse des anzulegenden Kunden.
	 * @return Die ID des neu angelegten Kunden.
	 * */	
	int erstelleKunde(String name, AdressTyp adresse, Session session);
	
	/**
	 * @param firmenName Der Name der Kundenfirma, deren ID zurueckgegeben werden soll
	 * @return Die ID des Kunden mit dem angegebenen Firmennamen, ansonsten -1
	 * */
	int getKundeId(String firmenName, Session session);
	
}
