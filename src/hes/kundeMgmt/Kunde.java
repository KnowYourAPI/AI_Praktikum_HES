package hes.kundeMgmt;

import hes.auftragMgmt.Angebot;
import hes.auftragMgmt.Auftrag;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

import util.AdressTyp;

@Entity
public class Kunde {
	
	@Id
	@TableGenerator(name="kundeid", table="kundePrimaryKeyTable", pkColumnName="kundePrimaryKey", pkColumnValue="nextKundeKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="kundeid")
	private int kundeId;

	@Column(nullable=false)
	private String name;
	@OneToOne
	private AdressTyp adresse;
	@OneToMany(targetEntity=Angebot.class, mappedBy="kunde")
	private List<Angebot> angebote;
	@OneToMany(targetEntity=Auftrag.class, mappedBy="kunde")
	private List<Auftrag> auftraege;
	
	public Kunde(String name, AdressTyp adresse) {
		this.name = name;
		this.adresse = adresse;
		this.angebote = new ArrayList<Angebot>();
		this.auftraege = new ArrayList<Auftrag>();
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
		return adresse;
	}
	
	public void setAdresse(AdressTyp adresse) {
		this.adresse = adresse;
	}

	public List<Angebot> getAngebote() {
		return angebote;
	}

	public void setAngebote(List<Angebot> angebote) {
		this.angebote = angebote;
	}

	public List<Auftrag> getAuftraege() {
		return auftraege;
	}

	public void setAuftraege(List<Auftrag> auftraege) {
		this.auftraege = auftraege;
	}

}
