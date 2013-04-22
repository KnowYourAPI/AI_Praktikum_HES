package hes.rechnungMgmt;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class RechnungRepository {
	
	public void speichereRechnung(Rechnung rechnung, Session session) {
		session.beginTransaction();
		session.save(rechnung);
		session.getTransaction().commit();
	}
	
	public Rechnung ladeRechnung(int rechnungId, Session session) {
		session.beginTransaction();
		Query query = session.createQuery("from Rechnung where rechnungId = :rechnungId");
		query.setParameter("rechnungId", rechnungId);
		List<?> queryResult = query.list();
		session.getTransaction().commit();
		
		if(!queryResult.isEmpty()) {
			Rechnung rechnung = (Rechnung)queryResult.get(0);
			return rechnung;
		} else
			return null;
	}

}
