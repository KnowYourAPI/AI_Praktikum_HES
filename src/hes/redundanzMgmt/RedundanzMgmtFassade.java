package hes.redundanzMgmt;

import hes.auftragMgmt.AngebotTyp;
import hes.auftragMgmt.AuftragTyp;
import hes.kundeMgmt.AdressTyp;
import hes.produktMgmt.ProduktTyp;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;
import java.util.List;

public class RedundanzMgmtFassade extends UnicastRemoteObject implements IRedundanzMgmt {
	
	private static final long serialVersionUID = 1L;
	
	private Dispatcher dispatcher;
	private Monitor monitor;
	private String name;
	
	public RedundanzMgmtFassade(String redundanzMgmtName) throws RemoteException {
		this.dispatcher = new Dispatcher();
		this.monitor = new Monitor();
		monitor.addObserver(dispatcher);
		this.name = redundanzMgmtName;
		
		try {
			Naming.rebind(name, this);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void ping(String server, String hesName) throws RemoteException {
		monitor.ping(server, hesName);		
	}

	@Override
	public void schalteOnline(String hesName) throws RemoteException {
		monitor.schalteOnline(hesName);
	}

	@Override
	public void schalteOffline(String hesName) throws RemoteException {
		monitor.schalteOffline(hesName);
	}

	@Override
	public int getAnzahlBearbeiteterAnfragen(String hesName) throws RemoteException {
		return dispatcher.getAnzahlBearbeiteterAnfragen(hesName);
	}

	@Override
	public int legeKundeAn(String name, AdressTyp adresse)
			throws RemoteException {
		return dispatcher.legeKundeAn(name, adresse);
	}

	@Override
	public int legeProduktAn(String name, int lagerbestand, float preis) throws RemoteException {
		return dispatcher.legeProduktAn(name, lagerbestand, preis);
	}

	@Override
	public int getKundeId(String firmenName) throws RemoteException {
		return dispatcher.getKundeId(firmenName);
	}

	@Override
	public int erstelleAngebot(int kundenId) throws RemoteException {
		return dispatcher.erstelleAngebot(kundenId);
	}

	@Override
	public List<ProduktTyp> getAlleProdukte() throws RemoteException {
		return dispatcher.getAlleProdukte();
	}

	@Override
	public AngebotTyp fuegeProduktZuAngebotHinzu(int angebotId, int produktId, int menge) throws RemoteException {
		return dispatcher.fuegeProduktZuAngebotHinzu(angebotId, produktId, menge);
	}

	@Override
	public AngebotTyp entferneProduktAusAngebot(int angebotId, int produktId) throws RemoteException {
		return dispatcher.entferneProduktAusAngebot(angebotId, produktId);
	}

	@Override
	public AuftragTyp erstelleAuftrag(int angebotId) throws RemoteException {
		return dispatcher.erstelleAuftrag(angebotId);
	}

	@Override
	public void meldeWareneingang(int produktId, int produktMenge, Date datum,
			String lieferantenName, Object lieferschein) throws RemoteException {
		dispatcher.meldeWareneingang(produktId, produktMenge, datum, lieferantenName, lieferschein);
	}

	@Override
	public int getAuftragId(int rechnungId) throws RemoteException {
		return dispatcher.getAuftragId(rechnungId);
	}

	@Override
	public void markiereLieferungAlsErfolgt(int lieferungId)
			throws RemoteException {
		dispatcher.markiereLieferungAlsErfolgt(lieferungId);
	}

	@Override
	public void markiereAuftragAlsAbgeschlossen(int auftragId)
			throws RemoteException {
		dispatcher.markiereAuftragAlsAbgeschlossen(auftragId);
	}

	@Override
	public boolean meldeZahlungseingang(int rechnungId, float betrag)
			throws RemoteException {
		return dispatcher.meldeZahlungseingang(rechnungId, betrag);
	}

}
