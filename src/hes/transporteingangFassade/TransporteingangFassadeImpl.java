package hes.transporteingangFassade;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import hes.lieferungMgmt.ILieferungMgmt;

public class TransporteingangFassadeImpl implements ITransporteingangFassade {

	private ILieferungMgmt lieferungMgmt;
	private SessionFactory sessionFactory;
	
	public TransporteingangFassadeImpl(ILieferungMgmt lieferungMgmt, SessionFactory sessionFactory) {
		this.lieferungMgmt = lieferungMgmt;
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void markiereLieferungAlsErfolgt(int lieferungId) {
		Session session = sessionFactory.getCurrentSession();
		lieferungMgmt.markiereLieferungAlsErfolgt(lieferungId, session);
	}

}
