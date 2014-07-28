package JSONs;

import org.json.simple.JSONObject;

public class State {

	String msg;
	String jsonmsg;
	Object obj;

	public State(String message, JSONObject obj) {
		msg = message;
		parseRequest(obj);
		
	}

	void parseRequest(JSONObject obj) {
		if (obj == null)
			System.out.println("NULL!");

		String idMess = (String) obj.get("id_wiadomosci");
		long idTask = (Long) obj.get("id_zadania");

		jsonmsg = (new StateResponse(idTask, idMess, "w oczekiwaniu", ""))
				.getStringJSON();

	}

	public String getStringJSON() {
		return jsonmsg;
	}

}
