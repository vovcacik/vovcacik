package sit;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/** Hlavní tøída celého serveru. */
public class TimeServer {

	/** Port, na kterém budeme naslouchat. */
	public static final int PORT = 10997;

	/** Hlavní metoda programu. */
	public static void main(String[] args) {
		ServerSocket server = null; // serverovı soket

		try {
			server = new ServerSocket(); // vytvoøit serverovı soket

			// získat adresu pro všechna síová rozhraní
			SocketAddress addr = new InetSocketAddress(InetAddress.getByName("0.0.0.0"), PORT);
			server.bind(addr); // zapnout naslouchání

			while (true) {
				Socket client = server.accept(); // poèkat na pøijetí klienta
				// vytvoøit proud, do kterého je moné posílat øetìzce
				PrintStream output = new PrintStream(client.getOutputStream());
				output.println(new java.util.Date()); // odeslat aktuální èas
														// a datum

				try {
					output.close(); // zavøít náš vlastní vıstupní proud
					client.close(); // odpojit klienta
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally { // v kadém pøípadì uzavøeme serverovı soket
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {

				}
			}
		}
	}
}
