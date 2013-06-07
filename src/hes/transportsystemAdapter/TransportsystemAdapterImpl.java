package hes.transportsystemAdapter;

import hes.fassade.IHESAWKFassade;

public class TransportsystemAdapterImpl implements ITransportSystemAdapter {
	
	private IHESAWKFassade hesFassade;
	
	public TransportsystemAdapterImpl(IHESAWKFassade hesFassade) {
		this.hesFassade = hesFassade;
	}
	
	@Override
	public void markiereLieferungAlsErfolgt(int lieferungId) {
		hesFassade.markiereLieferungAlsErfolgt(lieferungId);
	}

}
