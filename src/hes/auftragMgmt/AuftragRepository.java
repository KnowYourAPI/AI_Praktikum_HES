package hes.auftragMgmt;

import org.hibernate.Session;

public class AuftragRepository {
	public void erstelleAuftrag(Auftrag auftrag, Session session) {
		session.beginTransaction();
		session.save(auftrag);
		session.getTransaction().commit();
	}
	
	public Auftrag ladeAuftrag(int auftragId, Session session) {
		session.beginTransaction();
		Auftrag auftrag = (Auftrag)session.get(Auftrag.class, auftragId);
		session.getTransaction().commit();
		return auftrag;
	}
	
	public void aktualisiereAuftrag(Auftrag auftrag, Session session) {
		session.beginTransaction();
        if (auftrag != null){
        	session.update(auftrag);
        }
		session.getTransaction().commit();

	}
}
