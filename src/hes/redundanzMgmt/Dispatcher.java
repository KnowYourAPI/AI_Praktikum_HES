package hes.redundanzMgmt;

import hes.auftragMgmt.AngebotTyp;
import hes.auftragMgmt.AuftragTyp;
import hes.fassade.IHESRemoteAWKFassadeServer;
import hes.kundeMgmt.AdressTyp;
import hes.produktMgmt.ProduktTyp;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class Dispatcher implements Observer, IHESRemoteAWKFassadeServer {
	
	private final long HES_SUCHE_WARTEZEIT_IN_MILLISEKUNDEN = 3000;
	
	private Map<String, Boolean> hesInstanzZustaende;
	
	private List<HESRemoteClient> hesRemoteClients;
	
	private HESRemoteClient hesRemoteClient;
	
	public Dispatcher() {
		hesInstanzZustaende = new HashMap<String, Boolean>();
		hesRemoteClients = new ArrayList<HESRemoteClient>();
	}

	@Override
	public int legeKundeAn(String name, AdressTyp adresse) throws RemoteException {
		hesRemoteClient = waehleNaechstenClient();
		return hesRemoteClient.legeKundeAn(name, adresse);
	}

	@Override
	public int legeProduktAn(String name, int lagerbestand, float preis) throws RemoteException {
		hesRemoteClient = waehleNaechstenClient();
		return hesRemoteClient.legeProduktAn(name, lagerbestand, lagerbestand);
	}

	@Override
	public int getKundeId(String firmenName) throws RemoteException {
		hesRemoteClient = waehleNaechstenClient();
		return hesRemoteClient.getKundeId(firmenName);
	}

	@Override
	public int erstelleAngebot(int kundenId) throws RemoteException {
		hesRemoteClient = waehleNaechstenClient();
		return hesRemoteClient.erstelleAngebot(kundenId);
	}

	@Override
	public List<ProduktTyp> getAlleProdukte() throws RemoteException {
		hesRemoteClient = waehleNaechstenClient();
		return hesRemoteClient.getAlleProdukte();
	}

	@Override
	public AngebotTyp fuegeProduktZuAngebotHinzu(int angebotId, int produktId,
			int menge) throws RemoteException {
		hesRemoteClient = waehleNaechstenClient();
		return hesRemoteClient.fuegeProduktZuAngebotHinzu(angebotId, produktId, menge);
	}

	@Override
	public AngebotTyp entferneProduktAusAngebot(int angebotId, int produktId) throws RemoteException {
		hesRemoteClient = waehleNaechstenClient();
		return hesRemoteClient.entferneProduktAusAngebot(angebotId, produktId);
	}

	@Override
	public AuftragTyp erstelleAuftrag(int angebotId) throws RemoteException {
		hesRemoteClient = waehleNaechstenClient();
		return hesRemoteClient.erstelleAuftrag(angebotId);
	}

	@Override
	public void meldeWareneingang(int produktId, int produktMenge, Date datum,
			String lieferantenName, Object lieferschein) throws RemoteException {
		hesRemoteClient = waehleNaechstenClient();
		hesRemoteClient.meldeWareneingang(produktId, produktMenge, datum, lieferantenName, lieferschein);
	}

	@Override
	public int getAuftragId(int rechnungId) throws RemoteException {
		hesRemoteClient = waehleNaechstenClient();
		return hesRemoteClient.getAuftragId(rechnungId);
	}

	@Override
	public void markiereLieferungAlsErfolgt(int lieferungId) throws RemoteException {
		hesRemoteClient = waehleNaechstenClient();
		hesRemoteClient.markiereLieferungAlsErfolgt(lieferungId);
	}

	@Override
	public void markiereAuftragAlsAbgeschlossen(int auftragId) throws RemoteException {
		hesRemoteClient = waehleNaechstenClient();
		hesRemoteClient.markiereAuftragAlsAbgeschlossen(auftragId);
	}

	@Override
	public boolean meldeZahlungseingang(int rechnungId, float betrag) throws RemoteException {
		hesRemoteClient = waehleNaechstenClient();
		return hesRemoteClient.meldeZahlungseingang(rechnungId, betrag);
	}

	@Override
	public void update(Observable observable, Object object) {
		if (observable instanceof Monitor) {
			Object[] objectAry = (Object[]) object;
			
			//An/Ausschalten
			if(objectAry.length == 2) {
				String hesInstanzName = (String)objectAry[0];
				boolean istLebendig = (Boolean)objectAry[1];
				hesInstanzZustaende.put(hesInstanzName, istLebendig);
				hesInstanzZustaende.put(hesInstanzName, istLebendig);
			//Ping
			} else if (objectAry.length == 3) {
				String hesServer = (String)objectAry[0];
				String hesInstanzName = (String)objectAry[1];
				boolean istLebendig = (Boolean)objectAry[2];
				
				if(!hesInstanzZustaende.keySet().contains(hesInstanzName)) {
					System.out.println(hesInstanzName + " auf Rechner "  + hesServer + " beim Dispatcher registriert.");
					hesRemoteClients.add(new HESRemoteClient(hesServer, hesInstanzName));
				}
				
				hesInstanzZustaende.put(hesInstanzName, istLebendig);
			}
		}
	}

	private HESRemoteClient _waehleNaechstenClient() {
		int alterIndex = hesRemoteClients.indexOf(hesRemoteClient);
		int naechsterIndex =  alterIndex + 1;
		naechsterIndex = naechsterIndex == hesRemoteClients.size() ? 0 : naechsterIndex;
		boolean istErsterDurchlauf = true;
		
		while (istErsterDurchlauf) {
			if (alterIndex == naechsterIndex) {
				istErsterDurchlauf = false;				
			}
			HESRemoteClient naechsterHesRemoteClient = hesRemoteClients.get(naechsterIndex);
			if (istLebendig(naechsterHesRemoteClient)) {
				return hesRemoteClients.get(naechsterIndex);
			}
			naechsterIndex++;
			naechsterIndex = naechsterIndex == hesRemoteClients.size() ? 0 : naechsterIndex;
		}
		
		return null;
	}
	
	private HESRemoteClient waehleNaechstenClient() {
		HESRemoteClient naechsterClient = _waehleNaechstenClient();
		while (naechsterClient == null) {
			try {
				Thread.sleep(HES_SUCHE_WARTEZEIT_IN_MILLISEKUNDEN);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			naechsterClient = _waehleNaechstenClient();
		}
		System.out.println("Naechster Aufruf geht an: " + naechsterClient.getHesName());
		return naechsterClient;
	}

	private boolean istLebendig(HESRemoteClient client) {
		return hesInstanzZustaende.get(client.getHesName());
	}
	
	public int getAnzahlBearbeiteterAnfragen(String hesName) throws RemoteException {
		//TODO
		return 0;
	}
}
