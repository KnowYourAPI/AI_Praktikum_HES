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
		
		return null;
	}

	@Override
	public void markiereLieferungAlsErfolgt(int lieferungId, Session session) {
		lieferungRepository.markiereLieferungAlsErfolgt(lieferungId, session);
	}

}
