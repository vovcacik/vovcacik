package remoteHand;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class RHThread extends Thread {
	private Socket socket = null;

	public RHThread(Socket socket) {
		super("RHThread");
		this.socket = socket;
	}

	public void run() {

		try {
			PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(socket
					.getInputStream()));

			String inputLine, outputLine;
			RHProtocol rhp = new RHProtocol();
			outputLine = rhp.processInput(null);
			out.println(outputLine);

			while ((inputLine = in.readLine()) != null) {
				outputLine = rhp.processInput(inputLine);
				out.println(outputLine);
				if (outputLine.equals(RHProtocol.BYE))
					break;
			}
			out.close();
			in.close();
			socket.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
