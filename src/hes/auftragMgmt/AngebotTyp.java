package hes.auftragMgmt;

import hes.kundeMgmt.KundeTyp;
import hes.produktMgmt.ProduktTyp;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class AngebotTyp implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	int angebotId; 
	Date gueltigAb; 
	Date gueltigBis; 
	float gesamtPreis; 
	KundeTyp kunde;
	private Map<ProduktTyp, Integer> produktUmfang;
	
	public AngebotTyp(int angebotId, Date gueltigAb, Date gueltigBis, float gesamtPreis, KundeTyp kunde, Map<ProduktTyp, Integer> produktUmfang) {
		this.angebotId = angebotId; 
		this.gueltigAb = gueltigAb; 
		this.gueltigBis = gueltigBis;
		this.gesamtPreis = gesamtPreis;
		this.kunde = kunde; 
		this.produktUmfang = produktUmfang;
	}

	public int getAngebotId() {
		return angebotId;
	}

	public Date getGueltigAb() {
		return gueltigAb;
	}

	public Date getGueltigBis() {
		return gueltigBis;
	}

	public float getGesamtPreis() {
		return gesamtPreis;
	}

	public KundeTyp getKunde() {
		return kunde;
	}

	public Map<ProduktTyp, Integer> getProduktUmfang() {
		return produktUmfang;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + angebotId;
		result = prime * result + Float.floatToIntBits(gesamtPreis);
		result = prime * result
				+ ((gueltigAb == null) ? 0 : gueltigAb.hashCode());
		result = prime * result
				+ ((gueltigBis == null) ? 0 : gueltigBis.hashCode());
		result = prime * result + ((kunde == null) ? 0 : kunde.hashCode());
		result = prime * result
				+ ((produktUmfang == null) ? 0 : produktUmfang.hashCode());
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
		AngebotTyp other = (AngebotTyp) obj;
		if (angebotId != other.angebotId)
			return false;
		if (Float.floatToIntBits(gesamtPreis) != Float
				.floatToIntBits(other.gesamtPreis))
			return false;
		if (gueltigAb == null) {
			if (other.gueltigAb != null)
				return false;
		} else if (!gueltigAb.equals(other.gueltigAb))
			return false;
		if (gueltigBis == null) {
			if (other.gueltigBis != null)
				return false;
		} else if (!gueltigBis.equals(other.gueltigBis))
			return false;
		if (kunde == null) {
			if (other.kunde != null)
				return false;
		} else if (!kunde.equals(other.kunde))
			return false;
		if (produktUmfang == null) {
			if (other.produktUmfang != null)
				return false;
		} else if (!produktUmfang.equals(other.produktUmfang))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "AngebotTyp [angebotId=" + angebotId + ", gueltigAb="
				+ gueltigAb + ", gueltigBis=" + gueltigBis + ", gesamtPreis="
				+ gesamtPreis + ", kunde=" + kunde + ", produktUmfang="
				+ produktUmfang + "]";
	}

	
}
