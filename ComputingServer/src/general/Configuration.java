package general;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Configuration {

	Object obj;
	JSONObject jsonObject;

	public Configuration(String file) {
		parseConfiguration(file);
	}

	private void parseConfiguration(String file) {
		JSONParser parser = new JSONParser();

		try {

			obj = parser.parse(new FileReader(file));

			jsonObject = (JSONObject) obj;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public String getReceiverHostname() {
		return (String) jsonObject.get("hostnameReceiver");
	}

	public String getClientServiceHostname() {
		return (String) jsonObject.get("hostnameClientService");
	}

	public String getConnectionHostname() {
		return (String) jsonObject.get("hostnameConnected");
	}

	public long getReceiverPort() {
		return (Long) jsonObject.get("portReceiver");
	}

	public long getClientServicePort() {
		return (Long) jsonObject.get("portClientService");
	}

	public long getConnectionPort() {
		return (Long) jsonObject.get("portConnected");
	}

	public long getID() {
		
		return (Long) jsonObject.get("id");
	}
}
