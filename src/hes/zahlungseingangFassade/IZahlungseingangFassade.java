package hes.zahlungseingangFassade;

public interface IZahlungseingangFassade {
	
	/**
	 * @param rechnungID Die ID der Rechnung, die (teilweise) beglichen wurde
	 * @param betrag Der eingegangene Betrag
	 * @return true, wenn die Rechnung beglichen wurde, false wenn noch weiteres Geld zu ueberweisen ist
	 * */
	boolean meldeZahlungsEingang(int rechnungID, float betrag);

}
