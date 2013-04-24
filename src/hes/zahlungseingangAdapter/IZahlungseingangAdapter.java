package hes.zahlungseingangAdapter;

public interface IZahlungseingangAdapter {
	
	/**
	 * @param rechnungId Die ID der Rechnung, die (teilweise) beglichen wurde
	 * @param betrag Der eingegangene Betrag
	 * */
	void meldeZahlungsEingang(int rechnungId, float betrag);

}
