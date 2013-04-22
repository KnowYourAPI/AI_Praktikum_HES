package hes.lieferungMgmt;

import hes.auftragMgmt.Auftrag;

import org.hibernate.Session;

public class LieferungMgmtFassade implements ILieferungMgmt {
	
	private LieferungRepository lieferungRepository;
	
	public LieferungMgmtFassade() {
		this.lieferungRepository = new LieferungRepository();
	}

	@Override
	public Lieferung erstelleLieferung(Auftrag auftrag, Session session) {
		Lieferung lieferung = new Lieferung(auftrag);
		lieferungRepository.speichereLieferung(lieferung, session);
		return lieferung;
	}

	@Override
	public void markiereLieferungAlsErfolgt(int lieferungId, Session session) {
		Lieferung lieferung = lieferungRepository.ladeLieferung(lieferungId, session);
		if(lieferung != null) {
			lieferung.setLieferungErfolgt(true);
			lieferungRepository.speichereLieferung(lieferung, session);
		}
	}

}
