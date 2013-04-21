package hes.lieferungMgmt;

import org.hibernate.Session;

class TransportauftragRepository {
	
	void speichereTransportauftrag(Transportauftrag transportauftrag, Session session) {
		session.beginTransaction();
		session.save(transportauftrag);
		session.getTransaction().commit();
		
	}

}
