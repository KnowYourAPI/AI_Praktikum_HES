package hes.auftragMgmt;

import hes.produktMgmt.Produkt;
import hes.rechnungMgmt.Rechnung;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class AuftragRepository {
	
	public Auftrag erstelleAuftrag(Angebot angebot, Session session) {
		session.beginTransaction();
		Auftrag auftrag = new Auftrag(angebot);
		session.save(auftrag);
		session.getTransaction().commit();
		return auftrag;
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
	
	public Auftrag getAuftrag(int auftragId, Session session) {
		session.beginTransaction();
		Auftrag auftrag = (Auftrag)session.get(Auftrag.class, auftragId);
		session.getTransaction().commit();
		return auftrag;
	}
	
	public AuftragTyp getAuftragTyp(Auftrag auftrag) {
		return auftrag.getAuftragTyp();
	}
	
	public List<Auftrag> getNichtAbgeschlosseneAuftraege(Produkt produkt, Session session) {
		session.beginTransaction();
		//TODO die Query ist nicht vollstaendig!! <- Julian: Tippfehler korrigiert. Duerfte jetzt funktionieren.
		List<Auftrag> ergebnis = session.createSQLQuery("from Auftrag a where a.istAbgeschlossen = true").list();
		session.getTransaction().commit();
		return ergebnis;
	}
	
	public void markiereAuftragAlsAbgeschlossen(int auftragId, Session session) {
		session.beginTransaction();
		Auftrag auftrag = (Auftrag)session.get(Auftrag.class, auftragId);
		auftrag.setIstAbgeschlossen(true);
		session.update(auftrag);
		session.getTransaction().commit();
	}
	
//	Query query = session.createQuery("from Auftrag a where rechnungId = :rechnungId");
//	query.setParameter("rechnungId", rechnungId);

}
