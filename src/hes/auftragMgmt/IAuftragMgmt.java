package hes.auftragMgmt;

import hes.kundeMgmt.Kunde;
import hes.produktMgmt.Produkt;

import java.util.List;

public interface IAuftragMgmt {

	/**
	 * @param kunde Der Kunde, fuer den das Angebot erstellt werden soll.
	 * @return Das erstellte Angebot.
	 * */
	Angebot erstelleAngebot(Kunde kunde);
	
	/**
	 * @param angebot Das Angebot, zu dem das Produkt hinzugefuegt werden soll.
	 * @param produkt Das dem Angebot hinzuzufuegende Produkt.  
	 * @param menge Die gewuenschte Menge des hinzuzufuegenden Produkts.
	 * @return Das Angebot, dem das Prokukt in entspechender Menge hinzugefuegt wurde.
	 * */
	Angebot fuegeProduktZuAngebotHinzu(Angebot angebot, Produkt produkt, int menge);
	
	/**
	 * @param angebot Das Angebot, aus das Produkt entfernt werden soll.
	 * @param produkt Das aus dem Angebot zu entfernende Produkt.
 	 * @return Das Angebot, aus dem das Produkt entfernt wurde.
	 * */
	Angebot entferneProduktAusAngebot(Angebot angebot, Produkt produkt);
	
	/**
	 * @param angebot Das Angebot, aus dem ein Auftrag erstellt wird.
	 * @return Der erstellte Auftrag. 
	 * */
	Auftrag erstelleAuftrag(Angebot angebot);
	
	/**
	 * @param produkt Das Produkt, das in den Auftraegen vorhanden sein soll.
	 * @return Eine Liste aller nicht abgeschlossenen Auftraege im System, die das Produkt enthalten.
	 * */
	List<Auftrag> getNichtAbgeschlosseneAuftraege(Produkt produkt);
	
	/**
	 * @param auftragId Die ID des als abgeschlossen zu markierenden Auftrags.
	 * */
	void markiereAuftragAlsAbgeschlossen(int auftragId);
	
	/**
	 * @param angebot Das Angebot, von dem ein AngebotsTyp-Objekt erstellt werden soll.
	 * @return Das AngebotsTyp-Objekt, zu dem uebergebenen Angebot.
	 * */
	AngebotTyp getAngebotTyp(Angebot angebot);
	
	/**
	 * @param auftrag Der Auftrag, von dem ein AuftragsTyp-Objekt erstellt werden soll.
	 * @return Das AuftragsTyp-Objekt, zu dem uebergebenen Auftrag.
	 * */
	AuftragTyp getAuftragTyp(Auftrag auftrag);

}