package hes.redundanzMgmt;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;

import hes.auftragMgmt.AngebotTyp;
import hes.auftragMgmt.AuftragTyp;
import hes.fassade.IHESRemoteAWKFassadeServer;
import hes.kundeMgmt.AdressTyp;
import hes.produktMgmt.ProduktTyp;

public class RemoteHESFassade implements IHESRemoteAWKFassadeServer {
	
	private final String URL_PREFIX = "rmi://";
	private IHESRemoteAWKFassadeServer remoteFassadeServer;
	private String hesName;
	
	public RemoteHESFassade(String hesServer, String hesName) {
		
		String url = URL_PREFIX + hesServer + "/" + hesName;
		
		this.hesName = hesName;
		
		try {
			remoteFassadeServer = (IHESRemoteAWKFassadeServer) Naming.lookup(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
		
	}

	public String getHesName() {
		return this.hesName;
	}
	
	@Override
	public int legeKundeAn(String name, AdressTyp adresse) throws RemoteException {
		return remoteFassadeServer.legeKundeAn(name, adresse);
	}

	@Override
	public int legeProduktAn(String name, int lagerbestand, float preis) throws RemoteException {
		return remoteFassadeServer.legeProduktAn(name, lagerbestand, preis);
	}

	@Override
	public int getKundeId(String firmenName) throws RemoteException {
		return remoteFassadeServer.getKundeId(firmenName);
	}

	@Override
	public int erstelleAngebot(int kundenId) throws RemoteException {
		return remoteFassadeServer.erstelleAngebot(kundenId);
	}

	@Override
	public List<ProduktTyp> getAlleProdukte() throws RemoteException {
		return remoteFassadeServer.getAlleProdukte();
	}

	@Override
	public AngebotTyp fuegeProduktZuAngebotHinzu(int angebotId, int produktId,
			int menge) throws RemoteException {
		return remoteFassadeServer.fuegeProduktZuAngebotHinzu(angebotId, produktId, menge);
	}

	@Override
	public AngebotTyp entferneProduktAusAngebot(int angebotId, int produktId) throws RemoteException {
		return remoteFassadeServer.entferneProduktAusAngebot(angebotId, produktId);
	}

	@Override
	public AuftragTyp erstelleAuftrag(int angebotId) throws RemoteException {
		return remoteFassadeServer.erstelleAuftrag(angebotId);
	}

	@Override
	public void meldeWareneingang(int produktId, int produktMenge, Date datum,
			String lieferantenName, Object lieferschein) throws RemoteException {
		remoteFassadeServer.meldeWareneingang(produktId, produktMenge, datum, lieferantenName, lieferschein);
	}

	@Override
	public int getAuftragId(int rechnungId) throws RemoteException {
		return remoteFassadeServer.getAuftragId(rechnungId);
	}

	@Override
	public void markiereLieferungAlsErfolgt(int lieferungId) throws RemoteException {
		remoteFassadeServer.markiereLieferungAlsErfolgt(lieferungId);
	}

	@Override
	public void markiereAuftragAlsAbgeschlossen(int auftragId) throws RemoteException {
		remoteFassadeServer.markiereAuftragAlsAbgeschlossen(auftragId);
	}

	@Override
	public boolean meldeZahlungseingang(int rechnungId, float betrag) throws RemoteException {
		return remoteFassadeServer.meldeZahlungseingang(rechnungId, betrag);
	}

}
