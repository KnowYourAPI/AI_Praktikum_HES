package hes.auftragMgmt;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;

@Entity
public class Auftrag {
	
	@Id
	@TableGenerator(name="auftragid", table="auftragPrimaryKeyTable", pkColumnName="auftragPrimaryKey", pkColumnValue="nextAuftragKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="auftragid")
	private int auftragId;
	@Column(nullable=false)
	private boolean istAbgeschlossen;
	@Column(nullable=false)
	private Date beauftragtAm;
	@OneToOne
	private Angebot angebot;
	
	public Auftrag() {}
	
	public Auftrag(Angebot angebot) {
		this.istAbgeschlossen = false;
		this.beauftragtAm = new Date();
		this.angebot = angebot;
	}

	public float getGesamtpreis() {
		return angebot.getGesamtPreis();
	}

	public int getAuftragId() {
		return auftragId;
	}

	public void setAuftragId(int auftragId) {
		this.auftragId = auftragId;
	}

	public boolean isIstAbgeschlossen() {
		return istAbgeschlossen;
	}

	public void setIstAbgeschlossen(boolean istAbgeschlossen) {
		this.istAbgeschlossen = istAbgeschlossen;
	}

	public Date getBeauftragtAm() {
		return beauftragtAm;
	}

	public void setBeauftragtAm(Date beauftragtAm) {
		this.beauftragtAm = beauftragtAm;
	}

	public Angebot getAngebot() {
		return angebot;
	}

	public void setAngebot(Angebot angebot) {
		this.angebot = angebot;
	}
}
