package hes.rechnungMgmt;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class Zahlungseingang {
	
	@Id
	@TableGenerator(name="zahlungseingangId", table="zahlungseingangPrimaryKeyTable", pkColumnName="zahlungseingangPrimaryKey", pkColumnValue="nextZahlungseingangKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="zahlungseingangId")
	private int zahlungseingangId;
	private Rechnung rechnung;
	private float betrag;

}
