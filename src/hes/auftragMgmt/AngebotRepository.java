package hes.auftragMgmt;

import org.hibernate.Session;

public class AngebotRepository {
	
	public void erstelleAngebot(Angebot angebot, Session session) {
		session.beginTransaction();
		session.save(angebot);
		session.getTransaction().commit();
	}
	
	public Angebot ladeAngebot(int angebotId, Session session) {
		session.beginTransaction();
		Angebot angebot = (Angebot)session.get(Angebot.class, angebotId);
		session.getTransaction().commit();
		return angebot;
	}
	
	public void aktualisiereAngebot(Angebot angebot, Session session) {
		session.beginTransaction();
        if (angebot != null){
        	session.update(angebot);
        }
		session.getTransaction().commit();

	}

}
