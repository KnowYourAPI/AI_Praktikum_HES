package hes.auftragMgmt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hes.kundeMgmt.Kunde;
import hes.produktMgmt.Produkt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity
public class Angebot {
	
	@Id
	@TableGenerator(name="angebotid", table="angebotPrimaryKeyTable", pkColumnName="angebotPrimaryKey", pkColumnValue="nextAngebotKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="angebotid")
	private int angebotId;
	
	@Column(nullable=false)
	private Date gueltigAb;
	
	@Column(nullable=false)
	private Date gueltigBis;
	
	@Column(nullable=false)
	private float gesamtPreis;
	
	@ManyToOne
	@JoinColumn(name="kunde_id")
	private Kunde kunde;
	
	@ManyToMany
	@JoinTable(name="Join_Produkt_Angebot", joinColumns={@JoinColumn(name="angebotId")},
	inverseJoinColumns={@JoinColumn(name="produktId")})
	private List<Produkt> produkte;
	
	public Angebot() {}
	
	public Angebot(Kunde kunde) {
		this.gueltigAb = new Date();
		long sekundenZweierWochen = 1000*60*60*24*14;
		this.gueltigBis = new Date(this.gueltigAb.getTime() + sekundenZweierWochen);
		this.gesamtPreis = 0.0F;
		this.kunde = kunde;	
		this.produkte = new ArrayList<Produkt>();
	}

	public int getAngebotId() {
		return angebotId;
	}

	public void setAngebotId(int angebotId) {
		this.angebotId = angebotId;
	}

	public Date getGueltigAb() {
		return gueltigAb;
	}

	public void setGueltigAb(Date gueltigAb) {
		this.gueltigAb = gueltigAb;
	}

	public Date getGueltigBis() {
		return gueltigBis;
	}

	public void setGueltigBis(Date gueltigBis) {
		this.gueltigBis = gueltigBis;
	}

	public float getGesamtPreis() {
		return gesamtPreis;
	}

	public void setGesamtPreis(float gesamtPreis) {
		this.gesamtPreis = gesamtPreis;
	}

	public Kunde getKunde() {
		return kunde;
	}

	public void setKunde(Kunde kunde) {
		this.kunde = kunde;
	}

	public List<Produkt> getProdukte() {
		return produkte;
	}

	public void setProdukte(List<Produkt> produkte) {
		this.produkte = produkte;
	}
	
	public void addProdukt(Produkt produkt) {
		produkte.add(produkt);
	}
	
	@Override
	public String toString() {
		return "Angebot [angebotId=" + angebotId + ", gueltigAb=" + gueltigAb
				+ ", gueltigBis=" + gueltigBis + ", gesamtPreis=" + gesamtPreis
				+ ", kunde=" + kunde + "]";
	}	
	
}
