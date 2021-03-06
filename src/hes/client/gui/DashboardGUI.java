package hes.client.gui;

import hes.redundanzMgmt.Dispatcher;
import hes.redundanzMgmt.IRedundanzMgmt;
import hes.redundanzMgmt.Monitor;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;
import processing.core.PFont;
import util.Tuple;

public class DashboardGUI extends PApplet implements Observer{

	private static final long serialVersionUID = 1L;
	
	private static final String URL_PREFIX = "rmi://";
	private static final String REDUNDANZ_MGMT_SERVER = "localhost";
	private static final String REDUNDANZ_MGMT_NAME = "redundanzmgmt";
	
	private IRedundanzMgmt redundanzMgmtFassade; 
	
	private PFont pFont;
	
	//Liste der angezeigten HesSysteme in der angezeigten Reihenfolge
	private List<String> aktAngezeigteListe;
	
	// Liste von: hesName -> {istLebendig, istAngeschaltet}
	private Map<String, Tuple<Boolean,Boolean>> hesInstanzZustaende;
	
	// Liste von: hesName -> Anzahl bearbeiteter Anfragen 
	private Map<String, Integer> hesAnzahlBearbeiteterAnfragen;
	
	// Liste von: hesName -> {gesMillisekUptime, gesMillisekDowntime} 
	private Map<String, Tuple<Long, Long>> hesUpDowntime;
	
	// List von: hesName -> zuletzt geupdated in Millisekunden
	private Map<String, Long> hesLastTimeUpdated;
	
	public void setup() {
		pFont = createFont("Arial",16,true);
		hesInstanzZustaende = new HashMap<String, Tuple<Boolean, Boolean>>();
		String redundanzMgmtUrl = URL_PREFIX + REDUNDANZ_MGMT_SERVER + "/" + REDUNDANZ_MGMT_NAME;
		try {
			redundanzMgmtFassade = (IRedundanzMgmt) Naming.lookup(redundanzMgmtUrl);
		} catch (MalformedURLException e) {
			System.err.println("DashboardGui: Das Redundanzmanagement kann nicht unter folgender Adresse gefunden werden: " + redundanzMgmtUrl);
			e.printStackTrace();
		} catch (RemoteException e) {
			System.err.println("DashboardGui: Das Redundanzmanagement kann nicht unter folgender Adresse gefunden werden: " + redundanzMgmtUrl);
			e.printStackTrace();
		} catch (NotBoundException e) {
			System.err.println("DashboardGui: Das Redundanzmanagement kann nicht unter folgender Adresse gefunden werden: " + redundanzMgmtUrl);
			e.printStackTrace();
		}
		hesAnzahlBearbeiteterAnfragen = new HashMap<String, Integer>();
		hesUpDowntime = new HashMap<String, Tuple<Long,Long>>();
		hesLastTimeUpdated = new HashMap<String, Long>();
		size(500, 500);
		background(255);
	}

	public void draw() {
		zeigeUeberschrift();
		zeigeHesSysteme();
	}
	
	private void zeigeHesSysteme() {
		int x = 10;
		int y = 90;
		ellipseMode(CENTER);
		aktAngezeigteListe = new ArrayList<String>();
		
		for(Map.Entry<String, Tuple<Boolean,Boolean>> eintrag : hesInstanzZustaende.entrySet()) {
			textFont(pFont,16);
			x = 10;
			int rot = 0;
			int gruen = 0; 
			int blau = 0;
			boolean istLebendig = eintrag.getValue().getFirst();
			boolean istAngeschaltet = eintrag.getValue().getSecond();
			if (!istLebendig) {
				rot = 255;
			} else if (!istAngeschaltet) {
				rot = 255;
				gruen = 255;
			} else {
				gruen = 255;
			}
			fill(rot, gruen, blau);
			ellipse(x, y-8, 10, 10);
			x += 20;
			fill(0);
			String hesName = eintrag.getKey();
			int anzahlBearbeiteterAnfragen = 0;
			if (hesAnzahlBearbeiteterAnfragen.get(hesName) != null) {
				anzahlBearbeiteterAnfragen = hesAnzahlBearbeiteterAnfragen.get(hesName);
			}
			String hesText = hesName + " ( Anzahl Anfragen: " + anzahlBearbeiteterAnfragen + ") ";
			
			if (hesLastTimeUpdated.get(hesName) != null) {
				Long upTime = hesUpDowntime.get(hesName).getFirst();
				Long downTime = hesUpDowntime.get(hesName).getSecond();
				hesText += upTime/1000 + " Sek up " + downTime/1000 + " Sek down";
			}
			
			text(hesText, x, y);
			aktAngezeigteListe.add(eintrag.getKey());
			
			y += 30;
		}
	}
	
	private void zeigeUeberschrift() {
		background(255);
		int x = 10;
		int y = 25;
		fill(0);
		textFont(pFont,26);
		text("Die HES-Instanzen und ihr Status: ", x, y);
		x += 30;
		y += 30;
		ellipseMode(CENTER);
		textFont(pFont,16);
		fill(0, 255, 0);
		ellipse(x, y-8, 10, 10);
		x += 12;
		fill(0);
		text("Bereit", x, y);
		x += 70;
		fill(255, 255, 0);
		ellipse(x, y-8, 10, 10);
		x += 12;
		fill(0);
		text("Manuell Ausgeschaltet", x, y);
		x += 200;
		fill(255, 0, 0);
		ellipse(x, y-8, 10, 10);
		x += 12;
		fill(0);
		text("Tot", x, y);
	}
	
	@Override
	public void mouseReleased() {
		if (mouseX >= 5 && mouseX <= 15 && mouseY >= 75) {
			int aktuelleListenPosition = ((mouseY - 75) / 30);
			if (aktuelleListenPosition < hesInstanzZustaende.size()) {
				String hesName = aktAngezeigteListe.get(aktuelleListenPosition);

				if (hesInstanzZustaende.get(hesName).getFirst()) {
					try {
						if (hesInstanzZustaende.get(hesName).getSecond()) {
							redundanzMgmtFassade.schalteOffline(hesName);
						} else {
							redundanzMgmtFassade.schalteOnline(hesName);
						}
					} catch (RemoteException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	@Override
	public void update(Observable observable, Object arg) {
		if (observable instanceof Monitor) {
			Object[] objectAry = (Object[]) arg;
			String hesInstanzName = (String) objectAry[1];
			boolean istLebendig = (Boolean) objectAry[2];
			boolean istAngeschaltet = (Boolean) objectAry[3];
			Tuple<Boolean,Boolean> zustandTuple = new Tuple<Boolean, Boolean>(istLebendig, istAngeschaltet);
			hesInstanzZustaende.put(hesInstanzName, zustandTuple);	
			if (!hesLastTimeUpdated.keySet().contains(hesInstanzName)) {
				hesLastTimeUpdated.put(hesInstanzName, new Date().getTime());
				hesUpDowntime.put(hesInstanzName, new Tuple<Long,Long>(0L,0L));
			}
		} else if (observable instanceof Dispatcher) {
			Object[] ary = (Object[])arg;
			String hesName = (String) ary[0];
			int anzahlAnfragen = (Integer) ary[1];
			hesAnzahlBearbeiteterAnfragen.put(hesName, anzahlAnfragen);
		}
		
		for (String hesName : hesLastTimeUpdated.keySet()) {
			Long differenceInMilliseconds = new Date().getTime() - hesLastTimeUpdated.get(hesName);
			hesLastTimeUpdated.put(hesName, new Date().getTime());
			Long neueZeit;
			Tuple<Long,Long> upDownTime = hesUpDowntime.get(hesName);
			if (hesInstanzZustaende.get(hesName).getFirst() && hesInstanzZustaende.get(hesName).getSecond()) {
				neueZeit = upDownTime.getFirst() + differenceInMilliseconds;
				upDownTime.setFirst(neueZeit);
			} else {
				neueZeit = upDownTime.getSecond() + differenceInMilliseconds;
				upDownTime.setSecond(neueZeit);
			}
		}
		
	}

}
