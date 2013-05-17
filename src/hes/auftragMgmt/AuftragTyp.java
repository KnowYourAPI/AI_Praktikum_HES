package hes.auftragMgmt;

import java.io.Serializable;
import java.util.Date;


public class AuftragTyp implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int auftragId;
	private boolean istAbgeschlossen;
	private Date beauftragtAm;
	private AngebotTyp angebotTyp;
	
	
	public AuftragTyp(int auftragId, boolean istAbgeschlossen, Date beauftragtAm, AngebotTyp angebotTyp){
		this.auftragId = auftragId;
		this.istAbgeschlossen = istAbgeschlossen;
		this.beauftragtAm = beauftragtAm;
		this.angebotTyp = angebotTyp;
	}

	public int getAuftragId() {
		return auftragId;
	}

	public boolean isIstAbgeschlossen() {
		return istAbgeschlossen;
	}

	public Date getBeauftragtAm() {
		return beauftragtAm;
	}

	public AngebotTyp getAngebotTyp() {
		return angebotTyp;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((angebotTyp == null) ? 0 : angebotTyp.hashCode());
		result = prime * result + auftragId;
		result = prime * result
				+ ((beauftragtAm == null) ? 0 : beauftragtAm.hashCode());
		result = prime * result + (istAbgeschlossen ? 1231 : 1237);
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
		AuftragTyp other = (AuftragTyp) obj;
		if (angebotTyp == null) {
			if (other.angebotTyp != null)
				return false;
		} else if (!angebotTyp.equals(other.angebotTyp))
			return false;
		if (auftragId != other.auftragId)
			return false;
		if (beauftragtAm == null) {
			if (other.beauftragtAm != null)
				return false;
		} else if (!beauftragtAm.equals(other.beauftragtAm))
			return false;
		if (istAbgeschlossen != other.istAbgeschlossen)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AuftragTyp [auftragId=" + auftragId + ", istAbgeschlossen="
				+ istAbgeschlossen + ", beauftragtAm=" + beauftragtAm
				+ ", angebotTyp=" + angebotTyp + "]";
	}
	
	
}
