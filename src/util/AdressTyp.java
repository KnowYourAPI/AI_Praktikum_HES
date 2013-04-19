package util;

public class AdressTyp {
	
	private String strasse;
	private int hausnummer;
	private int postleitzahl;
	private String ortsname;
	
	public AdressTyp(String strasse, int hausnummer, int postleitzahl, String ortsname) {
		this.strasse = strasse;
		this.hausnummer = hausnummer;
		this.postleitzahl = postleitzahl;
		this.ortsname = ortsname;
	}

	public String getStrasse() {
		return strasse;
	}

	public int getHausnummer() {
		return hausnummer;
	}

	public int getPostleitzahl() {
		return postleitzahl;
	}

	public String getOrtsname() {
		return ortsname;
	}

}
