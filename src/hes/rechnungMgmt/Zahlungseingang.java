package hes.rechnungMgmt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity
public class Zahlungseingang {
	
	@Id
	@TableGenerator(name="zahlungseingangId", table="zahlungseingangPrimaryKeyTable", pkColumnName="zahlungseingangPrimaryKey", pkColumnValue="nextZahlungseingangKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="zahlungseingangId")
	private int zahlungseingangId;
	@ManyToOne
	@JoinColumn(name="rechnung_id")
	private Rechnung rechnung;
	@Column(nullable=false)
	private float betrag;
	
	public int getZahlungseingangId() {
		return zahlungseingangId;
	}
	
	public void setZahlungseingangId(int zahlungseingangId) {
		this.zahlungseingangId = zahlungseingangId;
	}
	
	public Rechnung getRechnung() {
		return rechnung;
	}
	
	public void setRechnung(Rechnung rechnung) {
		this.rechnung = rechnung;
	}
	
	public float getBetrag() {
		return betrag;
	}
	
	public void setBetrag(float betrag) {
		this.betrag = betrag;
	}
}
