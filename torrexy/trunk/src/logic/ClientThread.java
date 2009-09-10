package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/** Vl�kno zaji��uj�c� �ten� zpr�v od klienta. */
public class ClientThread extends Thread {
		/** Klient�v soket. */
		Socket socket;
		/** V�stupn� proud. */
		PrintStream out;
		/** Vstupn� proud. */
		BufferedReader in;
		ProxyServer server;

		/** Vytvo�� nov� klientsk� vl�kno. */
		public ClientThread(Socket socket, ProxyServer server) {
			this.socket = socket;
			this.server = server;
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
					//TODO zpracovat message
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
			server.getClients().remove(this); // vymazat ze seznamu
			try {
				out.close(); // zav��t v�stupn� proud
				in.close(); // zav��t vstupn� proud
				socket.close(); // zav��t soket
			} catch (IOException e) {
			}
		}
	}