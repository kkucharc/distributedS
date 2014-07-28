package JSONs;

import java.util.Date;

import org.json.simple.JSONObject;

public class GreetingsResponse {
	long IDnadawcy;
	JSONObject msg;
	JSONObject toParse;
	int ID;

	public GreetingsResponse(int ID, String idMess) {
		createGreetings(ID);
		this.ID = ID;
	}

	public GreetingsResponse(int ID, JSONObject obj) {
		this.toParse = obj;
		this.ID = ID;

		IDnadawcy = (Long) obj.get("id_nadawcy");
	}

	public void createGreetings(int ID) {

		msg = new JSONObject();
		msg.put("typ_wiadomosci", "a_greetings");
		msg.put("id_wiadomosci", new String(ID + "_" + new Date().toString()));
		msg.put("id_serwera", new Integer(ID));
		msg.put("id_nadawcy", new Integer(ID));
	}

	public String getStringJSON() {
		return msg.toJSONString();
	}

	public long getID() {
		return (Long) this.toParse.get("id_serwera");
	}

	public boolean isPassBy() {

		return (ID != IDnadawcy);
	}
}
