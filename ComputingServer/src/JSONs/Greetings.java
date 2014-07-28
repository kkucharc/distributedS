package JSONs;

import java.util.Date;
import java.util.Random;

import org.json.simple.JSONObject;

public class Greetings {
	JSONObject receivedJSON;
	String replyJSON;
	JSONObject msg;
	int ID;
	long IDnadawcy;
	public Greetings(int ID, JSONObject jsonObj) {
		this.ID = ID;
		receivedJSON = jsonObj;
		parseRequest();
	}

	public Greetings(int ID) {
		this.ID = ID;
		createGreetings();
	}
	public void createGreetings() {

		msg = new JSONObject();
		msg.put("typ_wiadomosci", "greetings");
		msg.put("id_wiadomosci", new String(ID + "_" + new Date().toString()));
		msg.put("id_nadawcy", new Integer(ID));

	}
	
	void parseRequest() {

		if (receivedJSON == null)
			System.out.println("NULL!");

		String idMess = ((String) receivedJSON.get("id_wiadomosci"));
		IDnadawcy = (Long) receivedJSON.get("id_nadawcy");
		int idTask = new Random().nextInt();
		replyJSON = (new GreetingsResponse(ID, idMess))
				.getStringJSON();
	}


	
	public String getStringJSON() {
		return msg.toJSONString();
	}

	public String getStringResponse() {
		
		return replyJSON;
	}

	public boolean isPassBy() {

		return (ID != IDnadawcy);
	}
	public long getID() {
		return IDnadawcy;
	}
}
