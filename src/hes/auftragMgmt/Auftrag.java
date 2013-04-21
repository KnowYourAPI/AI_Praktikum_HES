package hes.auftragMgmt;

import java.util.List;

import hes.kundeMgmt.Kunde;
import hes.produktMgmt.Produkt;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity
public class Auftrag {
	
	@Id
	@TableGenerator(name="auftragid", table="auftragPrimaryKeyTable", pkColumnName="auftragPrimaryKey", pkColumnValue="nextAuftragKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="auftragid")
	private int auftragId;
	
	@ManyToOne
	@JoinColumn(name="kunde_id")
	private Kunde kunde;

	@ManyToMany
	@JoinTable(name="Join_Produkt_Auftrag", joinColumns={@JoinColumn(name="auftragId")},
	inverseJoinColumns={@JoinColumn(name="produktId")})
	private List<Produkt> produkte;
}
