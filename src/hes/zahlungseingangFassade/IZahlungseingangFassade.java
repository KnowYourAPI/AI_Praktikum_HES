package hes.zahlungseingangFassade;

public interface IZahlungseingangFassade {
	
	/**
	 * @param rechnungId Die ID der Rechnung, die (teilweise) beglichen wurde
	 * @param betrag Der eingegangene Betrag
	 * */
	void meldeZahlungsEingang(int rechnungId, float betrag);

}
