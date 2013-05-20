package hes.client.redundanzMgmt;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IMonitor extends Remote {
	
	public void ping(String server,String hesName) throws RemoteException;
	
	public void schalteOnline(String hesName) throws RemoteException;
	
	public void schalteOffline(String hesName) throws RemoteException;

}
