package hes.rechnungMgmt;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class Rechnung {
	
	@Id
	@TableGenerator(name="rechnungId", table="rechnungPrimaryKeyTable", pkColumnName="rechnungPrimaryKey", pkColumnValue="nextRechnungKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="rechnungId")
	private int rechnungId;
	private Date rechnungsDatum;
	private boolean istBezahlt;
	private List<Zahlungseingang> zahlungseingaenge;

}
