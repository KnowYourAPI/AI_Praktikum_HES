package hes.produktMgmt;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;


@Entity
public class Produkt {

	@Id
	@TableGenerator(name="produktId", table="produktPrimaryKeyTable", pkColumnName="produktPrimaryKey", pkColumnValue="nextProduktKey", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="produktId")
	private int produktId;
	@Column(nullable=false)
	private String name;
	@Column(nullable=false)
	private int lagerbestand;

	public Produkt() {}
	
	public Produkt(String name, int lagerbestand) {
		this.name = name;
		this.lagerbestand = lagerbestand;
	}

	public int getProduktId() {
		return produktId;
	}

	public String getName() {
		return name;
	}

	public int getLagerbestand() {
		return lagerbestand;
	}

	@Override
	public String toString() {
		return "Produkt [produktId=" + produktId + ", name=" + name
				+ ", lagerbestand=" + lagerbestand + "]";
	}
	
}
