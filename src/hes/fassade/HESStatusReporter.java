package hes.fassade;

import hes.client.redundanzMgmt.IMonitor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class HESStatusReporter implements Runnable {
	
	private final String URL_PREFIX = "rmi://";
	private String hesName;
	private String remoteMonitorURL;
	private IMonitor monitor;
	private long wartezeitInMillisekunden;
	private boolean running;
	
	public HESStatusReporter(String hesName, String monitorServer, String monitorName, long warteZeitInMillisekunden) {
		this.hesName = hesName;
		this.remoteMonitorURL = URL_PREFIX + monitorServer + "/" + monitorName;
		this.wartezeitInMillisekunden = warteZeitInMillisekunden;
		this.running = true;
	}

	@Override
	public void run() {
		
		while(running) {
			if (monitor == null) {
				
				try {
					this.monitor = (IMonitor) Naming.lookup(remoteMonitorURL);
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
					monitor.ping(hesName);
				} catch (RemoteException e) {
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
