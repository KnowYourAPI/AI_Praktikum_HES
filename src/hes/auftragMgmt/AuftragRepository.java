package hes.auftragMgmt;

import hes.produktMgmt.Produkt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
		//TODO die Query ist nicht vollstaendig!! <- Weil wir nur die ausstehenden fuer die uebergebenen produkte haben wollen, das kriege ich nicht hin.... 
		List<?> oe = session.createSQLQuery("SELECT AuftragId FROM Auftrag WHERE Auftrag.istAbgeschlossen = 0" ).list();
		List<Auftrag> ergebnis = new ArrayList<Auftrag>();
		System.err.println(oe);
		
		Iterator<?> iter = oe.iterator();
		while(iter.hasNext()){
			Object obj = iter.next();
			System.err.println(obj.getClass());
			if(obj instanceof Integer){
				int auftragId = (Integer)obj;
				ergebnis.add(this.getAuftrag(auftragId, session));
			}
		}
		return ergebnis;
	}
	
	public void markiereAuftragAlsAbgeschlossen(int auftragId, Session session) {
		Auftrag auftrag = (Auftrag)session.get(Auftrag.class, auftragId);
		auftrag.setIstAbgeschlossen(true);
		session.update(auftrag);
	}
	
//	Query query = session.createQuery("from Auftrag a where rechnungId = :rechnungId");
//	query.setParameter("rechnungId", rechnungId);

}
