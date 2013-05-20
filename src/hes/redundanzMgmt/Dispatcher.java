package hes.redundanzMgmt;

import hes.auftragMgmt.AngebotTyp;
import hes.auftragMgmt.AuftragTyp;
import hes.fassade.IHESRemoteAWKFassadeServer;
import hes.kundeMgmt.AdressTyp;
import hes.produktMgmt.ProduktTyp;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

public class Dispatcher implements Observer, IHESRemoteAWKFassadeServer {

	@Override
	public int legeKundeAn(String name, AdressTyp adresse)
			throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int legeProduktAn(String name, int lagerbestand, float preis)
			throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getKundeId(String firmenName) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int erstelleAngebot(int kundenId) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ProduktTyp> getAlleProdukte() throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AngebotTyp fuegeProduktZuAngebotHinzu(int angebotId, int produktId,
			int menge) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AngebotTyp entferneProduktAusAngebot(int angebotId, int produktId)
			throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AuftragTyp erstelleAuftrag(int angebotId) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void meldeWareneingang(int produktId, int produktMenge, Date datum,
			String lieferantenName, Object lieferschein) throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getAuftragId(int rechnungId) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void markiereLieferungAlsErfolgt(int lieferungId)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void markiereAuftragAlsAbgeschlossen(int auftragId)
			throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean meldeZahlungseingang(int rechnungId, float betrag)
			throws RemoteException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	public int getAnzahlBearbeiteterAnfragen(String hesName) {
		// TODO
		return 0;
	}
	

}
