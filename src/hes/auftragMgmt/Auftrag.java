package hes.auftragMgmt;

import java.util.List;

import hes.produktMgmt.Produkt;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.TableGenerator;

@Entity
public class Auftrag {
	
	@Id
	@TableGenerator(name="auftragid", table="auftragPrimaryKeyTable", pkColumnName="auftragPrimaryKey", pkColumnValue="nextAuftragKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="auftragid")
	private int auftragId;

	@ManyToMany
	@JoinTable(name="Join_Produkt_Auftrag", joinColumns={@JoinColumn(name="auftragId")},
	inverseJoinColumns={@JoinColumn(name="produktId")})
	private List<Produkt> produkte;
	
	public Auftrag() {}
	
	public Auftrag(Angebot angebot) {
		
	}
	//TODO: Benoetigt in RechnungMgmt
	public float getGesamtpreis() {
		return 0;
	}
	
	public int getAuftragId() {
		return auftragId;
	}

	public void setAuftragId(int auftragId) {
		this.auftragId = auftragId;
	}

	public List<Produkt> getProdukte() {
		return produkte;
	}

	public void setProdukte(List<Produkt> produkte) {
		this.produkte = produkte;
	}
}
