package logic;

import java.io.IOException;
import java.net.InetAddress;
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
				getNewClient(s).start();
				System.out.println("New client: " + s.getInetAddress());
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

	public ClientThread getNewClient(InetAddress dstInetAddress, int dstPort) {
		Socket socket = null;
		try {
			socket = new Socket(dstInetAddress, dstPort);
		} catch (IOException e) {
			System.err.println("Could not create new socket to "+dstInetAddress.getHostAddress()+" on port "+dstPort);
			e.printStackTrace();
		}
		return getNewClient(socket);
	}
	public ClientThread getNewClient(Socket socket) {
		ClientThread client = null;
		client = new ClientThread(socket,this);
		getClients().add(client);
		return client;
	}
	public static void main(String[] args) {
		new ProxyServer().run();
	}
}