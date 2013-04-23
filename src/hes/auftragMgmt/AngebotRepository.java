package hes.auftragMgmt;

import hes.kundeMgmt.Kunde;

import org.hibernate.Session;

public class AngebotRepository {
	
	public Angebot erstelleAngebot(Kunde kunde, Session session) {
		Angebot angebot = new Angebot(kunde);
		session.save(angebot);
		return angebot;
	}
	
	public Angebot ladeAngebot(int angebotId, Session session) {
		Angebot angebot = (Angebot)session.get(Angebot.class, angebotId);
		return angebot;
	}
	
	public Angebot aktualisiereAngebot(Angebot angebot, Session session) {
        if (angebot != null){
        	session.update(angebot);
        }
		return angebot;
	}
	
	public Angebot getAngebot(int angebotId, Session session) {
		Angebot angebot = (Angebot)session.get(Angebot.class, angebotId);
		return angebot;
	}	
	
	public AngebotTyp getAngebotTyp(Angebot angebot) {
		return angebot.getAngebotTyp();
	}

}
