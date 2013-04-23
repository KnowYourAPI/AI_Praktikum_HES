package hes.auftragMgmt;

import hes.kundeMgmt.Kunde;

import java.util.List;

public interface IAuftragMgmt {

	/**
	 * @param kundenId Der Kunde, fuer den das Angebot erstellt werden soll.
	 * @return Der AngebotTyp des erstellten Angebots.
	 * */
	Angebot erstelleAngebot(Kunde kunden);
	
	/**
	 * @param angebotId Die ID des Angebots, zu dem das Produkt hinzugefuegt werden soll.
	 * @param produktId Die ID des hinzuzufuegenden Produkts.  
	 * @param menge Die gewuenschte Menge des hinzuzufuegenden Produkts.
	 * @return Das AngebotsTypobjekt, das die Information ueber das veraenderte Angebot enthaelt.
	 * */
	Angebot fuegeProduktZuAngebotHinzu(int angebotId, int produktId, int menge);
	
	/**
	 * @param angebotId Die ID des Angebots, aus dem ein Produkt entfernt werden soll.
	 * @param produktId Die ID des zu entfernenden Produkts.
 	 * @return Das AngebotsTypobjekt, das die Information ueber das veraenderte Angebot enthaelt.
	 * */
	Angebot entferneProduktAusAngebot(int angebotId, int produktId);
	
	/**
	 * @param angebotId Die ID des Angebots, aus dem ein Auftrag erstellt werden soll.
	 * @return Die ID des erstellten Auftrags. 
	 * */
	Auftrag erstelleAuftrag(int angebotId);
	
	/**
	 * @param produktID Die ID des Produktes, das in den Auftraegen vorhanden sein soll.
	 * @return Eine Liste aller nicht abgeschlossenen Auftraege im System, die das Produkt mit der angegebenen ProduktID enthalten.
	 * */
	List<Auftrag> getNichtAbgeschlosseneAuftraege(int produktID);
	
	/**
	 * @param auftragId Die ID des als abgeschlossen zu markierenden Auftrags.
	 * */
	void markiereAuftragAlsAbgeschlossen(int auftragId);
	
	AngebotTyp getAngebotTyp(Angebot angebot);
	
	AuftragTyp getAuftragTyp(Auftrag auftrag);

}