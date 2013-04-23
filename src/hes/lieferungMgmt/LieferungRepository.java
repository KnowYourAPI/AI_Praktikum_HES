package hes.lieferungMgmt;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

class LieferungRepository {
	
	Lieferung ladeLieferung(int lieferungId, Session session) {
		session.beginTransaction();
		Query query = session.createQuery("from Lieferung where lieferungId = :lieferungId");
		query.setParameter("lieferungId", lieferungId);
		List<?> list = query.list();
		session.getTransaction().commit();
		
		if(!list.isEmpty()) {
			Lieferung lieferung = (Lieferung)list.get(0);
			lieferung.setLieferungErfolgt(true);
			return lieferung;
		} else
			return null;
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
