package hes.rechnungMgmt;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

@Entity
public class Rechnung {
	
	@Id
	@TableGenerator(name="rechnungId", table="rechnungPrimaryKeyTable", pkColumnName="rechnungPrimaryKey", pkColumnValue="nextRechnungKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="rechnungId")
	private int rechnungId;
	@Column(nullable=false)
	private Date rechnungsDatum;
	@Column(nullable=false)
	private boolean istBezahlt;
	@OneToMany(targetEntity=Zahlungseingang.class, mappedBy="rechnung", cascade=CascadeType.ALL)
	private List<Zahlungseingang> zahlungseingaenge;

}
