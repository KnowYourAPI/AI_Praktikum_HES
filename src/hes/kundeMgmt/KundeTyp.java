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

	public List<AngebotTyp> getAngebotTypen() {
		List<AngebotTyp> angebotTypListe = new ArrayList<AngebotTyp>();
		
		for(Angebot angebot : kunde.getAngebote()) {
			angebotTypListe.add(angebot.getAngebotTyp());
		}
		
		return angebotTypListe;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((kunde == null) ? 0 : kunde.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KundeTyp other = (KundeTyp) obj;
		if (kunde == null) {
			if (other.kunde != null)
				return false;
		} else if (!kunde.equals(other.kunde))
			return false;
		return true;
	}
	
	

}
