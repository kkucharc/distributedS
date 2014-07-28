package JSONs;

import org.json.simple.JSONObject;

public class StateResponse{
	JSONObject msg;

	public StateResponse(long idTask, String idMessage, String stan, String wynik) {
		createJSONResponse(idTask, idMessage, stan, wynik);
	}

	public void createJSONResponse(long idTask, String idMessage, String stan, String wynik) {

		msg = new JSONObject();
		msg.put("typ_wiadomości", "status_zadania");
		msg.put("id_wiadomości", new String(idMessage));
		msg.put("id_zadania", new Long(idTask));
		msg.put("stan_zadania", new String(stan));
		msg.put("wynik", new String(wynik));

	}

	public String getStringJSON() {
		return msg.toJSONString();
	}

}
