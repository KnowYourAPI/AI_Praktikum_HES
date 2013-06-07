package hes.transportsystemAdapter;

import java.io.IOException;
import java.util.Date;

import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;


public class TransportsystemAdapterImpl implements ITransportSystemAdapter {
		
	private String transportSystemHostName;
	
	private int transportSystemPort;
	
	public TransportsystemAdapterImpl(String transportSystemHostName, int transportSystemPort) {
		this.transportSystemHostName = transportSystemHostName;
		this.transportSystemPort = transportSystemPort;
	}

	@Override
	public void verschickeTransportauftrag(int lieferungId) {
		long date = new Date().getTime();
		String rootUri = "http://" + transportSystemHostName + ":" + transportSystemPort + "/";
		String message = rootUri + "transportsystemMock/transportauftrag/send/" + lieferungId + "/" + date; 
		//String message = "http://localhost:8183/transportsystemMock/transportauftrag/send/42/6666666";
		Representation antwort = new ClientResource(message).get(); 
		try {
			System.out.println(antwort.getText());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
