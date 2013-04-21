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

}
