package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/** Hlavní tøída zajišující provoz chatovacího serveru. */
public class ProxyServer {
	/** Port, na kterém server pobìí. */
	public static final int PORT = 8989;
	/** Soket chatovacího serveru. */
	private ServerSocket server;
	/** Seznam pøipojenıch klientù. */
	private List<ClientThread> clients;

	/** Spustí server. */

	public void run() {
		clients = new java.util.ArrayList<ClientThread>();

		try {
			server = new ServerSocket(PORT); // vytvoøit serverovı soket
			// naslouchající na 0.0.0.0
			while (true) {
				Socket s = server.accept(); // pøijmout klienta
				ClientThread newclient = new ClientThread(s, this); // vytvoøit vlákno
				System.out.println("New client: " + s.getInetAddress());
				clients.add(newclient); // pøidat klienta do seznamu
				newclient.start(); // spustit vlákno
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			if (server != null) {
				// odpojit všechny klienty
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
	 * Vrací seznam klientù. * Tuto metodu je nutné pouívat místo pøímého
	 * pøístupu k promìnné clients - * - ten není synchronizovanı.
	 */
	public synchronized List<ClientThread> getClients() {
		return clients;
	}
	
	public static void main(String[] args) {
		new ProxyServer().run();
	}
}