package hes.produktMgmt;

public class ProduktTyp {

	private int produktId;
	private String name;
	private int lagerbestand;
	
	public ProduktTyp(int produktId, String name, int lagerbestand) {
		this.produktId = produktId;
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
		return "ProduktTyp [produktId=" + produktId + ", name=" + name
				+ ", lagerbestand=" + lagerbestand + "]";
	}

	
}
