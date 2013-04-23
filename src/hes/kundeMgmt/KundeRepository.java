package hes.kundeMgmt;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;


class KundeRepository {
	
	Kunde ladeKunde(int kundeId, Session session) {
		session.beginTransaction();
		Kunde kunde = (Kunde) session.get(Kunde.class, kundeId);
		session.getTransaction().commit();
		return kunde;
	}

	int erstelleKunde(String name, AdressTyp adresse, Session session) {
		session.beginTransaction();
		Kunde neuerKunde = new Kunde(name, adresse);
		session.save(neuerKunde);
		session.getTransaction().commit();
		return neuerKunde.getKundeId();
	}

	int getKundeId(String firmenName, Session session) {
		session.beginTransaction();
		Query query = session.createQuery("from Kunde where name = :firmenName");
		query.setParameter("firmenName", firmenName);
		List<?> list = query.list();
		
		if(list.isEmpty()) {
			return -1;
		} else {
			Kunde kunde = (Kunde)list.get(0);
			return kunde.getKundeId();
		}
	}

}
