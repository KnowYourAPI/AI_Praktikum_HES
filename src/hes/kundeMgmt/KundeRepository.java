package hes.kundeMgmt;

import org.hibernate.Query;
import org.hibernate.Session;


class KundeRepository {
	
	KundeTyp getKunde(int kundeId, Session session) {
		
		Query query = session.createQuery("");
		return null;
	}

	int erstelleKunde(String name, AdressTyp adresse, Session session) {
		session.beginTransaction();
		Kunde neuerKunde = new Kunde(name, adresse);
		session.save(neuerKunde);
		session.getTransaction().commit();
		return neuerKunde.getKundeId();
	}

	int getKundeId(String firmenName) {
		return 0;
	}

}
