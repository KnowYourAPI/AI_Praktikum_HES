package hes.transporteingangFassade;

import hes.fassade.IFassade;

public class TransporteingangFassadeImpl implements ITransporteingangFassade {
	
	private IFassade hesFassade;
	
	public TransporteingangFassadeImpl(IFassade hesFassade) {
		this.hesFassade = hesFassade;
	}
	
	@Override
	public void markiereLieferungAlsErfolgt(int lieferungId) {
		hesFassade.markiereLieferungAlsErfolgt(lieferungId);
	}

}
