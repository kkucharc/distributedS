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

import JSONs.StateResponse;

public class Connector {
	Socket clientSocket = null;

	String hostname = null;
	int port;

	OutputStream os = null;
	OutputStreamWriter osw = null;
	BufferedWriter bw = null;

	InputStream is = null;
	InputStreamReader isr = null;
	BufferedReader br = null;

	public Connector(String hostname, int port) {
		this.hostname = hostname;
		this.port = port;
		createSocket();
	}

	public void createSocket() {

		try {
			clientSocket = new Socket(hostname, port);

			os = clientSocket.getOutputStream();
			osw = new OutputStreamWriter(os);
			bw = new BufferedWriter(osw);

			is = clientSocket.getInputStream();
			isr = new InputStreamReader(is);
			br = new BufferedReader(isr);
			System.out.println("Polaczony jako klient. Sle.");
		} catch (UnknownHostException e) {
			System.err.println("Nieznany host");
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to host");
			e.printStackTrace();
		}

	}

	private void slij(String msg) {

		if (clientSocket != null) {
			String responseLine = msg;
			Integer len = responseLine.length();
			try {
				bw.write(len);

				char[] cbuf = new char[len];
				cbuf = responseLine.toCharArray();
				System.out.println("Message sent from the client : "
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

			// Odpowiedz
			try {
				int lenRead = br.read();
				char[] message = new char[lenRead];
				br.read(message, 0, lenRead);
				System.out.println("Message received from the server : "
						+ new String(message));
				if (message != null)
					parseMessage(new String(message));
			} catch (IOException e) {
				System.err.println("Can't read message");
			} finally {
				try {
					br.close();
					bw.close();
					clientSocket.close();
				} catch (IOException e1) {
					System.err.println("Can't close socket");
				}
			}

		}
	}

	private void parseMessage(String string) {

		new StateResponse(string).showResult();

	}

	public boolean works() {
		if (clientSocket == null)
			return false;
		return clientSocket.isConnected();
	}

	public void sendMessage(Message message) {
		slij(message.getSendMessage());

	}

	public void close() {

		try {
			clientSocket.close();
		} catch (IOException e) {
			System.out.println("Nie udalo sie zamknac polaczenia");
			e.printStackTrace();
		}

	}
}