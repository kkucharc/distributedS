package general;

import network.Server;

public class Start {
	static String fileConf = "/home/kasiaonthepole/workspace/ComputingServer/configuration/conf3.json";
	static Server server;

	public static void main(String[] args) {
		server = new Server(new Configuration(fileConf));
	}

}
