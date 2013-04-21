package hes.kundeMgmt;

import org.hibernate.Session;

import util.AdressTyp;

class KundeRepository {
	
	KundeTyp getKunde(int kundeId) {
		return null;
	}

	int erstelleKunde(String name, AdressTyp adresse, Session session) {
		session.beginTransaction();
		Kunde neuerKunde = new Kunde(name, adresse);
		session.save(adresse);
		session.save(neuerKunde);
		session.getTransaction().commit();
		return neuerKunde.getKundeId();
	}

	int getKundeId(String firmenName) {
		return 0;
	}

}
