package hes.auftragMgmt;

import hes.kundeMgmt.KundeTyp;
import hes.produktMgmt.ProduktTyp;

import java.util.Date;
import java.util.Map;

public class AngebotTyp {
	
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
	public String toString() {
		return "AngebotTyp [angebotId=" + angebotId + ", gueltigAb="
				+ gueltigAb + ", gueltigBis=" + gueltigBis + ", gesamtPreis="
				+ gesamtPreis + ", kunde=" + kunde + ", produktUmfang="
				+ produktUmfang + "]";
	}

	
}
