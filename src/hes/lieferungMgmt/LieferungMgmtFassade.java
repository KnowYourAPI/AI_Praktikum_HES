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
		transportsystemAdapter.verschickeTransportauftrag(lieferung.getLieferungId());
		lieferungRepository.speichereLieferung(lieferung, session);
		return lieferung;
	}

	@Override
	public void markiereLieferungAlsErfolgt(int lieferungId, Session session) {
		Lieferung lieferung = lieferungRepository.ladeLieferung(lieferungId, session);
		if(lieferung != null) {
			lieferung.setLieferungErfolgt(true);
			lieferung.setLieferdatum(new Date());
			lieferungRepository.aktualisiereLieferung(lieferung, session);
		}
	}

}
