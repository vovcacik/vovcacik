package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sun.net.InetAddressCachePolicy;

import com.sun.org.apache.bcel.internal.generic.INEG;

/** Vlákno zajišující ètení zpráv od klienta. */
public class ClientThread extends Thread {
		/** Klientùv soket. */
		Socket socket;
		/** Vıstupní proud. */
		PrintStream out;
		/** Vstupní proud. */
		BufferedReader in;
		ProxyServer server;

		/** Vytvoøí nové klientské vlákno. */
		public ClientThread(Socket socket, ProxyServer server) {
			this.socket = socket;
			this.server = server;
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
				String message = "";
				while (true) {
					String line = in.readLine();
					if (line == null) break;
					if (line.startsWith("/quit")) break;
					message += line+"\n";
					if (line.equals("")) {
						System.out.println("///\n"+message+"///\n\n");
						processMessage(message);
						message="";
					}
				}
			} catch (IOException e) {
				System.err.println("Kvuli chybe odpojen klient.");
				e.printStackTrace(System.err);
			} finally {
				close(); // odpojit
			}
		}

		private void processMessage(String message) {
			Pattern pattern = Pattern.compile("http://.+?/{1}");
			Matcher matcher = pattern.matcher(message);
			InetAddress inetAddress = null;
			Socket socket = null;
			PrintStream serverout;
			BufferedReader serverin;

			boolean found = false;
			while (matcher.find()) {
				String address = matcher.group();
				address = address.substring(7, address.length()-1);
				try {
					inetAddress = InetAddress.getByName(address);
				} catch (UnknownHostException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				found = true;
				try {
					socket = new Socket(inetAddress, 80);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					serverout = new PrintStream(socket.getOutputStream());
					serverin = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					serverout.print(message);
					serverout.flush();
					message = "";
					while (true) {
						String line = serverin.readLine();
						if (line == null) break;
						if (line.startsWith("/quit")) break;
						message += line+"\n";
						if (line.equals("")||true) {
							System.out.println("///\n"+message+"///\n\n");
							out.print(message);
							message="";
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				break;
			}
			if(!found){
				System.out.println("No match.");
			}

		}

		/** Odpojí klienta. */
		public void close() {
			server.getClients().remove(this); // vymazat ze seznamu
			try {
				out.close(); // zavøít vıstupní proud
				in.close(); // zavøít vstupní proud
				socket.close(); // zavøít soket
			} catch (IOException e) {
			}
		}
	}