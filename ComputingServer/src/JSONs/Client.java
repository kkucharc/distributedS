package JSONs;

import java.util.Date;
import java.util.Random;

import org.json.simple.JSONObject;

public class Client {

	JSONObject receivedJSON;
	String replyJSON;
	JSONObject msg;
	int ID;
	long IDnadawcy;

	public Client(int ID, JSONObject jsonObj) {
		this.ID = ID;
		receivedJSON = jsonObj;
		parseRequest();
	}

	public Client(int ID, String skrypt) {
		this.ID = ID;
		
	}

	/**
	 * “typ_wiadomosci”: “wiadomosc_klienta”, // String “id_wiadomości” :
	 * ”nr_id_czas”, // String “id_nadawcy”: ”id_serwera” // int “id_odbiorcy”:
	 * ”id_serwera” // int “waga” : “liczba”, // int “skrypt”: “tresc” // String
	 */
	public void createClient(String skrypt) {

		msg = new JSONObject();
		msg.put("typ_wiadomosci", "wiadomosc_klienta");
		msg.put("id_wiadomosci", new String(ID + "_" + new Date().toString()));
		msg.put("id_nadawcy", new Integer(ID));
		msg.put("waga", 1);
		msg.put("skrypt", skrypt);

	}

	void parseRequest() {

		if (receivedJSON == null)
			System.out.println("NULL!");
		System.out.print(receivedJSON);
		System.out.println("PUSTO!");

		String idMess = ((String) receivedJSON.get("id_wiadomosci"));
		IDnadawcy = (Long) receivedJSON.get("id_nadawcy");
		int idTask = new Random().nextInt();
		replyJSON = (new GreetingsResponse(ID, idMess)).getStringJSON();
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

}
