package hes.auftragMgmt;

import java.util.Date;


public class AuftragTyp {
	
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
	public String toString() {
		return "AuftragTyp [auftragId=" + auftragId + ", istAbgeschlossen="
				+ istAbgeschlossen + ", beauftragtAm=" + beauftragtAm
				+ ", angebotTyp=" + angebotTyp + "]";
	}
	
	
}
