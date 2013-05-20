package hes.redundanzMgmt;

public class HESTimer extends Thread{
	private Monitor monitor;
	private int timeouttime;
	private String hesName;
	
	public HESTimer(Monitor monitor, int timeouttime, String hesName) {
		this.monitor = monitor;
		this.timeouttime = timeouttime;
		this.hesName = hesName;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(timeouttime);
			monitor.meldeTimeout(hesName, this);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
