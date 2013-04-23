package hes.kundeMgmt;

import hes.auftragMgmt.Angebot;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;


@Entity
public class Kunde {
	
	@Id
	@TableGenerator(name="kundeid", table="kundePrimaryKeyTable", pkColumnName="kundePrimaryKey", pkColumnValue="nextKundeKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="kundeid")
	private int kundeId;

	@Column(nullable=false)
	private String name;
	@OneToOne(cascade=CascadeType.ALL)
	private Adresse adresse;
	@OneToMany(targetEntity=Angebot.class, mappedBy="kunde")
	private List<Angebot> angebote;
	
	public Kunde() {}
	
	public Kunde(String name, AdressTyp adressTyp) {
		this.name = name;
		this.adresse = new Adresse(adressTyp);
		this.angebote = new ArrayList<Angebot>();
	}
	
	public int getKundeId() {
		return kundeId;
	}

	public void setKundeId(int kundeId) {
		this.kundeId = kundeId;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public AdressTyp getAdresse() {
		return new AdressTyp(adresse);
	}
	
	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public List<Angebot> getAngebote() {
		return angebote;
	}

	public void setAngebote(List<Angebot> angebote) {
		this.angebote = angebote;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((adresse == null) ? 0 : adresse.hashCode());
		result = prime * result
				+ ((angebote == null) ? 0 : angebote.hashCode());
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
		Kunde other = (Kunde) obj;
		if (adresse == null) {
			if (other.adresse != null)
				return false;
		} else if (!adresse.equals(other.adresse))
			return false;
		if (angebote == null) {
			if (other.angebote != null)
				return false;
		} else if (!angebote.equals(other.angebote))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
