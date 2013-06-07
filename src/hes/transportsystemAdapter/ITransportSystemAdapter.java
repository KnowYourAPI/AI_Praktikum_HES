package hes.transportsystemAdapter;

public interface ITransportSystemAdapter {
	
	/**
	 * @param lieferungId Die ID der Lieferung, die als erfolgt markiert werden soll
	 * */
	void markiereLieferungAlsErfolgt(int lieferungId);

}
