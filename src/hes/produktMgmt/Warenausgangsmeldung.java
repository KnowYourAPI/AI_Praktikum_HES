package hes.produktMgmt;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity
public class Warenausgangsmeldung {

	@Id
	@TableGenerator(name="warenausgangsmeldungId", table="warenausgangsmeldungPrimaryKeyTable", pkColumnName="warenausgangsmeldungPrimaryKey", pkColumnValue="nextWarenausgangsmeldungKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="warenausgangsmeldungId")
	int warenausgangsmeldungId;
	
	@Column(nullable=false)
	Date datum;
	
	@Column(nullable=false)
	int menge;
	
	@ManyToOne
	@JoinColumn(name="produktId")
	Produkt produkt;
	
	public Warenausgangsmeldung() {}
	
	public Warenausgangsmeldung(int menge, Produkt produkt){
		this.datum = new Date();
		this.menge = menge;
//		this.produkt = produkt;
	}

	public int getWarenausgangsmeldungId() {
		return warenausgangsmeldungId;
	}

	public void setWarenausgangsmeldungId(int warenausgangsmeldungId) {
		this.warenausgangsmeldungId = warenausgangsmeldungId;
	}

	public Date getDatum() {
		return datum;
	}

	public void setDatum(Date datum) {
		this.datum = datum;
	}

	public int getMenge() {
		return menge;
	}

	public void setMenge(int menge) {
		this.menge = menge;
	}

	public Produkt getProdukt() {
		return produkt;
	}

	public void setProdukt(Produkt produkt) {
		this.produkt = produkt;
	}

	@Override
	public String toString() {
		return "Warenausgangsmeldung [warenausgangsmeldungId="
				+ warenausgangsmeldungId + ", datum=" + datum + ", menge="
				+ menge + "]";
	}
	
	
	
}
