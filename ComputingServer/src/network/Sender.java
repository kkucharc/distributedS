package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import JSONs.Client;
import JSONs.Greetings;

public class Sender implements Runnable {

	String hostnameConnectTo;
	int portNumberConnectTo;
	Socket clientSocket = null;

	OutputStream os = null;
	OutputStreamWriter osw = null;
	BufferedWriter bw = null;

	InputStream is = null;
	InputStreamReader isr = null;
	BufferedReader br = null;

	// drugi serwer ktory sie podlacza a my nasluchujemy
	Receiver friendServer = null;

	CoordinatorQueues cq;
	int ID;

	boolean coordinator = false;

	public Sender(int portConnectTo, String hostnameConnectTo, int id,
			Receiver friendServer) {
		ID = id;
		this.hostnameConnectTo = hostnameConnectTo;
		this.portNumberConnectTo = portConnectTo;

		this.friendServer = friendServer;
		System.out.println("Sender: probuje laczyc do "
				+ this.hostnameConnectTo + ":" + this.portNumberConnectTo);
	}

	@Override
	public void run() {
		cq = new CoordinatorQueues();
		friendServer.setCQ(cq);
		friendServer.setSender(this);
		if (createSocket()) {
			sendGreetings();
			czytaj();
		} else {
			System.out
					.println("Sender: Timeout na probe podlaczenia sie do kogos...");
			System.out.println("Sender: Jestem koordynatorem! Muahaha!");

			friendServer.setCoordinator(true);
			coordinator = true;
		}

	}

	public Boolean createSocket() {

		boolean stan = false;
		int i = 0;
		if (!hostnameConnectTo.equals("")) {
			do {
				i++;
				try {
					Thread.currentThread().sleep(3000);
				} catch (Exception ex) {
				}
				System.out.println("Sender: Try to connect... " + i);
				try {
					clientSocket = new Socket(hostnameConnectTo,
							portNumberConnectTo);
					os = clientSocket.getOutputStream();
					osw = new OutputStreamWriter(os);
					bw = new BufferedWriter(osw);

					is = clientSocket.getInputStream();
					isr = new InputStreamReader(is);
					br = new BufferedReader(isr);
					return true;
				} catch (UnknownHostException e) {
					stan = false;
					System.err.println("Sender: Nieznany host");

				} catch (IOException e) {
					stan = false;
					continue;
				}
				try {
					java.lang.Thread.sleep(12000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (stan == false && i < 5);
		}
		return false;
	}

	private void czytaj() {
		try {
			while (clientSocket.isConnected()) {

				int len = br.read();
				char[] cbuf = new char[len];
				br.read(cbuf, 0, len);
				System.out
						.println("Sender: Message received from the client : "
								+ new String(cbuf));
				parseMessage(new String(cbuf));

			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Sender: nie czytam!");
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
			friendServer.passMessage(message);
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
				System.out.println("Sender: Greetings sent to the client: "
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

	private void odpowiedz(String response) {
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

	public boolean isConnected() {
		if (clientSocket == null)
			return false;
		else
			return true;
	}

	public void giveRequest(String mess) {
		Client message = new Client(ID, mess);
	}
}
