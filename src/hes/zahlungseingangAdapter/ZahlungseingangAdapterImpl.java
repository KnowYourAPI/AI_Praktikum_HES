package hes.zahlungseingangAdapter;

import hes.fassade.IHESAWKFassade;

public class ZahlungseingangAdapterImpl implements IZahlungseingangAdapter {

	private IHESAWKFassade hesFassade;
	
	public ZahlungseingangAdapterImpl(IHESAWKFassade hesFassade) {
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