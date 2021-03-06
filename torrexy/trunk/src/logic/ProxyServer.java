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
			proxyServer = new ServerSocket(PROXY_PORT);// naslouchaj�c� na 0.0.0.0 TODO zm�nit jen na 1 IP
			while (true) {
				Socket s = proxyServer.accept();
				getNewClient(s).start();
			}
		} catch (IOException e) { //TODO vyhazuje v�ce vyj�mek - o�et�it
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
					System.err.println("Error: Proxy server socket has not been closed.");
					e.printStackTrace(System.err);
				}
			} else {
				//TODO proxyServer == null
				//TODO ud�lat close metodu pro ProxyServer t��du?
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
		System.out.println("New client: " + socket.getInetAddress().getHostAddress()+", "+socket.getInetAddress().getHostName());
		return client;
	}
	public static void main(String[] args) {
		new ProxyServer().run();
	}
}