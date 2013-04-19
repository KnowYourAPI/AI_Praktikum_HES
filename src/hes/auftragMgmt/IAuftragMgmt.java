package hes.auftragMgmt;

import java.util.List;

public interface IAuftragMgmt {

	/**
	 * @param kundenId Die ID des Kunden, fuer den das Angebot erstellt werden soll.
	 * @return Die ID des erstellten Angebots.
	 * */
	int erstelleAngebot(int kundenId);
	
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
	 * @param angebotId Die ID des Angebots, aus dem ein Auftrag erstellt werden soll.
	 * @return Die ID des erstellten Auftrags. 
	 * */
	AuftragTyp erstelleAuftrag(int angebotId);
	
	/**
	 * @param produktID Die ID des Produktes, das in den Auftraegen vorhanden sein soll.
	 * @return Eine Liste aller nicht abgeschlossenen Auftraege im System, die das Produkt mit der angegebenen ProduktID enthalten.
	 * */
	List<AuftragTyp> getNichtAbgeschlosseneAuftraege(int produktID);
	
	/**
	 * @param auftragId Die ID des als abgeschlossen zu markierenden Auftrags.
	 * */
	void markiereAuftragAlsAbgeschlossen(int auftragId);

}