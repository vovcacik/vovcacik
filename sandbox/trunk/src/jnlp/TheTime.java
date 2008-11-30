package jnlp;

import java.net.MalformedURLException;
import java.net.URL;
import javax.jnlp.BasicService;
import javax.jnlp.ServiceManager;
import javax.jnlp.UnavailableServiceException;

public class TheTime {
	static BasicService basicService = null;

	public static void main(String args[]) {
		try {
			basicService = (BasicService) ServiceManager.lookup("javax.jnlp.BasicService");
		} catch (UnavailableServiceException e) {
			System.err.println("Lookup failed: " + e);
		}

		try {
			URL url = new URL("http://java.sun.com/");
			basicService.showDocument(url);
		} catch (MalformedURLException ignored) {
		}
		;
	}
}
