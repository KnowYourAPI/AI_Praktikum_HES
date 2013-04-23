package hes.auftragMgmt;

import hes.kundeMgmt.Kunde;

import org.hibernate.Session;

public class AngebotRepository {
	
	public Angebot erstelleAngebot(Kunde kunde, Session session) {
		session.beginTransaction();
		Angebot angebot = new Angebot(kunde);
		session.save(angebot);
		session.getTransaction().commit();
		return angebot;
	}
	
	public Angebot ladeAngebot(int angebotId, Session session) {
		session.beginTransaction();
		Angebot angebot = (Angebot)session.get(Angebot.class, angebotId);
		session.getTransaction().commit();
		return angebot;
	}
	
	public Angebot aktualisiereAngebot(Angebot angebot, Session session) {
		session.beginTransaction();
        if (angebot != null){
        	session.update(angebot);
        }
		session.getTransaction().commit();
		return angebot;
	}
	
	public Angebot getAngebot(int angebotId, Session session) {
		session.beginTransaction();
		Angebot angebot = (Angebot)session.get(Angebot.class, angebotId);
		session.getTransaction().commit();
		return angebot;
	}

}
