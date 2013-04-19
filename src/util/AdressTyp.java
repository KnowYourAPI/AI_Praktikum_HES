package util;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class AdressTyp {
	
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

}
