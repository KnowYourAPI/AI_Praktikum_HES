package hes.kundeMgmt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;


@Entity
public class Adresse {
	
	@Id
	@TableGenerator(name="adressTypId", table="adressTypPrimaryKeyTable", pkColumnName="adressTypPrimaryKey", pkColumnValue="nextAdressTypKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="adressTypId")
	private int adressTypId;
	@Column(nullable=false)
	private String strasse;
	@Column(nullable=false)
	private String hausnummer;
	@Column(nullable=false)
	private String postleitzahl;
	@Column(nullable=false)
	private String ortsname;
	
	public Adresse() {}
	
	public Adresse(String strasse, String hausnummer, String postleitzahl, String ortsname) {
		this.strasse = strasse;
		this.hausnummer = hausnummer;
		this.postleitzahl = postleitzahl;
		this.ortsname = ortsname;
	}
	
	public Adresse(AdressTyp adressTyp) {
		this.strasse = adressTyp.getStrasse();
		this.hausnummer = adressTyp.getHausnummer();
		this.postleitzahl = adressTyp.getPostleitzahl();
		this.ortsname = adressTyp.getOrtsname();
	}

	public int getAdressTypId() {
		return adressTypId;
	}

	public void setAdressTypId(int adressTypId) {
		this.adressTypId = adressTypId;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getHausnummer() {
		return hausnummer;
	}

	public void setHausnummer(String hausnummer) {
		this.hausnummer = hausnummer;
	}

	public String getPostleitzahl() {
		return postleitzahl;
	}

	public void setPostleitzahl(String postleitzahl) {
		this.postleitzahl = postleitzahl;
	}

	public String getOrtsname() {
		return ortsname;
	}

	public void setOrtsname(String ortsname) {
		this.ortsname = ortsname;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + adressTypId;
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
		Adresse other = (Adresse) obj;
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
