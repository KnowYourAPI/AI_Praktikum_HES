package hes.auftragMgmt;

import hes.kundeMgmt.Kunde;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

@Entity
public class Angebot {
	
	@Id
	@TableGenerator(name="angebotid", table="angebotPrimaryKeyTable", pkColumnName="angebotPrimaryKey", pkColumnValue="nextAngebotKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="angebotid")
	private int angebotId;
	
	@ManyToOne
	@JoinColumn(name="kunde_id")
	private Kunde kunde;

}
