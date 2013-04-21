package hes.kundeMgmt;

public class AdressTyp {
	
	private Adresse adresse;
	
	public AdressTyp(String strasse, String hausnummer, String postleitzahl, String ortsname) {
		this.adresse = new Adresse(strasse, hausnummer, postleitzahl, ortsname);
	}
	
	public AdressTyp(Adresse adresse) {
		this.adresse = adresse;
	}

	public int getAdressTypId() {
		return adresse.getAdressTypId();
	}

	public String getStrasse() {
		return adresse.getStrasse();
	}

	public String getHausnummer() {
		return adresse.getHausnummer();
	}

	public String getPostleitzahl() {
		return adresse.getPostleitzahl();
	}

	public String getOrtsname() {
		return adresse.getOrtsname();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
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
		AdressTyp other = (AdressTyp) obj;
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		} else if (!adresse.equals(other.adresse))
			return false;
		return true;
	}

}
