package hes.produktMgmt;

import hes.auftragMgmt.Angebot;
import hes.auftragMgmt.Auftrag;

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

	@ManyToMany
	@JoinTable(name="Join_Produkt_Auftrag", joinColumns={@JoinColumn(name="produktId")},
	inverseJoinColumns={@JoinColumn(name="auftragId")})
	private List<Auftrag> auftraege;
	
	@OneToMany(targetEntity=Warenausgangsmeldung.class, mappedBy="produkt")
	private List<Warenausgangsmeldung> warenausgangsmeldungen;
	
	public Produkt() {}
	
	public Produkt(String name, int lagerbestand, float preis) {
		this.name = name;
		this.lagerbestand = lagerbestand;
		this.preis = preis;
		this.angebote = new ArrayList<Angebot>();
		this.auftraege = new ArrayList<Auftrag>();
		this.warenausgangsmeldungen = new ArrayList<Warenausgangsmeldung>();
	}

	public int getProduktId() {
		return produktId;
	}

	public String getName() {
		return name;
	}

	public int getLagerbestand() {
		return lagerbestand;
	}

	public float getPreis() {
		return preis;
	}
	
	public void setProduktId(int produktId) {
		this.produktId = produktId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAngebote(List<Angebot> angebote) {
		this.angebote = angebote;
	}

	public void setAuftraege(List<Auftrag> auftraege) {
		this.auftraege = auftraege;
	}

	public void setLagerbestand(int lagerbestand) {
		this.lagerbestand = lagerbestand;
	}
	
	public void setPreis(float preis) {
		this.preis = preis;
	}
	
	public List<Warenausgangsmeldung> getWarenausgangsmeldungen() {
		return warenausgangsmeldungen;
	}

	public void setWarenausgangsmeldungen(
			List<Warenausgangsmeldung> warenausgangsmeldungen) {
		this.warenausgangsmeldungen = warenausgangsmeldungen;
	}

	public List<Angebot> getAngebote() {
		return angebote;
	}

	public List<Auftrag> getAuftraege() {
		return auftraege;
	}

	public Produkt erhoeheLagerbestand(int menge) {
		this.lagerbestand = lagerbestand + menge;
		return this;
	}

	public void addAngebot(Angebot angebot) {
		angebote.add(angebot);
	}
	
	public void addAuftrag(Auftrag auftrag) {
		auftraege.add(auftrag);
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
