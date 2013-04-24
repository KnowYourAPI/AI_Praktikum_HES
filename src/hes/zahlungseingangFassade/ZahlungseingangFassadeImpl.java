package hes.zahlungseingangFassade;

import hes.fassade.IFassade;

public class ZahlungseingangFassadeImpl implements IZahlungseingangFassade {

	private IFassade hesFassade;
	
	public ZahlungseingangFassadeImpl(IFassade hesFassade) {
		this.hesFassade = hesFassade;
	}
	
	@Override
	public void meldeZahlungsEingang(int rechnungId, float betrag) {
		boolean rechnungBezahlt = hesFassade.meldeZahlungseingang(rechnungId, betrag);

		if(rechnungBezahlt) {
			int auftragId = hesFassade.getAuftragId(rechnungId);
			hesFassade.markiereAuftragAlsAbgeschlossen(auftragId);
		}
	}

}