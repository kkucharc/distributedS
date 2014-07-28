package JSONs;

import java.util.Random;

import org.json.simple.JSONObject;

public class State {

	JSONObject msg;

	public State(int idTask) {
		createJSONQuestion(idTask);
	}

	public void createJSONQuestion(int idTask) {

		msg = new JSONObject();
		msg.put("typ_wiadomosci", "zapytanie_o_zad");
		msg.put("id_wiadomosci", new Integer(new Random().nextInt()).toString());
		msg.put("id_zadania", new Integer(idTask));

	}

	public String getStringJSON() {
		return msg.toJSONString();
	}

}
