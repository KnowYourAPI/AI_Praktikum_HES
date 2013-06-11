package hes.transportsystemAdapter;

import hes.fassade.IHESRemoteAWKFassadeServer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Date;

import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;


public class TransportsystemAdapterImpl implements ITransportSystemAdapter {
		
	private String transportSystemHostName;
	
	private int transportSystemPort;
	
	private IHESRemoteAWKFassadeServer remoteFassade;
	
	public TransportsystemAdapterImpl(String transportSystemHostName, int transportSystemPort, String redundanzMgmtFassadeURL) {
		this.transportSystemHostName = transportSystemHostName;
		this.transportSystemPort = transportSystemPort;
		try {
			this.remoteFassade = (IHESRemoteAWKFassadeServer) Naming.lookup(redundanzMgmtFassadeURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object[] verschickeTransportauftrag(int lieferungId) {
		long date = new Date().getTime();
		String rootUri = "http://" + transportSystemHostName + ":" + transportSystemPort + "/";
		String message = rootUri + "transportsystemMock/transportauftrag/send/" + lieferungId + "/" + date; 
		Representation antwort = new ClientResource(message).get(); 
		
			try {
				String antwortString = antwort.getText();
				String[] antwortAryString = antwortString.split("\"");
				String[] realesAntwortAry = antwortAryString[1].split("%");
				Date ausgangsdatum = new Date(Long.parseLong(realesAntwortAry[1].split(":")[1]));
				boolean lieferungErfolgt = Boolean.parseBoolean(realesAntwortAry[2].split(":")[1]);
				Date lieferdatum = new Date(Long.parseLong(realesAntwortAry[3].split(":")[1]));
				String transportdienstleister = realesAntwortAry[4].split(":")[1];
				
//				if (lieferungErfolgt) {
//					System.err.println("------------------markiere lieferung als erfolgt:");
//					System.err.println(antwortString);
//					
//					remoteFassade.markiereLieferungAlsErfolgt(lieferungId, ausgangsdatum, lieferdatum, transportdienstleister);
//				} else {
//					System.err.println("Lieferung mit der Nummer " + lieferungId + " wurde nicht ausgeliefert.");
//				}
				return new Object[] {lieferungId, ausgangsdatum, lieferungErfolgt, lieferdatum, transportdienstleister};
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		return null;
	}

}
