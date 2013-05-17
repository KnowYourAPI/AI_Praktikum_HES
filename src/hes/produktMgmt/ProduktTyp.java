package hes.produktMgmt;

import java.io.Serializable;

public class ProduktTyp implements Serializable {

	private static final long serialVersionUID = 1L;

	private int produktId;
	private String name;
	private int lagerbestand;
	private float preis;
	
	public ProduktTyp(int produktId, String name, int lagerbestand, float preis) {
		this.produktId = produktId;
		this.name = name;
		this.lagerbestand = lagerbestand;
		this.preis = preis;
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

	public float getPreis() {
		return preis;
	}
	
	@Override
	public String toString() {
		return "ProduktTyp [produktId=" + produktId + ", name=" + name
				+ ", lagerbestand=" + lagerbestand + ", preis=" + preis + "]";
	}

	
}
