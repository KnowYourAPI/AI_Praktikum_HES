package hes.redundanzMgmt;

import java.io.Serializable;
import java.util.Date;
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
	//{HesName -> {Uptime, Downtime}}
	private Map<String, Tuple<Long, Long>> hesUpAndDownTime;
	private Map<String, Date> hesClientLastPing;
	
	private Map<String, HESTimer> timerListe;
	
	private static final int TIMEOUT = 20000;
	
	public Monitor() {
		this.hesInstanzZustaende = new HashMap<String, Tuple<Boolean, Boolean>>();
		this.timerListe = new HashMap<String, HESTimer>();
		this.hesClientLastPing = new HashMap<String, Date>();
		this.hesUpAndDownTime = new HashMap<String, Tuple<Long, Long>>();
	}
	
	
	public synchronized void ping(String server, String hesName) {
		Tuple<Boolean, Boolean> hesInstanzZustand = hesInstanzZustaende.get(hesName);
		if(hesInstanzZustand != null) {
			if(hesInstanzZustand.getFirst() == false) {
				hesInstanzZustand.setFirst(true);
			}
			
			updateTime(hesName);
			
		} else {
			hesInstanzZustand = new Tuple<Boolean, Boolean>(true, true);
			hesClientLastPing.put(hesName, new Date());
			hesUpAndDownTime.put(hesName, new Tuple<Long, Long>(0L,0L));
			hesInstanzZustaende.put(hesName, hesInstanzZustand);
		}
		HESTimer timer = new HESTimer(this, TIMEOUT, hesName);
		timer.start();
		this.timerListe.put(hesName, timer);
		
		//Wird benoetigt in der Gui, um up- und downtime regelmaessig zu updaten!
		setChanged();
		notifyObservers(new Object[] {server, hesName, hesInstanzZustand.getFirst(), hesInstanzZustand.getSecond(), hesUpAndDownTime.get(hesName)});
	}

	public synchronized void meldeTimeout(String hesName, HESTimer timer){
		if (this.timerListe.get(hesName) == timer) {
			System.out.println("Timeout gemeldet fuer " + hesName);
			Tuple<Boolean, Boolean> zustand = hesInstanzZustaende.get(hesName);
			zustand.setFirst(false);
			updateTime(hesName);
			setChanged();
			notifyObservers(new Object[] {null, hesName, zustand.getFirst(), zustand.getSecond(), hesUpAndDownTime.get(hesName)});
		}
	}
	
	public synchronized void schalteOnline(String hesName) {
		Tuple<Boolean, Boolean> hesInstanzZustand = hesInstanzZustaende.get(hesName);
		
		if(hesInstanzZustand != null) {
			if(hesInstanzZustand.getSecond() == false) {
				hesInstanzZustand.setSecond(true);
				setChanged();
			}
			
			updateTime(hesName);
			notifyObservers(new Object[] {null, hesName, hesInstanzZustand.getFirst(), hesInstanzZustand.getSecond(), hesUpAndDownTime.get(hesName)});
		}
	}

	public synchronized void schalteOffline(String hesName) {
		Tuple<Boolean, Boolean> hesInstanzZustand = hesInstanzZustaende.get(hesName);
		
		if(hesInstanzZustand != null) {
			if(hesInstanzZustand.getSecond() == true) {
				hesInstanzZustand.setSecond(false);
				setChanged();
			}
			
			updateTime(hesName);
			notifyObservers(new Object[] {null, hesName, hesInstanzZustand.getFirst(), hesInstanzZustand.getSecond(), hesUpAndDownTime.get(hesName)});
		}
	}
	
	public void updateTime(String hesName) {
		Tuple<Boolean, Boolean> hesInstanzZustand = hesInstanzZustaende.get(hesName);
		if(!hesInstanzZustand.getFirst() || !hesInstanzZustand.getSecond()) {
			Tuple<Long, Long> oldUpAndDownTime = hesUpAndDownTime.get(hesName);
			long newDownTime = oldUpAndDownTime.getSecond() + (new Date().getTime() - hesClientLastPing.get(hesName).getTime()); 
			hesUpAndDownTime.put(hesName, new Tuple<Long, Long>(oldUpAndDownTime.getFirst(), newDownTime));
			hesClientLastPing.put(hesName, new Date());
		} else {
			Tuple<Long, Long> oldUpAndDownTime = hesUpAndDownTime.get(hesName);
			long newUpTime = oldUpAndDownTime.getFirst() + (new Date().getTime() - hesClientLastPing.get(hesName).getTime()); 
			hesUpAndDownTime.put(hesName, new Tuple<Long, Long>(newUpTime, oldUpAndDownTime.getSecond()));
			hesClientLastPing.put(hesName, new Date());
		}
	}
}
