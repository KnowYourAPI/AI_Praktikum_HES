package hes.redundanzMgmt;

import java.rmi.RemoteException;

public class RedundanzMgmtFassade implements IRedundanzMgmt {
	
	private Dispatcher dispatcher;
	private Monitor monitor;
	
	public RedundanzMgmtFassade() {
		this.dispatcher = new Dispatcher();
		this.monitor = new Monitor();
	}

	@Override
	public void ping(String hesName) throws RemoteException {
		monitor.ping(hesName);		
	}

	@Override
	public void schalteOnline(String hesName) throws RemoteException {
		monitor.schalteOnline(hesName);
	}

	@Override
	public void schalteOffline(String hesName) throws RemoteException {
		monitor.schalteOffline(hesName);
	}

	@Override
	public int getAnzahlBearbeiteterAnfragen(String hesName) throws RemoteException {
		// TODO Auto-generated method stub
		return 0;
	}

}
