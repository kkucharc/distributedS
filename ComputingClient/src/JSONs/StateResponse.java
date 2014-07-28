package JSONs;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class StateResponse {
	String stringMsg;
	JSONObject msg;
	JSONParser parser;

	long idTask = 0;
	String stan = "";
	String wynik = "";

	public StateResponse(String msg) {
		stringMsg = msg;
		parseJSONResponse();
	}

	private void parseJSONResponse() {
		parser = new JSONParser();
		try {
			msg = (JSONObject) parser.parse(stringMsg);

			idTask = (Long) msg.get("id_zadania");
			stan = (String) msg.get("stan_zadania");
			wynik = (String) msg.get("wynik");
		} catch (ParseException e) {
			System.err.println("Can't parse response");
		}

	}

	public void showResult() {
		System.out.println("Zadanie numer: " + idTask);
		System.out.println("Stan zadania: " + stan);
		System.out.println("Wyniki: " + wynik);
	}

}
