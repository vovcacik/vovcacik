package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientThread extends Thread {
	private static final String TRACKER_URI_PATTERN = "^GET http://(.+?):?(\\d*)/.* HTTP/\\d.\\d$";
	//group 1 = inetAddress of tracker; group 2 = port
//	private static final String TRACKER_URI_PATTERN = "http://(.+?)/{1}";//group 1=address
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
//			if(trackerOut!=null)trackerOut.close();
//			if(trackerIn!=null)trackerIn.close();
//			if(trackerSocket!=null)trackerSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}