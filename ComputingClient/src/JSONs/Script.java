package JSONs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import org.json.simple.JSONObject;

public class Script {
	JSONObject msg;

	public Script(String fileName) {
		createJSONScript(fileName);
	}

	private void createJSONScript(String fileName) {
		String content;
		content = readFile(fileName);

		msg = new JSONObject();
		msg.put("typ_wiadomosci", "zlecenie_zadania");
		msg.put("id_wiadomosci", new Integer(new Random().nextInt()).toString());
		msg.put("waga", new Integer(1));
		msg.put("skrypt", content);

	}

	public String getJSONScript() {
		return msg.toJSONString();
	}

	static String readFile(String file) {

		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));

			try {
				while ((line = reader.readLine()) != null) {
					stringBuilder.append(line);
					stringBuilder.append(ls);
				}
			} catch (IOException e) {
				System.out.println("Cos poszlo nie tak z plikiem");
			}

			return stringBuilder.toString();
		} catch (FileNotFoundException e) {
			System.out.println("Cos poszlo nie tak z plikiem");
		}
		return stringBuilder.toString();
	}

}
