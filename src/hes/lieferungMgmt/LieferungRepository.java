package hes.lieferungMgmt;

import org.hibernate.Session;

class LieferungRepository {
	
	Lieferung ladeLieferung(int lieferungId, Session session) {
		session.beginTransaction();
		Lieferung lieferung = (Lieferung) session.get(Lieferung.class, lieferungId);
		session.getTransaction().commit();
		
		return lieferung;
	}
	
	void speichereLieferung(Lieferung lieferung, Session session) {
		session.beginTransaction();
		session.save(lieferung);
		session.getTransaction().commit();
	}
	
	void aktualisiereLieferung(Lieferung lieferung, Session session) {
		session.beginTransaction();
		session.update(lieferung);
		session.getTransaction().commit();
	}
}
