package hes;

import hes.auftragMgmt.AuftragMgmtFassade;
import hes.auftragMgmt.IAuftragMgmt;
import hes.fassade.FassadeImpl;
import hes.fassade.IFassade;
import hes.kundeMgmt.IKundeMgmt;
import hes.kundeMgmt.KundeMgmtFassade;
import hes.lieferungMgmt.ILieferungMgmt;
import hes.lieferungMgmt.LieferungMgmtFassade;
import hes.produktMgmt.IProduktMgmt;
import hes.produktMgmt.ProduktMgmtFassade;
import hes.rechnungMgmt.IRechnungMgmt;
import hes.rechnungMgmt.RechnungMgmtFassade;

public class HESStarter {

	public static void main(String[] args) {
		IAuftragMgmt auftragMgmt = new AuftragMgmtFassade();
		IKundeMgmt kundeMgmt = new KundeMgmtFassade();
		IRechnungMgmt rechnungMgmt = new RechnungMgmtFassade();
		IProduktMgmt produktMgmt = new ProduktMgmtFassade();
		ILieferungMgmt lieferungMgmt = new LieferungMgmtFassade();
		IFassade fassade = new FassadeImpl(auftragMgmt, kundeMgmt,
				rechnungMgmt, produktMgmt, lieferungMgmt);

	}

}
