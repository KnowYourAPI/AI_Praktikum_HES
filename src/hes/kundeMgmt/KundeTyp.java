package hes.kundeMgmt;

import hes.auftragMgmt.Angebot;
import hes.auftragMgmt.AngebotTyp;

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
		return new AdressTyp(kunde.getAdresse());
	}

	public List<AngebotTyp> getAngebote() {
		List<AngebotTyp> angebotTypListe = new ArrayList<AngebotTyp>();
		
		for(Angebot angebot : kunde.getAngebote()) {
			angebotTypListe.add(angebot.getAngebotTyp());
		}
		
		return angebotTypListe;
	}

}
