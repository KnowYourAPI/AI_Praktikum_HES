package hes.redundanzMgmt;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRedundanzMgmt extends Remote {
	
	public void ping(String hesName) throws RemoteException;
	
	public void schalteOnline(String hesName) throws RemoteException;
	
	public void schalteOffline(String hesName) throws RemoteException;
	
	public int getAnzahlBearbeiteterAnfragen(String hesName) throws RemoteException;

}
