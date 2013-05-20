package hes.client;

import java.rmi.Naming;

import hes.kundeMgmt.AdressTyp;
import hes.redundanzMgmt.IRedundanzMgmt;

public class HESClientStarter {
	
	private static final String URL_PREFIX = "rmi://";
	private static final String REDUNDANZ_MGMT_SERVER = "localhost";
	private static final String REDUNDANZ_MGMT_NAME = "redundanzmgmt";
	
	public static void main(String[] args) throws Exception {
		String redundanzMgmtUrl = URL_PREFIX + REDUNDANZ_MGMT_SERVER + "/" + REDUNDANZ_MGMT_NAME;
		
		IRedundanzMgmt redundanzMgmt = (IRedundanzMgmt) Naming.lookup(redundanzMgmtUrl);
		
		//Simpler Testaufruf:
		String name = "Peter Mustermann";
		AdressTyp adresse = new AdressTyp("Musterweg", "42a", "12345", "Beispielstadt");

		System.out.println("Neuer Kunde, Id:" + redundanzMgmt.legeKundeAn(name, adresse));
		int produktId1 = redundanzMgmt.legeProduktAn("Tolles Produkt", 42, 0);
		System.out.println("Neues Produkt: " + produktId1);
		int produktId2 = redundanzMgmt.legeProduktAn("Bibaum", 720, 21);
		System.out.println("Neues Produkt: " + produktId2);
		int angebotId = redundanzMgmt.erstelleAngebot(1);
		System.out.println("Neues Angebot: " + angebotId);
		redundanzMgmt.fuegeProduktZuAngebotHinzu(angebotId, produktId1, 25);
		System.out.println("Produkt zu Angebot hinzugefuegt.");
		redundanzMgmt.fuegeProduktZuAngebotHinzu(angebotId, produktId2, 32);
		System.out.println("Produkt zu Angebot hinzugefuegt.");

	}

}
