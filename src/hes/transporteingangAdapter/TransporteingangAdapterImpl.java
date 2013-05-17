package hes.transporteingangAdapter;

import hes.fassade.IHESAWKFassade;

public class TransporteingangAdapterImpl implements ITransporteingangAdapter {
	
	private IHESAWKFassade hesFassade;
	
	public TransporteingangAdapterImpl(IHESAWKFassade hesFassade) {
		this.hesFassade = hesFassade;
	}
	
	@Override
	public void markiereLieferungAlsErfolgt(int lieferungId) {
		hesFassade.markiereLieferungAlsErfolgt(lieferungId);
	}

}
