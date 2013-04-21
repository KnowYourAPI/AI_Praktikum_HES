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

}
