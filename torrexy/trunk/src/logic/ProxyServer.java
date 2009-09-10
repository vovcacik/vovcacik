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
	public static final int PORT = 10997;
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
				ClientThread newclient = new ClientThread(s); // vytvoøit
				// vlákno
				clients.add(newclient); // pøidat klienta do seznamu
				newclient.start(); // spustit vlákno
				broadcast("Pøipojen.", newclient); // odeslat ostatním zprávu
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

	/** Pošle všem kromì odesílatele zprávu. */
	public synchronized void broadcast(String message, ClientThread from) {
		String ip = from.socket.getInetAddress().getHostAddress();
		message = ip + ": " + message;
		for (ClientThread clt : getClients()) {
			if (clt == from) continue;
			clt.out.println(message);
			clt.out.flush();
		}
		System.out.println(message);
	}

	/**
	 * Vrací seznam klientù. * Tuto metodu je nutné pouívat místo pøímého
	 * pøístupu k promìnné clients - * - ten není synchronizovanı.
	 */
	public synchronized List<ClientThread> getClients() {
		return clients;
	}

	/** Vlákno zajišující ètení zpráv od klienta. */
	private class ClientThread extends Thread {
		/** Klientùv soket. */
		Socket socket;
		/** Vıstupní proud. */
		PrintStream out;
		/** Vstupní proud. */
		BufferedReader in;

		/** Vytvoøí nové klientské vlákno. */
		public ClientThread(Socket socket) {
			this.socket = socket;
			try {
				out = new PrintStream(socket.getOutputStream()); // vytvoøit
																	// PrintStream
				in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // vytvoøit
																							// BufferedReader
																							// //
																							// BufferedReader
			} catch (IOException e) {
				e.printStackTrace(System.err);
				close();
			}
		}

		public void run() {
			try {
				while (true) {
					String message = in.readLine();
					if (message == null) break;
					if (message.startsWith("/quit")) break;
					else broadcast(message, this);
				}
			} catch (IOException e) {
				System.err.println("Kvuli chybe odpojen klient.");
				e.printStackTrace(System.err);
			} finally {
				close(); // odpojit
			}
		}

		/** Odpojí klienta. */
		public void close() {
			broadcast("Odpojen.", this);// odeslat zprávu o odpojení
			getClients().remove(this); // vymazat ze seznamu
			try {
				out.close(); // zavøít vıstupní proud
				in.close(); // zavøít vstupní proud
				socket.close(); // zavøít soket
			} catch (IOException e) {
			}
		}
	}

	public static void main(String[] args) {
		new ProxyServer().run();
	}
}