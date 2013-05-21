package hes.redundanzMgmt;

import hes.fassade.IHESRemoteAWKFassadeServer;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Observer;

public interface IRedundanzMgmt extends Remote, IHESRemoteAWKFassadeServer {
	
	public void ping(String server, String hesName) throws RemoteException;
	
	public void schalteOnline(String hesName) throws RemoteException;
	
	public void schalteOffline(String hesName) throws RemoteException;
	
	public void addDispatcherObserver(Observer observer) throws RemoteException;
	
	public void addMonitorObserver(Observer observer) throws RemoteException;

}
