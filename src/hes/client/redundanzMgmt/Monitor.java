package hes.client.redundanzMgmt;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import util.Tuple;

public class Monitor extends Observable implements IMonitor {
	
	// Zuordnung:
	// {HESName -> lebendig, online}
	// Beispiel:
	// {"HES1" -> true, false} 
	// "HES1" ist lebendig, soll aber manuell als ausgeschaltet simuliert werden
	private Map<String, Tuple<Boolean, Boolean>> hesInstanzZustaende;
	
	public Monitor() {
		this.hesInstanzZustaende = new HashMap<String, Tuple<Boolean, Boolean>>();
	}

	@Override
	public void ping(String hesName) {
		Tuple<Boolean, Boolean> hesInstanzZustand = hesInstanzZustaende.get(hesName);
		if(hesInstanzZustand != null) {
			if(hesInstanzZustand.getFirst() == false) {
				hesInstanzZustand.setFirst(true);
				setChanged();
			}
		} else {
			hesInstanzZustand = hesInstanzZustaende.put(hesName, new Tuple<Boolean, Boolean>(true, true));
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
