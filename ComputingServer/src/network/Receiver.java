package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import JSONs.Greetings;

public class Receiver implements Runnable {
	String hostname;
	int portNumber;
	Socket clientSocket = null;
	ServerSocket serverSocket = null;

	OutputStream os = null;
	OutputStreamWriter osw = null;
	BufferedWriter bw = null;

	InputStream is = null;
	InputStreamReader isr = null;
	BufferedReader br = null;

	int ID;
	private boolean coordinator = false;
	private Sender sender;

	CoordinatorQueues cq;

	public Receiver(int port, String hostname, int ID) {
		this.ID = ID;
		this.hostname = hostname;
		this.portNumber = port;

	}

	@Override
	public void run() {

		try {
			serverSocket = new ServerSocket(portNumber);
			System.out.println("Receiver: przyjmuje na " + hostname + ":"
					+ portNumber);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (true) {
			try {
				clientSocket = serverSocket.accept();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			/* Dodany fragment do nasluchiwania wielu klientow-serwerow */
			Thread t = new Thread(new Runnable() {
				public void run() {
					System.out.println("Receiver: polaczyl sie nowy "
							+ clientSocket.getInetAddress() + ":"+ clientSocket.getPort());
					createSocket();
					sendGreetings();
					czytaj();
				}
			});
			t.start();

		}

	}

	private void createSocket() {

		try {

			os = clientSocket.getOutputStream();
			osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw);

			is = clientSocket.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void sendGreetings() {
		String response = new Greetings(ID).getStringJSON();
		if (clientSocket != null && bw != null && br != null) {
			Integer len = response.length();
			try {
				bw.write(len);

				char[] cbuf = new char[len];
				cbuf = response.toCharArray();
				System.out.println("Receiver: Message sent to the client : "
						+ new String(cbuf));
				bw.write(cbuf, 0, len);

			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				bw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	private void czytaj() {
		try {
			while (clientSocket.isConnected()) {

				int len = br.read();
				char[] cbuf = new char[len];
				br.read(cbuf, 0, len);
				System.out
						.println("Receiver: Message received from the client : "
								+ new String(cbuf));

				parseMessage(new String(cbuf));

			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Receiver: nie czytam!");
		} finally {
			try {

				clientSocket.close();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void parseMessage(String message) {
		Message newMsg = new Message(message, ID, cq);
		if (newMsg.getSendMessage() != null
				&& newMsg.getSendMessage().equals(""))
			odpowiedz(newMsg.getSendMessage());
		if (newMsg.isPassBy() && !coordinator) {
			sender.passMessage(message);
		}
	}

	private void odpowiedz(String response) {
		Integer len = response.length();
		System.out.println("len " + len);
		try {
			bw.write(len);

			char[] cbuf = new char[len];
			cbuf = response.toCharArray();
			System.out.println("Receiver: Message sent to the client : "
					+ new String(cbuf));
			bw.write(cbuf, 0, len);

		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			bw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void passMessage(String message) {
		if (clientSocket != null && bw != null && br != null) {
			Integer len = message.length();
			try {
				bw.write(len);

				char[] cbuf = new char[len];
				cbuf = message.toCharArray();
				System.out.println("Sender: Message sent to the client : "
						+ new String(cbuf));
				bw.write(cbuf, 0, len);

			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				bw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public void setCoordinator(boolean b) {
		coordinator = b;
	}

	public void setSender(Sender sender) {
		this.sender = sender;

	}

	public void setCQ(CoordinatorQueues cq) {
		this.cq = cq;
	}

	public void passRemoteMessage(String message) {
		// TODO Auto-generated method stub
		
	}

}
