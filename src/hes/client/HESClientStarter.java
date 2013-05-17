package hes.client;

import java.rmi.RemoteException;
import java.util.ArrayList;

import hes.client.redundanzMgmt.Dispatcher;
import hes.client.redundanzMgmt.HESRemoteClient;
import hes.client.redundanzMgmt.Monitor;
import hes.kundeMgmt.AdressTyp;

public class HESClientStarter {
	
	private static String HES1_SERVER = "localhost";
	private static String HES2_SERVER =	"localhost";	
	private static String HES1_NAME = "HES1";
	private static String HES2_NAME = "HES2";
	
	public static void main(String[] args) {
		HESRemoteClient hesRemoteClient1 = new HESRemoteClient(HES1_SERVER, HES1_NAME);
//		HESRemoteClient hesRemoteClient2 = new HESRemoteClient(HES2_SERVER, HES2_NAME);
		ArrayList<HESRemoteClient> remoteClients = new ArrayList<HESRemoteClient>();
		remoteClients.add(hesRemoteClient1);
//		remoteClients.add(hesRemoteClient2);
		Dispatcher dispatcher = new Dispatcher(remoteClients);
		Monitor monitor = new Monitor();
		
		monitor.addObserver(dispatcher);
		
		//Simpler Testaufruf:
		String name = "Max Mustermann";
		AdressTyp adresse = new AdressTyp("Musterweg", "42a", "12345", "Beispielstadt");
		
		try {
			System.out.println("Neuer Kunde, Id:" + dispatcher.legeKundeAn(name, adresse));
			int produktId1 = dispatcher.legeProduktAn("Tolles Produkt", 42, 0);
			System.out.println("Neues Produkt: " + produktId1);
			int produktId2 = dispatcher.legeProduktAn("Bibaum", 720, 21);
			System.out.println("Neues Produkt: " + produktId2);
			int angebotId = dispatcher.erstelleAngebot(1);
			System.out.println("Neues Angebot: " + angebotId);
			dispatcher.fuegeProduktZuAngebotHinzu(angebotId, produktId1, 25);
			System.out.println("Produkt zu Angebt hinzugefuegt.");
			dispatcher.fuegeProduktZuAngebotHinzu(angebotId, produktId2, 32);
			System.out.println("Produkt zu Angebt hinzugefuegt.");
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

}
