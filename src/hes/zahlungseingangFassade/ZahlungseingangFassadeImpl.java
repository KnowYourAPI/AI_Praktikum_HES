package hes.zahlungseingangFassade;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import hes.auftragMgmt.IAuftragMgmt;
import hes.rechnungMgmt.IRechnungMgmt;

public class ZahlungseingangFassadeImpl implements IZahlungseingangFassade {
	
	private IRechnungMgmt rechnungMgmt;
	private IAuftragMgmt auftragMgmt;
	private SessionFactory sessionFactory;

	@Override
	public void meldeZahlungsEingang(int rechnungId, float betrag) {
		Session session = sessionFactory.getCurrentSession();
		boolean rechnungBezahlt = rechnungMgmt.meldeZahlungsEingang(rechnungId, betrag, session);

		if(rechnungBezahlt) {
			session = sessionFactory.getCurrentSession();
			int auftragId = rechnungMgmt.getAuftragId(rechnungId, session);
			session = sessionFactory.getCurrentSession();
			auftragMgmt.markiereAuftragAlsAbgeschlossen(auftragId, session);
		}
	}

}