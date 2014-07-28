package network;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import JSONs.Greetings;
import JSONs.GreetingsResponse;
import JSONs.ScriptJSON;
import JSONs.State;

public class MessageClientService {
	String msg = null;

	JSONObject jsonmsg;
	JSONObject jsonObj;
	JSONParser parser;
	
	Object obj;
	
	String request;

	public MessageClientService(String request) {
		this.request = request;
		parse();
	}

	private void parse() {
		parser = new JSONParser();

		try {
			obj = parser.parse(request);
		} catch (ParseException e) {
			System.err.println("Nie udalo sie sparsowac");
			e.printStackTrace();
		}
		jsonObj = (JSONObject) obj;
		if (obj == null)
			System.out.println("Brak obiektu JSON!");
		String typ = (String) jsonObj.get("typ_wiadomosci");
		if (typ == null) {
			System.out.println("Brak Typu");
		} else {
			choice(typ);

		}

	}

	private void choice(String type) {

		if (type.equals("zapytanie_o_zad")) {
			State state = new State(request, jsonObj);
			msg = state.getStringJSON();
		} else if (type.equals("zlecenie_zadania")) {
			ScriptJSON script = new ScriptJSON(request, jsonObj);
			msg = script.getStringJSON();

		}
	}

	public String getSendMessage() {
		return msg;
	}

}
