package hes.lieferungMgmt;

import java.util.Date;

import hes.auftragMgmt.Auftrag;
import hes.transportsystemAdapter.ITransportSystemAdapter;

import org.hibernate.Session;

public class LieferungMgmtFassade implements ILieferungMgmt {
	
	private LieferungRepository lieferungRepository;
	
	private ITransportSystemAdapter transportsystemAdapter;
	
	public LieferungMgmtFassade(ITransportSystemAdapter transportsystemAdapter) {
		this.lieferungRepository = new LieferungRepository();
		this.transportsystemAdapter = transportsystemAdapter;
	}

	@Override
	public Lieferung erstelleLieferung(Auftrag auftrag, Session session) {
		Lieferung lieferung = new Lieferung(auftrag);
		lieferungRepository.speichereLieferung(lieferung, session);
		System.err.println("!!!! Nach Erstellung d. Lieferung, id  " + lieferung.getLieferungId());
		System.err.println("!!!!!" + lieferungRepository.ladeLieferung(lieferung.getLieferungId(), session));
		Object[] returnWerte = transportsystemAdapter.verschickeTransportauftrag(lieferung.getLieferungId());
		
		if(returnWerte != null) {
			int lieferungId = (Integer) returnWerte[0];
			Date ausgangsdatum = (Date) returnWerte[1];
			boolean lieferungErfolgt = (Boolean)returnWerte[2];
			Date lieferdatum = (Date) returnWerte[3];
			String transportdienstleister = (String)returnWerte[4];
			if(lieferungErfolgt) {
				markiereLieferungAlsErfolgt(lieferungId, ausgangsdatum, lieferdatum, transportdienstleister, session);				
			} else {
				System.err.println("Lieferung mit der Nummer " + lieferungId + " wurde nicht ausgeliefert.");
			}
		} else {
			System.err.println("Es gab einen Fehler beim Verschicken des Transportauftrags.");
		}
		
		return lieferung;
	}
	
	
	@Override
	public void markiereLieferungAlsErfolgt(int lieferungId, Date ausgangsdatum, Date lieferdatum, String transportdienstleister, Session session) {
		Lieferung lieferung = lieferungRepository.ladeLieferung(lieferungId, session);
		System.err.println("______markiere lieferung als erfolgt in der LieferungMgmtFassade");
		System.err.println("null? " + lieferung + " lieferungID: " + lieferungId);
		if(lieferung != null) {
			lieferung.setLieferungErfolgt(true);
			lieferung.setAusgangsdatum(ausgangsdatum);
			lieferung.setLieferdatum(lieferdatum);
			lieferung.setTransportdienstleister(transportdienstleister);
			lieferungRepository.aktualisiereLieferung(lieferung, session);
		}
	}

}
