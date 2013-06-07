package hes.zahlungseingangAdapter;

import java.rmi.RemoteException;

import hes.fassade.IHESRemoteAWKFassadeServer;

public class ZahlungseingangAdapterImpl implements IZahlungseingangAdapter {

	private IHESRemoteAWKFassadeServer hesFassade;
		
	private IMessageQueueListener messageQueueListener;
	
	public ZahlungseingangAdapterImpl(IHESRemoteAWKFassadeServer hesFassade) {
		this.hesFassade = hesFassade;		
	}
	
	@Override
	public synchronized void meldeZahlungsEingang(int rechnungId, float betrag) throws RemoteException {
		boolean rechnungBezahlt = hesFassade.meldeZahlungseingang(rechnungId, betrag);

		if(rechnungBezahlt) {
			int auftragId = hesFassade.getAuftragId(rechnungId);
			hesFassade.markiereAuftragAlsAbgeschlossen(auftragId);
		}
	}

	@Override
	public void startup(String queueName, String brokerHost) {
		messageQueueListener = new RabbitMqListener(queueName, brokerHost, this);
		new Thread(messageQueueListener).start();
	}

}