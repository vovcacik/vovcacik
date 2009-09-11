package logic;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class ProxyServer {
	public static final int PROXY_PORT = 8282;
	private ServerSocket proxyServer;
	private List<ClientThread> clients;

	public void run() {
		clients = new java.util.ArrayList<ClientThread>();

		try {
			proxyServer = new ServerSocket(PROXY_PORT);// naslouchající na 0.0.0.0
			while (true) {
				Socket s = proxyServer.accept();
				ClientThread newClient = new ClientThread(s, this);
				System.out.println("New client: " + s.getInetAddress());
				clients.add(newClient);
				newClient.start();
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			if (proxyServer != null) {
				for (ClientThread clt : getClients()) {
					clt.close();
				}
				clients.clear();
				try {
					proxyServer.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * Vrací seznam klientù. * Tuto metodu je nutné používat místo pøímého
	 * pøístupu k promìnné clients - * - ten není synchronizovaný.
	 */
	public synchronized List<ClientThread> getClients() {
		return clients;
	}
	
	public static void main(String[] args) {
		new ProxyServer().run();
	}
}