package hes.auftragMgmt;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hes.kundeMgmt.Kunde;
import hes.kundeMgmt.KundeTyp;
import hes.produktMgmt.Produkt;
import hes.produktMgmt.ProduktTyp;

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

import org.hibernate.annotations.CollectionOfElements;

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
	
	@CollectionOfElements
	private Map<Produkt, Integer> produktUmfang;
	
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
		this.produktUmfang = new HashMap<Produkt,Integer>();
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
	
	public Map<Produkt, Integer> getProduktUmfang() {
		return produktUmfang;
	}

	public void setProduktUmfang(HashMap<Produkt, Integer> produktUmfang) {
		this.produktUmfang = produktUmfang;
	}

	public Angebot fuegeProduktHinzu(Produkt produkt, int menge) {
		if(!produkte.contains(produkt)){
			produkte.add(produkt);
		}
		gesamtPreis += (produkt.getPreis() * menge);
		if (!produktUmfang.keySet().contains(produkt)){
			produktUmfang.put(produkt, 0);			
		}
		produktUmfang.put(produkt, produktUmfang.get(produkt) + menge);
		return this;
	}
	
	public Angebot entferneProdukt(Produkt produkt) {
		if(produkte.contains(produkt)){
			produkte.remove(produkt);
			int menge = produktUmfang.get(produkt);
			gesamtPreis -= (produkt.getPreis() * menge);		
			produktUmfang.remove(produkt);
		}
		return this;
	}
	
	public AngebotTyp getAngebotTyp() {
		Date gueltigAb = new Date(this.gueltigAb.getTime());
		Date gueltigBis = new Date(this.gueltigBis.getTime());
		KundeTyp kundeTyp = this.kunde.getKundeTyp();
		Map<ProduktTyp, Integer> produktUmfangTyp = new HashMap<ProduktTyp, Integer>();


		    for (Map.Entry<Produkt, Integer> eintrag : produktUmfang.entrySet()) {
		    	ProduktTyp produktTyp = eintrag.getKey().getProduktTyp();
		    	int menge = eintrag.getValue();
		    	produktUmfangTyp.put(produktTyp, menge);
		    }
		return new AngebotTyp(this.angebotId, gueltigAb, gueltigBis, this.gesamtPreis, kundeTyp, produktUmfangTyp);
	}
	
	@Override
	public String toString() {
		return "Angebot [angebotId=" + angebotId + ", gueltigAb=" + gueltigAb
				+ ", gueltigBis=" + gueltigBis + ", gesamtPreis=" + gesamtPreis
				+ ", kunde=" + kunde + ", Produktumfang= " + produktUmfang + "]";
	}	
	
}
