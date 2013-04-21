package hes.auftragMgmt;

import hes.kundeMgmt.Kunde;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Auftrag {
	
	@ManyToOne
	@JoinColumn(name="kunde_id")
	private Kunde kunde;

}
