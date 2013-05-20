package hes.redundanzMgmt;

import hes.fassade.IHESRemoteAWKFassadeServer;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IRedundanzMgmt extends Remote, IHESRemoteAWKFassadeServer {
	
	public void ping(String server, String hesName) throws RemoteException;
	
	public void schalteOnline(String hesName) throws RemoteException;
	
	public void schalteOffline(String hesName) throws RemoteException;
	
	public int getAnzahlBearbeiteterAnfragen(String hesName) throws RemoteException;

}
