package hes.client.redundanzMgmt;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import util.Tuple;

public class Monitor extends Observable implements IMonitor, Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Zuordnung:
	// {HESName -> lebendig, online}
	// Beispiel:
	// {"HES1" -> true, false} 
	// "HES1" ist lebendig, soll aber manuell als ausgeschaltet simuliert werden
	private Map<String, Tuple<Boolean, Boolean>> hesInstanzZustaende;
	
	public Monitor(String monitorName) {
		this.hesInstanzZustaende = new HashMap<String, Tuple<Boolean, Boolean>>();
		
		try {
			Naming.rebind(monitorName, this);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void ping(String hesName) {
		System.out.println("Ping erhalten von: " + hesName);
		Tuple<Boolean, Boolean> hesInstanzZustand = hesInstanzZustaende.get(hesName);
		if(hesInstanzZustand != null) {
			if(hesInstanzZustand.getFirst() == false) {
				hesInstanzZustand.setFirst(true);
				setChanged();
			}
		} else {
			hesInstanzZustand = new Tuple<Boolean, Boolean>(true, true);
			hesInstanzZustaende.put(hesName, hesInstanzZustand);
			setChanged();
		}
		
		notifyObservers(new Object[] {hesName, istLebendig(hesInstanzZustand)});
	}

	@Override
	public void schalteOnline(String hesName) {
		Tuple<Boolean, Boolean> hesInstanzZustand = hesInstanzZustaende.get(hesName);
		
		if(hesInstanzZustand != null) {
			if(hesInstanzZustand.getSecond() == false) {
				hesInstanzZustand.setSecond(true);
				setChanged();
			}
			
			notifyObservers(new Object[] {hesName, istLebendig(hesInstanzZustand)});
		}
	}

	@Override
	public void schalteOffline(String hesName) {
		Tuple<Boolean, Boolean> hesInstanzZustand = hesInstanzZustaende.get(hesName);
		
		if(hesInstanzZustand != null) {
			if(hesInstanzZustand.getSecond() == true) {
				hesInstanzZustand.setSecond(false);
				setChanged();
			}
			
			notifyObservers(new Object[] {hesName, istLebendig(hesInstanzZustand)});
		}
	}

	private boolean istLebendig(Tuple<Boolean, Boolean> hesInstanzZustand) {
		return hesInstanzZustand.getFirst() && hesInstanzZustand.getSecond();
	}
}
