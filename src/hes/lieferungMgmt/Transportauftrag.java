package hes.lieferungMgmt;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class Transportauftrag {
	
	@Id
	@TableGenerator(name="transportauftragId", table="transportauftragPrimaryKeyTable", pkColumnName="transportauftragPrimaryKey", pkColumnValue="nextTransportauftragKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="transportauftragId")
	private int transportauftragId;
	
	private Date ausgangsdatum,
				 lieferdatum;
	
	@Column(nullable=false)
	private boolean lieferungErfolgt;
	@Column(nullable=false)
	private String transportdienstleister;
	
	public Transportauftrag() {}
	
	public Transportauftrag(Date ausgangsdatum, Date lieferdatum,
			boolean lieferungErfolgt, String transportdienstleister) {
		this.ausgangsdatum = ausgangsdatum;
		this.lieferdatum = lieferdatum;
		this.lieferungErfolgt = lieferungErfolgt;
		this.transportdienstleister = transportdienstleister;
	}

	public int getTransportauftragId() {
		return transportauftragId;
	}

	public void setTransportauftragId(int transportauftragId) {
		this.transportauftragId = transportauftragId;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((ausgangsdatum == null) ? 0 : ausgangsdatum.hashCode());
		result = prime * result
				+ ((lieferdatum == null) ? 0 : lieferdatum.hashCode());
		result = prime * result + (lieferungErfolgt ? 1231 : 1237);
		result = prime
				* result
				+ ((transportdienstleister == null) ? 0
						: transportdienstleister.hashCode());
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
		Transportauftrag other = (Transportauftrag) obj;
		if (ausgangsdatum == null) {
			if (other.ausgangsdatum != null)
				return false;
		} else if (!ausgangsdatum.equals(other.ausgangsdatum))
			return false;
		if (lieferdatum == null) {
			if (other.lieferdatum != null)
				return false;
		} else if (!lieferdatum.equals(other.lieferdatum))
			return false;
		if (lieferungErfolgt != other.lieferungErfolgt)
			return false;
		if (transportdienstleister == null) {
			if (other.transportdienstleister != null)
				return false;
		} else if (!transportdienstleister.equals(other.transportdienstleister))
			return false;
		return true;
	}

}
