package JSONs;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

import org.json.simple.JSONObject;

import scripts.Script;

public class ScriptJSON {

	String msg;
	String jsonmsg;

	Object obj;

	public ScriptJSON(String request, JSONObject obj) {
		msg = request;
		parseRequest(obj);
	}

	void parseRequest(JSONObject obj) {

		if (obj == null)
			System.out.println("NULL!");

		String skrypt = (String) obj.get("skrypt");
		String idMess = ((String) obj.get("id_wiadomosci"));
		int idTask = new Random().nextInt();
		jsonmsg = (new ScriptResponse(idTask, idMess, "w oczekiwaniu", ""))
				.getStringJSON();

		try {
			String name = "temp" + new Random().nextInt(4);
			PrintWriter writer = new PrintWriter(name);

			writer.print(skrypt);
			writer.close();
			new Script().runScript(name);
		} catch (FileNotFoundException e) {
			System.err.println("Nie udalo sie zapisac pliku");
			e.printStackTrace();
		}

	}

	public String getStringJSON() {
		return jsonmsg;
	}

}
