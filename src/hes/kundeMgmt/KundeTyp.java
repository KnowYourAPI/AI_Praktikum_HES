package hes.kundeMgmt;

import hes.auftragMgmt.Angebot;
import hes.auftragMgmt.Auftrag;

import java.util.ArrayList;
import java.util.List;


public class KundeTyp {
	
	private Kunde kunde;
	
	public KundeTyp(Kunde kunde) {
		this.kunde = kunde;
	}
	
	public int getKundeId() {
		return kunde.getKundeId();
	}

	public String getName() {
		return kunde.getName();
	}

	public AdressTyp getAdresse() {
		return kunde.getAdresse();
	}

	public List<Angebot> getAngebote() {
		return new ArrayList<Angebot>(kunde.getAngebote());
	}

	public List<Auftrag> getAuftraege() {
		return new ArrayList<Auftrag>(kunde.getAuftraege());
	}

}
