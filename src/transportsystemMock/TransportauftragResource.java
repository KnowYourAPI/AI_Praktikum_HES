package transportsystemMock;

import java.util.Date;

import org.restlet.resource.Get;
import org.restlet.resource.ServerResource;

/**
 * Resource which has only one representation.
 * 
 */
public class TransportauftragResource extends ServerResource {

    @Get
    public String represent() {
    	String transportauftragnummer = (String) getRequestAttributes().get("transportauftragnummer");
    	String ausgangsdatumMillis = (String) getRequestAttributes().get("ausgangsdatummillis");
    	String message = "transportauftragnummer:" + transportauftragnummer + "%" +
    					 "ausgangsdatum:" + ausgangsdatumMillis + "%" +
    					 "lieferungerfolgt:" + true + "%" +
    					 "lieferdatum:" + new Date().getTime() + "%" +
    					 "transportdiensteister:" + "UPS";
        return message;
    }

}
