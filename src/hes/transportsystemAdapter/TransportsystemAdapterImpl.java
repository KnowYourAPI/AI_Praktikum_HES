package hes.transportsystemAdapter;

import java.io.IOException;
import java.util.Date;

import org.apache.derby.tools.sysinfo;
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
				
				return new Object[] {lieferungId, ausgangsdatum, lieferungErfolgt, lieferdatum, transportdienstleister};
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		return null;
	}

}
