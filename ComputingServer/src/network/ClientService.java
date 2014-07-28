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

public class ClientService implements Runnable {
	String hostname;
	int portNumber;

	ServerSocket serverSocket = null;
	Socket clientSocket = null;

	private Sender sender;

	public ClientService(int port, String hostname, Sender sender) {
		this.hostname = hostname;
		this.portNumber = port;
		this.sender = sender;
	}

	@Override
	public void run() {

		try {
			serverSocket = new ServerSocket(portNumber);
			System.out.println("ClientService: przyjmuje na " + hostname + ":"
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

			/* Nasluchiwanie wielu klientow */
			Thread t = new Thread(new Runnable() {
				public void run() {
					System.out.println("ClientService: polaczyl sie nowy "
							+ clientSocket.getInetAddress() + ":"
							+ clientSocket.getPort());
					createSocket(clientSocket);

				}
			});
			t.start();

		}

	}

	private void createSocket(Socket clientSocket2) {
		OutputStream os = null;
		OutputStreamWriter osw = null;
		BufferedWriter bw = null;

		InputStream is = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
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

		czytaj(clientSocket2, br, bw);

	}

	private void czytaj(Socket clientSocket, BufferedReader br,
			BufferedWriter bw) {
		try {

			int len = br.read();
			char[] cbuf = new char[len];
			br.read(cbuf, 0, len);
			String message = new String(cbuf);
			System.out
					.println("ClientService: Message received from the client with port : "
							+ message);

			parseMessage(message, clientSocket, br, bw);

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ClientService: nie czytam!");
		} finally {
			try {

				clientSocket.close();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private void parseMessage(String message, Socket clientSocket,
			BufferedReader br, BufferedWriter bw) {
		MessageClientService newMsg = new MessageClientService(message);
		odpowiedz(newMsg.getSendMessage(), clientSocket, br, bw);
		sender.giveRequest(message);
	}

	private void odpowiedz(String response, Socket clientSocket,
			BufferedReader br, BufferedWriter bw) {
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

		try {
			bw.close();
			br.close();
			clientSocket.close();

		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
