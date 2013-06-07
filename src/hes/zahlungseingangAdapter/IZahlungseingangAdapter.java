package hes.zahlungseingangAdapter;

import java.rmi.RemoteException;

public interface IZahlungseingangAdapter {
	
	/**
	 * @param rechnungId Die ID der Rechnung, die (teilweise) beglichen wurde
	 * @param betrag Der eingegangene Betrag
	 * @throws RemoteException 
	 * */
	void  meldeZahlungsEingang(int rechnungId, float betrag) throws RemoteException;

	/**
	 * Started den Message-Queue-Listener, 
	 * der eingehende Nachrichten vom Hapsar-System empfaengt
	 * 
	 * @param queueName Der Name der Queue, ueber die die Kommunikation mit dem Hapsar-System stattfindet.
	 * @param brokerHost Der Name bzw. die IP des Servers, auf dem der MQ-Broker laeuft.
	 * */
	void startup(String queueName, String brokerHost);
}
