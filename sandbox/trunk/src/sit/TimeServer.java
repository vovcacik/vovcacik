package sit;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

/** Hlavn� t��da cel�ho serveru. */
public class TimeServer {

	/** Port, na kter�m budeme naslouchat. */
	public static final int PORT = 10997;

	/** Hlavn� metoda programu. */
	public static void main(String[] args) {
		ServerSocket server = null; // serverov� soket

		try {
			server = new ServerSocket(); // vytvo�it serverov� soket

			// z�skat adresu pro v�echna s�ov� rozhran�
			SocketAddress addr = new InetSocketAddress(InetAddress.getByName("0.0.0.0"), PORT);
			server.bind(addr); // zapnout naslouch�n�

			while (true) {
				Socket client = server.accept(); // po�kat na p�ijet� klienta
				// vytvo�it proud, do kter�ho je mo�n� pos�lat �et�zce
				PrintStream output = new PrintStream(client.getOutputStream());
				output.println(new java.util.Date()); // odeslat aktu�ln� �as
														// a datum

				try {
					output.close(); // zav��t n� vlastn� v�stupn� proud
					client.close(); // odpojit klienta
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally { // v ka�d�m p��pad� uzav�eme serverov� soket
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {

				}
			}
		}
	}
}
