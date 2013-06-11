package hes.lieferungMgmt;

import java.util.Date;

import hes.auftragMgmt.Auftrag;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

@Entity
public class Lieferung {
	
	@Id
	@TableGenerator(name="lieferungId", table="lieferungPrimaryKeyTable", pkColumnName="lieferungPrimaryKey", pkColumnValue="nextLieferungKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="lieferungId")
	private int lieferungId;
	
	@OneToOne(cascade=CascadeType.ALL)
	private Auftrag auftrag;
	private Date ausgangsdatum;
	private Date lieferdatum;
	
	@Column(nullable=false)
	private boolean lieferungErfolgt;
	private String transportdienstleister;
	
	public Lieferung() {}
	
	public Lieferung(Auftrag auftrag) {
		this.auftrag = auftrag;
	}

	public int getLieferungId() {
		return lieferungId;
	}

	public void setLieferungId(int lieferungId) {
		this.lieferungId = lieferungId;
	}

	public Auftrag getAuftrag() {
		return auftrag;
	}

	public void setAuftrag(Auftrag auftrag) {
		this.auftrag = auftrag;
	}

	public Date getAusgangsdatum() {
		return ausgangsdatum;
	}

	public void setAusgangsdatum(Date ausgangsdatum) {
		this.ausgangsdatum = ausgangsdatum;
	}

	public Date getLieferdatum() {
		return lieferdatum;
	}

	public void setLieferdatum(Date lieferdatum) {
		this.lieferdatum = lieferdatum;
	}

	public boolean isLieferungErfolgt() {
		return lieferungErfolgt;
	}

	public void setLieferungErfolgt(boolean lieferungErfolgt) {
		this.lieferungErfolgt = lieferungErfolgt;
	}

	public String getTransportdienstleister() {
		return transportdienstleister;
	}

	public void setTransportdienstleister(String transportdienstleister) {
		this.transportdienstleister = transportdienstleister;
	}
}
