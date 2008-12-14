package remoteHand;

import java.io.IOException;

public class RHProtocol {
	static final String BYE = "HTTP/1.1 204 No Content\n";
	static final String PREFIX = "GET /RemoteHand/?";
	static final String SUFFIX = " HTTP/1.1";

	public String processInput(String theInput) {
		if (theInput != null) {
			theInput = theInput.replace("%20", " ");
			if (theInput.startsWith(PREFIX)) {
				String[] args = getArgs(theInput);
				processArgs(args);
			}
		}
		return BYE;
	}

	private String[] getArgs(String theInput) {
		theInput = theInput.substring(PREFIX.length(), theInput.length()
				- SUFFIX.length());
		String[] args = theInput.split(" ");
		return args;
	}

	private void processArgs(String[] args) {
		// TODO vymazat debug stringy
		for (String s : args) {
			System.out.println(s);
		}
		System.out.println(args.length);

		// definitions
		if (args[0].equals("a")) {
			String[] cmds = {"C:\\WINDOWS\\system32\\notepad.exe",
					"C:\\test.txt"};
			try {
				Runtime.getRuntime().exec(cmds);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (args[0] == "b") {
			// nothing so far
		} else {
			// don't do anything
		}
	}
}