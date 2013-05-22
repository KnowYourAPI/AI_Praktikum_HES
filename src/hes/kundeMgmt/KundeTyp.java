package hes.kundeMgmt;

import hes.auftragMgmt.AngebotTyp;

import java.io.Serializable;
import java.util.List;

public class KundeTyp implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private AdressTyp adresse;
	private List<AngebotTyp> angebotTypen;
	
	public KundeTyp(String name, AdressTyp adresse) {
		this.name = name;
		this.adresse = adresse;
	}

	public String getName() {
		return name;
	}

	public AdressTyp getAdresse() {
		return adresse;
	}

//	public List<AngebotTyp> getAngebotTypen() {
////		List<AngebotTyp> angebotTypListe = new ArrayList<AngebotTyp>();
////		
////		for(Angebot angebot : kunde.getAngebote()) {
////			angebotTypListe.add(angebot.getAngebotTyp());
////		}
////		
////		return angebotTypListe;
//		return angebotTypen;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
		result = prime * result
				+ ((angebotTypen == null) ? 0 : angebotTypen.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		} else if (!adresse.equals(other.adresse))
			return false;
		if (angebotTypen == null) {
			if (other.angebotTypen != null)
				return false;
		} else if (!angebotTypen.equals(other.angebotTypen))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
