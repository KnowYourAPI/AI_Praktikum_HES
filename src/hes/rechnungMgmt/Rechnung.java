package hes.rechnungMgmt;

import hes.auftragMgmt.Auftrag;

import java.util.ArrayList;
import java.util.Date;
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
public class Rechnung {
	
	@Id
	@TableGenerator(name="rechnungId", table="rechnungPrimaryKeyTable", pkColumnName="rechnungPrimaryKey", pkColumnValue="nextRechnungKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="rechnungId")
	private int rechnungId;
	@Column(nullable=false)
	private Date rechnungsDatum;
	@Column(nullable=false)
	private boolean istBezahlt;
	@OneToMany(targetEntity=Zahlungseingang.class, mappedBy="rechnung", cascade=CascadeType.ALL)
	private List<Zahlungseingang> zahlungseingaenge;
	@OneToOne
	private Auftrag auftrag;
	@Column(nullable=false)
	private float insgesamtGezahlterBetrag;
	
	public Rechnung() {}
	
	public Rechnung(Auftrag auftrag) {
		this.rechnungsDatum = new Date();
		this.istBezahlt = false;
		this.zahlungseingaenge = new ArrayList<Zahlungseingang>();
		this.auftrag = auftrag;
		this.insgesamtGezahlterBetrag = 0;
	}
	
	public boolean addiereZahlungseingang(float betrag) {
		insgesamtGezahlterBetrag += betrag;
		istBezahlt = insgesamtGezahlterBetrag >= auftrag.getGesamtpreis();
		return istBezahlt;
	}
	
	public int getAuftragId() {
		return auftrag.getAuftragId();
	}
	
	public int getRechnungId() {
		return rechnungId;
	}
	
	public void setRechnungId(int rechnungId) {
		this.rechnungId = rechnungId;
	}
	
	public Date getRechnungsDatum() {
		return rechnungsDatum;
	}
	
	public void setRechnungsDatum(Date rechnungsDatum) {
		this.rechnungsDatum = rechnungsDatum;
	}
	
	public boolean isIstBezahlt() {
		return istBezahlt;
	}
	
	public void setIstBezahlt(boolean istBezahlt) {
		this.istBezahlt = istBezahlt;
	}
	
	public List<Zahlungseingang> getZahlungseingaenge() {
		return zahlungseingaenge;
	}
	
	public void setZahlungseingaenge(List<Zahlungseingang> zahlungseingaenge) {
		this.zahlungseingaenge = zahlungseingaenge;
	}

	public Auftrag getAuftrag() {
		return auftrag;
	}

	public void setAuftrag(Auftrag auftrag) {
		this.auftrag = auftrag;
	}
}
