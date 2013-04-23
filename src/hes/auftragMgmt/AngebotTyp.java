package hes.auftragMgmt;

import hes.kundeMgmt.KundeTyp;
import hes.produktMgmt.ProduktTyp;

import java.util.Date;
import java.util.List;

public class AngebotTyp {
	
	int angebotId; 
	Date gueltigAb; 
	Date gueltigBis; 
	float gesamtPreis; 
	KundeTyp kunde;
	List<ProduktTyp> produkte;
	
	public AngebotTyp(int angebotId, Date gueltigAb, Date gueltigBis, float gesamtPreis, KundeTyp kunde, List<ProduktTyp> produkte) {
		this.angebotId = angebotId; 
		this.gueltigAb = gueltigAb; 
		this.gueltigBis = gueltigBis;
		this.gesamtPreis = gesamtPreis;
		this.kunde = kunde; 
		this.produkte = produkte;
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

	public List<ProduktTyp> getProdukte() {
		return produkte;
	}
	
	

}
