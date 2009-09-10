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
	public static final int PORT = 10997;
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
				ClientThread newclient = new ClientThread(s); // vytvo�it
				// vl�kno
				clients.add(newclient); // p�idat klienta do seznamu
				newclient.start(); // spustit vl�kno
				broadcast("P�ipojen.", newclient); // odeslat ostatn�m zpr�vu
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

	/** Po�le v�em krom� odes�latele zpr�vu. */
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
	 * Vrac� seznam klient�. * Tuto metodu je nutn� pou��vat m�sto p��m�ho
	 * p��stupu k prom�nn� clients - * - ten nen� synchronizovan�.
	 */
	public synchronized List<ClientThread> getClients() {
		return clients;
	}

	/** Vl�kno zaji��uj�c� �ten� zpr�v od klienta. */
	private class ClientThread extends Thread {
		/** Klient�v soket. */
		Socket socket;
		/** V�stupn� proud. */
		PrintStream out;
		/** Vstupn� proud. */
		BufferedReader in;

		/** Vytvo�� nov� klientsk� vl�kno. */
		public ClientThread(Socket socket) {
			this.socket = socket;
			try {
				out = new PrintStream(socket.getOutputStream()); // vytvo�it
																	// PrintStream
				in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // vytvo�it
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

		/** Odpoj� klienta. */
		public void close() {
			broadcast("Odpojen.", this);// odeslat zpr�vu o odpojen�
			getClients().remove(this); // vymazat ze seznamu
			try {
				out.close(); // zav��t v�stupn� proud
				in.close(); // zav��t vstupn� proud
				socket.close(); // zav��t soket
			} catch (IOException e) {
			}
		}
	}

	public static void main(String[] args) {
		new ProxyServer().run();
	}
}