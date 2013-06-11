package hes.transportsystemAdapter;

public interface ITransportSystemAdapter {

	/**
	 * @param lieferungId Die ID der Lieferung, deren Transportauftrag versendet werden soll.
	 * @return Die Parameter der markierelieferungAlsErfolgt-Methode in einem Object[] der Form
	 *         {TransportAuftragNr, Ausgangsdatum, LieferungErfolgt, Lieferdatum, Transportdienstleister}
	 * */
	Object[] verschickeTransportauftrag(int lieferungId);
	
}
