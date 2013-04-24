package hes.auftragMgmt;

import hes.produktMgmt.Produkt;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public class AuftragRepository {
	
	public Auftrag erstelleAuftrag(Angebot angebot, Session session) {
		Auftrag auftrag = new Auftrag(angebot);
		session.save(auftrag);
		return auftrag;
	}
	
	public Auftrag ladeAuftrag(int auftragId, Session session) {
		Auftrag auftrag = (Auftrag)session.get(Auftrag.class, auftragId);
		return auftrag;
	}
	
	public void aktualisiereAuftrag(Auftrag auftrag, Session session) {
        if (auftrag != null){
        	session.update(auftrag);
        }
	}
	
	public Auftrag getAuftrag(int auftragId, Session session) {
		Auftrag auftrag = (Auftrag)session.get(Auftrag.class, auftragId);
		return auftrag;
	}
	
	public AuftragTyp getAuftragTyp(Auftrag auftrag) {
		return auftrag.getAuftragTyp();
	}
	
	public List<Auftrag> getNichtAbgeschlosseneAuftraege(Produkt produkt, Session session) {
		Query query = session.createQuery("from Auftrag a where a.istAbgeschlossen = false and :produkt member of a.angebot.produkte" );
		query.setParameter("produkt", produkt);
		@SuppressWarnings("unchecked")
		List<Auftrag> queryResultList = query.list();
		System.err.println(queryResultList);
		return queryResultList;		
	}
	
	public void markiereAuftragAlsAbgeschlossen(int auftragId, Session session) {
		Auftrag auftrag = (Auftrag)session.get(Auftrag.class, auftragId);
		auftrag.setIstAbgeschlossen(true);
		session.update(auftrag);
	}

}
