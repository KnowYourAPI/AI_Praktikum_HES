package hes.rechnungMgmt;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class RechnungRepository {
	
	public void speichereRechnung(Rechnung rechnung, Session session) {
		session.save(rechnung);
	}
	
	public Rechnung ladeRechnung(int rechnungId, Session session) {
		Query query = session.createQuery("from Rechnung where rechnungId = :rechnungId");
		query.setParameter("rechnungId", rechnungId);
		List<?> queryResult = query.list();
		
		if(!queryResult.isEmpty()) {
			Rechnung rechnung = (Rechnung)queryResult.get(0);
			return rechnung;
		} else
			return null;
	}
	
	public void aktualisiereRechnung(Rechnung rechnung, Session session) {
		session.update(rechnung);		
	}

}
