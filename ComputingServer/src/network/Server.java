package network;

import general.Configuration;

public class Server {

	int ID;
	
	// odbieranie zadan
	ClientService clientListener = null;
	// drugi serwer ktory sie podlacza a my nasluchujemy
	Receiver friendServer = null;
	// drugi do ktorego sie podlaczamy
	Sender friendClient = null;

	public Server(Configuration conf) {
		ID = (int) conf.getID();
		System.out.println("My id " + ID);
		startReceiver(conf.getReceiverHostname(), (int) conf.getReceiverPort());
		startListenClient(conf.getClientServiceHostname(),
				(int) conf.getClientServicePort());
		
		startSender(conf.getConnectionHostname(),
				(int) conf.getConnectionPort());
	}

	private void startReceiver(String hostname, int port) {
		System.out.println("Stawianie serwera");
		friendServer = new Receiver(port, hostname, ID);
		new Thread(friendServer).start();
	}

	private void startSender(String hostnameConnectTo, int portConnectTo) {
		System.out.println("Stawianie clienta");
			friendClient = new Sender(portConnectTo, hostnameConnectTo, ID, friendServer);
			new Thread(friendClient).start();
	}

	private void startListenClient(String hostname, int port) {
		System.out.println("Nasluchiwanie klientow");
		clientListener = new ClientService(port, hostname, friendClient);
		new Thread(clientListener).start();

	}

}
