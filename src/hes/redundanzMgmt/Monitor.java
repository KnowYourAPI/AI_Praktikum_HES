package hes.redundanzMgmt;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

import util.Tuple;

public class Monitor extends Observable implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Zuordnung:
	// {HESName -> lebendig, online}
	// Beispiel:
	// {"HES1" -> true, false} 
	// "HES1" ist lebendig, soll aber manuell als ausgeschaltet simuliert werden
	private Map<String, Tuple<Boolean, Boolean>> hesInstanzZustaende;
	
	private Map<String, HESTimer> timerListe;
	
	private static final int TIMEOUT = 20000;
	
	public Monitor() {
		this.hesInstanzZustaende = new HashMap<String, Tuple<Boolean, Boolean>>();
		this.timerListe = new HashMap<String, HESTimer>();
	}
	
	
	public void ping(String server, String hesName) {
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
		HESTimer timer = new HESTimer(this, TIMEOUT, hesName);
		timer.start();
		this.timerListe.put(hesName, timer);
		
		notifyObservers(new Object[] {server, hesName, hesInstanzZustand.getFirst(), hesInstanzZustand.getSecond()});
	}

	public void meldeTimeout(String hesName, HESTimer timer){
		if (this.timerListe.get(hesName) == timer) {
			System.out.println("Timeout gemeldet fuer " + hesName);
			Tuple<Boolean, Boolean> zustand = hesInstanzZustaende.get(hesName);
			zustand.setFirst(false);	
		}
	}
	
	public void schalteOnline(String hesName) {
		Tuple<Boolean, Boolean> hesInstanzZustand = hesInstanzZustaende.get(hesName);
		
		if(hesInstanzZustand != null) {
			if(hesInstanzZustand.getSecond() == false) {
				hesInstanzZustand.setSecond(true);
				setChanged();
			}
			
			notifyObservers(new Object[] {null, hesName, hesInstanzZustand.getFirst(), hesInstanzZustand.getSecond()});
		}
	}

	public void schalteOffline(String hesName) {
		Tuple<Boolean, Boolean> hesInstanzZustand = hesInstanzZustaende.get(hesName);
		
		if(hesInstanzZustand != null) {
			if(hesInstanzZustand.getSecond() == true) {
				hesInstanzZustand.setSecond(false);
				setChanged();
			}
			
			notifyObservers(new Object[] {null, hesName, hesInstanzZustand.getFirst(), hesInstanzZustand.getSecond()});
		}
	}
}
