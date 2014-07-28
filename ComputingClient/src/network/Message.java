package network;

import JSONs.Script;
import JSONs.State;

public class Message {

	String msg = null;

	public Message(String request, String type) {
		if (type.equals("zapytanie_o_zad")) {
			System.out.println("zadanko " + new Integer(request));
			msg = new State(new Integer(request)).getStringJSON();
		} else if (type.equals("zlecenie_zadania")) {
			msg = new Script(request).getJSONScript();
		}

	}

	public String getSendMessage() {
		return msg;
	}

}
