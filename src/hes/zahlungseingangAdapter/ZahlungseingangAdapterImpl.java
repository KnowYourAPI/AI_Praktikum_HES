package hes.zahlungseingangAdapter;

import hes.fassade.IFassade;

public class ZahlungseingangAdapterImpl implements IZahlungseingangAdapter {

	private IFassade hesFassade;
	
	public ZahlungseingangAdapterImpl(IFassade hesFassade) {
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