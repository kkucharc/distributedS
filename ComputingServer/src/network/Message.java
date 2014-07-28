package network;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import JSONs.Greetings;
import JSONs.GreetingsResponse;

public class Message {
	String msg = null;

	JSONObject jsonmsg;
	JSONObject jsonObj;
	JSONParser parser;
	Object obj;

	String request;
	int ID;
	private boolean passBy = false;

	CoordinatorQueues cq = null;

	public Message(String request, int ID, CoordinatorQueues cq) {
		this.ID = ID;
		this.request = request;
		this.cq = cq;
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

		if (type.equals("greetings")) {
			Greetings greet = new Greetings(ID, jsonObj);
			msg = greet.getStringResponse();
//			passBy = false;
			int id = (int) greet.getID();
			passBy = (id!=ID);
		} else if (type.equals("a_greetings")) {
			GreetingsResponse gr = new GreetingsResponse(ID, jsonObj);

			int id = (int) gr.getID();
			passBy = (id!=ID);
			cq.addList(id);
		}
	}

	public String getSendMessage() {
		return msg;
	}

	public boolean isPassBy() {
		return passBy;
	}
}
