package hes.lieferungMgmt;

import javax.persistence.CascadeType;
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
	private Transportauftrag transportauftrag;
	
	public Lieferung() {}
	
	public Lieferung(Transportauftrag transportauftrag) {
		this.transportauftrag = transportauftrag;
	}

	public int getLieferungId() {
		return lieferungId;
	}

	public void setLieferungId(int lieferungId) {
		this.lieferungId = lieferungId;
	}

	public Transportauftrag getTransportauftrag() {
		return transportauftrag;
	}

	public void setTransportauftrag(Transportauftrag transportauftrag) {
		this.transportauftrag = transportauftrag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((transportauftrag == null) ? 0 : transportauftrag.hashCode());
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
		Lieferung other = (Lieferung) obj;
		if (transportauftrag == null) {
			if (other.transportauftrag != null)
				return false;
		} else if (!transportauftrag.equals(other.transportauftrag))
			return false;
		return true;
	}

}
