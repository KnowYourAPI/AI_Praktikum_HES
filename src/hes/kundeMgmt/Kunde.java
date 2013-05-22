package hes.kundeMgmt;

import hes.auftragMgmt.Angebot;
import hes.auftragMgmt.AngebotTyp;
import hes.produktMgmt.ProduktTyp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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
public class Kunde implements Serializable {
	
	private static final long serialVersionUID = 1L;

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
	
	public void addAngebot(Angebot angebot) {
		angebote.add(angebot);
	}
	
	public KundeTyp getKundeTyp() {
		AdressTyp adressTyp = new AdressTyp(adresse.getStrasse(), adresse.getHausnummer(),
											adresse.getPostleitzahl(), adresse.getOrtsname());
		List<AngebotTyp> angebotTypen = new ArrayList<AngebotTyp>();
		
//		for(Angebot angebot : angebote) {
//			angebot.getAngebotTyp();
//			int angebotId = angebot.getAngebotId();
//			Date gueltigAb = angebot.getGueltigAb();
//			Date gueltigBis = angebot.getGueltigBis();
//			float gesamtPreis = angebot.getGesamtPreis();
//			KundeTyp kunde = new KundeTyp(name, adressTyp, );
//			Map<ProduktTyp, Integer> produktUmfang = angebot.getProduktUmfang();
//			new AngebotTyp(angebotId, gueltigAb, gueltigBis, gesamtPreis, kunde, produktUmfang);
//		}
		
		return new KundeTyp(name, adressTyp);
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

	public Adresse getAdresse() {
		return adresse;
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
