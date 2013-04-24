package hes.transporteingangAdapter;

import hes.fassade.IFassade;

public class TransporteingangAdapterImpl implements ITransporteingangAdapter {
	
	private IFassade hesFassade;
	
	public TransporteingangAdapterImpl(IFassade hesFassade) {
		this.hesFassade = hesFassade;
	}
	
	@Override
	public void markiereLieferungAlsErfolgt(int lieferungId) {
		hesFassade.markiereLieferungAlsErfolgt(lieferungId);
	}

}
