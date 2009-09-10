package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

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
				while (true) {
					String message = in.readLine();
					if (message == null) break;
					if (message.startsWith("/quit")) break;
					//TODO zpracovat message
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
			server.getClients().remove(this); // vymazat ze seznamu
			try {
				out.close(); // zavøít vıstupní proud
				in.close(); // zavøít vstupní proud
				socket.close(); // zavøít soket
			} catch (IOException e) {
			}
		}
	}