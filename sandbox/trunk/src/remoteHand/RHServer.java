package remoteHand;

import java.io.IOException;
import java.net.ServerSocket;

public class RHServer {
	private static final int PORT = 50000;

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = null;
		boolean listening = true;

		try {
			serverSocket = new ServerSocket(PORT);
		} catch (IOException e) {
			System.err.println("Could not listen on port: " + PORT + ". ");
			System.exit(-1);
		}

		while (listening) {
			new RHThread(serverSocket.accept()).start();
		}

		serverSocket.close();
	}
}