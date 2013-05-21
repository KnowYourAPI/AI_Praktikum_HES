package hes;

import java.rmi.RemoteException;

public class HESStarter2 {

	public static void main(String[] args) throws RemoteException {
		HESStarter.startup("HES4", 10000, false);
	}

}
