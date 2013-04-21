package hes.lieferungMgmt;

import org.hibernate.Session;

public class LieferungMgmtFassade implements ILieferungMgmt {
	
	private LieferungRepository lieferungRepository;
	private TransportauftragRepository transportauftragRepository;
	
	public LieferungMgmtFassade() {
		this.lieferungRepository = new LieferungRepository();
		this.transportauftragRepository = new TransportauftragRepository();
	}

	@Override
	public int erstelleLieferungUndTransportauftrag(int auftragID, Session session) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void markiereLieferungAlsErfolgt(int lieferungId, Session session) {
		lieferungRepository.markiereLieferungAlsErfolgt(lieferungId, session);
	}

}
