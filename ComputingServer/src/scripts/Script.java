package scripts;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Script {
	
		
	public void runScript(String sctripName) {
		try {
			String[] command = { "perl",
					sctripName, "22" };
			System.out.println(Arrays.toString(command));
			ProcessBuilder builder = new ProcessBuilder(command);
			builder.redirectErrorStream(true);
			Process process = builder.start();
			InputStream stdout = process.getInputStream();
			process.waitFor();
			try {
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(stdout));
				String line;

				line = reader.readLine();
			    while (line != null && ! line.trim().equals("--EOF--")) {
			        System.out.println ("Stdout: " + line);
			        line = reader.readLine();
			    }
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			System.out.println("exitValue = " + process.waitFor());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
	
}
