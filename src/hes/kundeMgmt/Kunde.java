package hes.kundeMgmt;

import hes.auftragMgmt.IAngebot;
import hes.auftragMgmt.IAuftrag;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

import util.AdressTyp;

@Entity
public class Kunde implements IKunde {

	@Id
	@TableGenerator(name="kundeid", table="kundePrimaryKeyTable", pkColumnName="kundePrimaryKey", pkColumnValue="nextKundeKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="kundeid")
	private int kundeId;
	@Column(nullable=false)
	private String name;
	@Column(nullable=false)
	private AdressTyp adresse;
	@Column(nullable=false)
	private List<IAngebot> angebote;
	@Column(nullable=false)
	private List<IAuftrag> auftraege;
	
	public Kunde(String name, AdressTyp adresse) {
		this.name = name;
		this.adresse = adresse;
		this.angebote = new ArrayList<IAngebot>();
		this.auftraege = new ArrayList<IAuftrag>();
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public AdressTyp getAdresse() {
		return adresse;
	}

}
