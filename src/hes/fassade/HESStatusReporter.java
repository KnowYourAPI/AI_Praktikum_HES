package hes.fassade;

import hes.redundanzMgmt.IRedundanzMgmt;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class HESStatusReporter implements Runnable {
	
	private final String URL_PREFIX = "rmi://";
	private String hesName;
	private String remoteMonitorURL;
	private IRedundanzMgmt redundanzMgmt;
	private long wartezeitInMillisekunden;
	private boolean running;
	
	public HESStatusReporter(String hesName, String redundanzMgmtServer, String redundanzMgmtName, long warteZeitInMillisekunden) {
		this.hesName = hesName;
		this.remoteMonitorURL = URL_PREFIX + redundanzMgmtServer + "/" + redundanzMgmtName;
		this.wartezeitInMillisekunden = warteZeitInMillisekunden;
		this.running = true;
	}

	@Override
	public void run() {
		
		while(running) {
			if (redundanzMgmt == null) {
				
				try {
					this.redundanzMgmt = (IRedundanzMgmt) Naming.lookup(remoteMonitorURL);
				} catch (MalformedURLException e) {
					//URL nicht in Ordnung
					
					e.printStackTrace();
				} catch (RemoteException e) {
					//Nameservice nicht erreichbar
					
					e.printStackTrace();
				} catch (NotBoundException e) {
					//Name nicht vorhanden
					System.out.println("Das remote Objekt unter dem Namen " + remoteMonitorURL + " konnte nicht gefunden werden.");
				}
				
			} else {
				try {
					String localHost = InetAddress.getLocalHost().getHostName();
					redundanzMgmt.ping(localHost, hesName);
				} catch (RemoteException e) {
					e.printStackTrace();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
			
			try {
				Thread.sleep(wartezeitInMillisekunden);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
				
	}
	
	public void shutdown() {
		this.running = false;
	}

}
