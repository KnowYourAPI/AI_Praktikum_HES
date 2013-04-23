package hes.lieferungMgmt;

import org.hibernate.Session;

class LieferungRepository {
	
	Lieferung ladeLieferung(int lieferungId, Session session) {
		Lieferung lieferung = (Lieferung) session.get(Lieferung.class, lieferungId);
		return lieferung;
	}
	
	void speichereLieferung(Lieferung lieferung, Session session) {
		session.save(lieferung);
	}
	
	void aktualisiereLieferung(Lieferung lieferung, Session session) {
		session.update(lieferung);
	}
}
