package hes.zahlungseingangFassade;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import hes.auftragMgmt.IAuftragMgmt;
import hes.fassade.IFassade;
import hes.rechnungMgmt.IRechnungMgmt;

public class ZahlungseingangFassadeImpl implements IZahlungseingangFassade {

	private IFassade hesFassade;
	
	public ZahlungseingangFassadeImpl(IFassade hesFassade) {
		this.hesFassade = hesFassade;
	}
	
	@Override
	public void meldeZahlungsEingang(int rechnungId, float betrag) {
		boolean rechnungBezahlt = hesFassade.meldeZahlungsEingang(rechnungId, betrag);

		if(rechnungBezahlt) {
			int auftragId = hesFassade.getAuftragId(rechnungId);
			hesFassade.markiereAuftragAlsAbgeschlossen(auftragId);
		}
	}

}