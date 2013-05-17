package hes.fassade;

import hes.auftragMgmt.AngebotTyp;
import hes.auftragMgmt.AuftragTyp;
import hes.kundeMgmt.AdressTyp;
import hes.produktMgmt.ProduktTyp;

import java.util.Date;
import java.util.List;

public class HESRemoteAWKFassadeServer implements IHESRemoteAWKFassadeServer {

	private IHESAWKFassade hesAwkFassade;
	
	public HESRemoteAWKFassadeServer(IHESAWKFassade hesAwkFassade) {
		this.hesAwkFassade = hesAwkFassade;
	}
	
	@Override
	public int legeKundeAn(String name, AdressTyp adresse) {
		return hesAwkFassade.legeKundeAn(name, adresse);
	}

	@Override
	public int legeProduktAn(String name, int lagerbestand, float preis) {
		return hesAwkFassade.legeProduktAn(name, lagerbestand, preis);
	}

	@Override
	public int getKundeId(String firmenName) {
		return hesAwkFassade.getKundeId(firmenName);
	}

	@Override
	public int erstelleAngebot(int kundenId) {
		return hesAwkFassade.erstelleAngebot(kundenId);
	}

	@Override
	public List<ProduktTyp> getAlleProdukte() {
		return hesAwkFassade.getAlleProdukte();
	}

	@Override
	public AngebotTyp fuegeProduktZuAngebotHinzu(int angebotId, int produktId,
			int menge) {
		return hesAwkFassade.fuegeProduktZuAngebotHinzu(angebotId, produktId, menge);
	}

	@Override
	public AngebotTyp entferneProduktAusAngebot(int angebotId, int produktId) {
		return hesAwkFassade.entferneProduktAusAngebot(angebotId, produktId);
	}

	@Override
	public AuftragTyp erstelleAuftrag(int angebotId) {
		return hesAwkFassade.erstelleAuftrag(angebotId);
	}

	@Override
	public void meldeWareneingang(int produktId, int produktMenge, Date datum,
			String lieferantenName, Object lieferschein) {
		hesAwkFassade.meldeWareneingang(produktId, produktMenge, datum, lieferantenName, lieferschein);
	}

	@Override
	public int getAuftragId(int rechnungId) {
		return hesAwkFassade.getAuftragId(rechnungId);
	}

	@Override
	public void markiereLieferungAlsErfolgt(int lieferungId) {
		hesAwkFassade.markiereLieferungAlsErfolgt(lieferungId);
	}

	@Override
	public void markiereAuftragAlsAbgeschlossen(int auftragId) {
		hesAwkFassade.markiereAuftragAlsAbgeschlossen(auftragId);
	}

	@Override
	public boolean meldeZahlungseingang(int rechnungId, float betrag) {
		return hesAwkFassade.meldeZahlungseingang(rechnungId, betrag);
	}

}
