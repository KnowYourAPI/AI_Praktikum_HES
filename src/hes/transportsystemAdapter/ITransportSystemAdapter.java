package hes.transportsystemAdapter;

public interface ITransportSystemAdapter {

	/**
	 * @param lieferungId Die ID der Lieferung, deren Transportauftrag versendet werden soll.
	 * */
	void verschickeTransportauftrag(int lieferungId);
	
}
