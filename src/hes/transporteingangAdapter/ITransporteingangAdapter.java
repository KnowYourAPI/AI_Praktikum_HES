package hes.transporteingangAdapter;

public interface ITransporteingangAdapter {
	
	/**
	 * @param lieferungId Die ID der Lieferung, die als erfolgt markiert werden soll
	 * */
	void markiereLieferungAlsErfolgt(int lieferungId);

}
