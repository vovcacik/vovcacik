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
	Socket trackerSocket;
	PrintStream trackerOut;
	BufferedReader trackerIn;

	public ClientThread(Socket socket, ProxyServer server) {
		this.socket = socket;
		this.proxyServer = server;
		try {
			out = new PrintStream(socket.getOutputStream());
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			e.printStackTrace(System.err);
			close();
		}
	}

	public void run() {
		try {
			String message = "";
			while (true) {
				String line = in.readLine();
				if (line==null) break;
				message += line+"\n";
				if (line.equals("")) { //TODO zpráva mùže pokraèovat i po prázdném øádku
					System.out.println("###"+message+"###");//TODO vymazat
					processMessage(message);
					message="";
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			close();
		}
	}

	private void processMessage(String message) {
		Pattern pattern = Pattern.compile(TRACKER_URI_PATTERN, Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(message);
		InetAddress trackerInetAddress = null;
		int trackerPort = 80; // default HTTP port
		Socket trackerSocket = null;
		PrintStream trackerOut;
		BufferedReader trackerIn; //TODO close socket, in, out

		boolean found = false;
		while (matcher.find()) {
			found = true;
			String address = matcher.group(1);
			System.out.println(matcher.group(2));
			if (!matcher.group(2).equals("")){
				trackerPort = Integer.parseInt(matcher.group(2));
			}
			try {
				trackerInetAddress = InetAddress.getByName(address);
				trackerSocket = new Socket(trackerInetAddress, trackerPort);
			} catch (Exception e) {
				e.printStackTrace();
				close();
			}
			try {
				trackerOut = new PrintStream(trackerSocket.getOutputStream());
				trackerIn = new BufferedReader(new InputStreamReader(trackerSocket.getInputStream()));

				trackerOut.print(message);
				trackerOut.flush();
				
				while (true) {
					String line = trackerIn.readLine();
					if (line == null) break;
					out.println(line);
					System.out.println("///"+line+"///"); //TODO delete
				}
			} catch (IOException e){
				e.printStackTrace();
				close();
			}
		} if(!found){
			System.out.println("Remote server address (tracker) not found.");
		} 
	}
	/** Odpojí klienta. */
	public void close() {
		proxyServer.getClients().remove(this);
		try {
			out.close();
			in.close();
			socket.close();
			if(trackerOut!=null)trackerOut.close();
			if(trackerIn!=null)trackerIn.close();
			if(trackerSocket!=null)trackerSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}