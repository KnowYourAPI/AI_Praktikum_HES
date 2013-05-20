package hes.redundanzMgmt;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class RedundanzMgmtFassade implements IRedundanzMgmt {
	
	private Dispatcher dispatcher;
	private Monitor monitor;
	private String name;
	
	public RedundanzMgmtFassade(String redundanzMgmtName, List<HESRemoteClient> hesRemoteClients) {
		this.dispatcher = new Dispatcher(hesRemoteClients);
		this.monitor = new Monitor();
		monitor.addObserver(dispatcher);
		this.name = redundanzMgmtName;
		
		try {
			Naming.rebind(name, this);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
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
		return dispatcher.getAnzahlBearbeiteterAnfragen(hesName);
	}

}
