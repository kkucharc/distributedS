package general;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import network.Connector;
import network.Message;

public class Start {
	static Connector server;

	static String hostname = null;
	static int port;
	static BufferedReader br = new BufferedReader(new InputStreamReader(
			System.in));

	public static void main(String[] args) {
		try {
			menu();
		} catch (IOException e) {
			System.out.println("Problem z wczytaniem wejscia");
			e.printStackTrace();
		}
	}

	static void menu() throws IOException {
		String choice = "n";
		System.out.println("Witaj!\nAby wyjsc wpisz: [q/Q] "
				+ (choice.equals("Q") || choice.equals("q")));
		while (!choice.equals("Q") && !choice.equals("q")) {

			System.out
					.println("Z kim połączyc (hostname/ip)? [default - localhost]");

			choice = br.readLine();

			if (choice.equals("q") || choice.equals("Q")) {
				break;
			} else if (choice.equals(null) || choice.trim().equals("")) {
				choice = "localhost";
			}
			hostname = choice;

			System.out.println("IP/host: " + hostname);
			System.out.println("A jaki port? [default - 1111;q - wyjscie]");

			choice = br.readLine();

			if (choice.equals("q") || choice.equals("Q")) {
				break;
			} else if (choice.equals(null) || choice.trim().equals("")) {
				port = 1111;
			} else {
				port = Integer.parseInt(choice);
			}
			System.out.println("hostname \'" + hostname + "\' port \'" + port);
			while (!choice.equals("n") && !choice.equals("N")) {
				server = new Connector(hostname, port);
				if (server != null && server.works()) {
					serverRequestsMenu();
					choice = "n";
				} else {
					System.out
							.println("Serwer nie odpowiada, ponowic probe? [y/N]");
					choice = br.readLine();
				}
			}

		}
	}

	private static void serverRequestsMenu() throws IOException {
		String choice = "";

		while (!choice.equals("Q") && !choice.equals("q")
				&& !choice.equals("n") && !choice.equals("N")) {

			System.out
					.println("1. Przekaz skrypt \n2. Zapytaj o zadanie\n[q/Q] Wyjdz");

			choice = br.readLine();
			if (choice.equals("1")) {
				System.out
						.println("Podaj sciezke do skrypu: [default - fibonacci]");
				choice = br.readLine();
				if (choice.equals(null) || choice.trim().equals(""))
					choice = "/home/kasiaonthepole/workspace/ComputingServer/exampleScripts/fibonacci.pl";
				server.sendMessage(new Message(choice, "zlecenie_zadania"));
				choice = "n";
			} else if (choice.equals("2")) {
				System.out.println("Podaj numer zadania:");
				choice = br.readLine();
				server.sendMessage(new Message(choice, "zapytanie_o_zad"));
				choice = "n";
			}

		}

		server.close();
	}
}
