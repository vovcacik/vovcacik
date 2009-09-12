package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;

public class ClientThread extends Thread {
	ProxyServer proxyServer;
	Socket socket;
	PrintStream out;
	BufferedReader in;
	ClientThread dst;

	public ClientThread(Socket socket, ProxyServer server) {
		this.socket = socket;
		this.proxyServer = server;
		this.dst = null;
		try {
			out = new PrintStream(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace(System.err);
			close();
		}
	}

	public void run() {
		while(true) {
			Message msg = new Message(this);
			if(dst == null) {
				InetAddress dstInetAddress = msg.getDstInetAddress();
				int dstPort = msg.getDstPort();
				dst = proxyServer.getNewClient(dstInetAddress, dstPort);
				dst.setDst(this);
				msg.loadAll();
				dst.start();
			}
			dst.send(msg);
		}
	}
	void setDst(ClientThread dst) {
		this.dst = dst;
	}

	public BufferedReader getInput() {
		return this.in;
	}
	public void send(Message msg) {
		System.out.println("Message to: \n"+socket.getInetAddress().getHostAddress()+"\n"+
				socket.getInetAddress().getHostName()+"\n"+msg+"END OF MESSAGE\n");
		out.print(msg);
		out.flush();
	}
	/** Odpojí klienta. */
	public void close() {
		proxyServer.getClients().remove(this);
		try {
			out.close();
			in.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		proxyServer.getClients().remove(dst);
		if (dst!=null) dst.close();
		dst=null;
	}
}