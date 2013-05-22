package hes.kundeMgmt;

import java.io.Serializable;

public class AdressTyp implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String strasse;
	private String hausnummer;
	private String postleitzahl;
	private String ortsname;
	
	public AdressTyp(String strasse, String hausnummer, String postleitzahl, String ortsname) {
		this.strasse = strasse;
		this.hausnummer = hausnummer;
		this.postleitzahl = postleitzahl;
		this.ortsname = ortsname;
	}

	public String getStrasse() {
		return strasse;
	}

	public String getHausnummer() {
		return hausnummer;
	}

	public String getPostleitzahl() {
		return postleitzahl;
	}

	public String getOrtsname() {
		return ortsname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((hausnummer == null) ? 0 : hausnummer.hashCode());
		result = prime * result
				+ ((ortsname == null) ? 0 : ortsname.hashCode());
		result = prime * result
				+ ((postleitzahl == null) ? 0 : postleitzahl.hashCode());
		result = prime * result + ((strasse == null) ? 0 : strasse.hashCode());
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
		if (hausnummer == null) {
			if (other.hausnummer != null)
				return false;
		} else if (!hausnummer.equals(other.hausnummer))
			return false;
		if (ortsname == null) {
			if (other.ortsname != null)
				return false;
		} else if (!ortsname.equals(other.ortsname))
			return false;
		if (postleitzahl == null) {
			if (other.postleitzahl != null)
				return false;
		} else if (!postleitzahl.equals(other.postleitzahl))
			return false;
		if (strasse == null) {
			if (other.strasse != null)
				return false;
		} else if (!strasse.equals(other.strasse))
			return false;
		return true;
	}

}
