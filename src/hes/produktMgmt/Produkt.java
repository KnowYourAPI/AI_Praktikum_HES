package hes.produktMgmt;

import hes.auftragMgmt.Angebot;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;



@Entity
public class Produkt {

	@Id
	@TableGenerator(name="produktId", table="produktPrimaryKeyTable", pkColumnName="produktPrimaryKey", pkColumnValue="nextProduktKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="produktId")
	private int produktId;
	
	@Column(nullable=false)
	private String name;
	
	@Column(nullable=false)
	private int lagerbestand;
	
	@Column(nullable= false)
	private float preis;
	
	@ManyToMany
	@JoinTable(name="Join_Produkt_Angebot", joinColumns={@JoinColumn(name="produktId")},
	inverseJoinColumns={@JoinColumn(name="angebotId")})
	private List<Angebot> angebote;
	
	@OneToMany(targetEntity=Warenausgangsmeldung.class, mappedBy="produkt")
	private List<Warenausgangsmeldung> warenausgangsmeldungen;
	
	public Produkt() {}
	
	public Produkt(String name, int lagerbestand, float preis) {
		this.name = name;
		this.lagerbestand = lagerbestand;
		this.preis = preis;
		this.angebote = new ArrayList<Angebot>();
		this.warenausgangsmeldungen = new ArrayList<Warenausgangsmeldung>();
	}

	public int getProduktId() {
		return produktId;
	}

	public void setProduktId(int produktId) {
		this.produktId = produktId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLagerbestand() {
		return lagerbestand;
	}

	public void setLagerbestand(int lagerbestand) {
		this.lagerbestand = lagerbestand;
	}

	public float getPreis() {
		return preis;
	}

	public void setPreis(float preis) {
		this.preis = preis;
	}

	public List<Angebot> getAngebote() {
		return angebote;
	}

	public void setAngebote(List<Angebot> angebote) {
		this.angebote = angebote;
	}

	public List<Warenausgangsmeldung> getWarenausgangsmeldungen() {
		return warenausgangsmeldungen;
	}

	public void setWarenausgangsmeldungen(
			List<Warenausgangsmeldung> warenausgangsmeldungen) {
		this.warenausgangsmeldungen = warenausgangsmeldungen;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + Float.floatToIntBits(preis);
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
		Produkt other = (Produkt) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (Float.floatToIntBits(preis) != Float.floatToIntBits(other.preis))
			return false;
		return true;
	}

	public Produkt erhoeheLagerbestand(int menge) {
		this.lagerbestand = lagerbestand + menge;
		return this;
	}

	public void addAngebot(Angebot angebot) {
		angebote.add(angebot);
	}
	
	public void entferneAngebot(Angebot angebot) {
		angebote.remove(angebot);
	}
	
	public void addWarenausgangsmeldung(Warenausgangsmeldung warenausgangsmeldung) {
		warenausgangsmeldungen.add(warenausgangsmeldung);
	}
	
	public ProduktTyp getProduktTyp() {
		return new ProduktTyp(this.produktId, this.name, this.lagerbestand, this.preis);
	}
	
	@Override
	public String toString() {
		return "Produkt [produktId=" + produktId + ", name=" + name
				+ ", lagerbestand=" + lagerbestand + ", preis=" + preis + "]";
	}
	
}
