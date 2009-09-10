package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/** Hlavn� t��da zaji��uj�c� provoz chatovac�ho serveru. */
public class ProxyServer {
	/** Port, na kter�m server pob��. */
	public static final int PORT = 8989;
	/** Soket chatovac�ho serveru. */
	private ServerSocket server;
	/** Seznam p�ipojen�ch klient�. */
	private List<ClientThread> clients;

	/** Spust� server. */

	public void run() {
		clients = new java.util.ArrayList<ClientThread>();

		try {
			server = new ServerSocket(PORT); // vytvo�it serverov� soket
			// naslouchaj�c� na 0.0.0.0
			while (true) {
				Socket s = server.accept(); // p�ijmout klienta
				ClientThread newclient = new ClientThread(s, this); // vytvo�it vl�kno
				System.out.println("New client: " + s.getInetAddress());
				clients.add(newclient); // p�idat klienta do seznamu
				newclient.start(); // spustit vl�kno
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			if (server != null) {
				// odpojit v�echny klienty
				for (ClientThread clt : getClients())
					clt.close();
				clients.clear();
				try {
					server.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * Vrac� seznam klient�. * Tuto metodu je nutn� pou��vat m�sto p��m�ho
	 * p��stupu k prom�nn� clients - * - ten nen� synchronizovan�.
	 */
	public synchronized List<ClientThread> getClients() {
		return clients;
	}
	
	public static void main(String[] args) {
		new ProxyServer().run();
	}
}