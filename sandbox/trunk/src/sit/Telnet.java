package sit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/** Hlavn� t��da programu Telnet. Spou�t� se p��kazem "java Telnet host port". */
public class Telnet {

	/** Hlavn� metoda. P�ipoj� se k serveru a spust� dv� vl�kna TelnetThread. */
	public static void main(String[] args) {
		// ov��it po�et parametr�
		if (args.length < 2) {
			System.out.println("Pou�it�: java Telnet host port");
			return;
		}

		String hostname = args[0]; // z�skat hostname

		// z�skat port
		int port = 0;
		try {
			port = Integer.parseInt(args[1]); // p�ev�st parametr na ��slo
		} catch (NumberFormatException e) {
			System.out.println("Neplatn� port");
			System.exit(-1);
		}

		// vytvo�it adresu a soket
		InetSocketAddress addr = new InetSocketAddress(hostname, port);
		Socket socket = new Socket();

		try {
			socket.connect(addr);// pokusit se p�ipojit

			// vl�kno, kter� �te ze soketu a tiskne na konzoli
			Thread reading = new TelnetThread(socket.getInputStream(), System.out);
			// vl�kno, kter� �te z konzole a pos�l� data do soketu
			Thread writing = new TelnetThread(System.in, socket.getOutputStream());
			writing.setDaemon(true); // vytvo�it d�mona

			// spustit vl�kna
			reading.start();
			writing.start();

			reading.join(); // po�kat odpojen�
			socket.close(); // uzav�it soket
		}

		/* Probuzen� hlavn�ho vl�kna z �ek�n�. Nem�lo by nastat. */
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		/* Vypr�el �as p�ipojen� k serveru. */
		catch (SocketTimeoutException e) {
			System.out.println("Nelze se p�ipojit k serveru.");
			System.exit(-1);
		}

		/* Nezn�m� host. */
		catch (UnknownHostException e) {
			System.out.println("Nezn�m� host.");
			System.exit(-1);
		}

		/*
		 * Jin� IO v�jimka. Obvykle NoRouteToHostException nebo
		 * ConnectException.
		 */
		catch (IOException e) {
			System.out.println("IO chyba:");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Toto vl�kno �ek� na data ze zadan�ho vstupn�ho proudu a okam�it� je
	 * p�epos�l� do v�stupn�ho proudu.
	 */
	static class TelnetThread extends Thread {

		/** Vstupn� proud. */
		private InputStream is;
		/** V�stupn� proud. */
		private OutputStream os;

		/** Vytvo�� nov� vl�kno pracuj�c� se zadan�mi proudy. */
		public TelnetThread(InputStream is, OutputStream os) {
			this.is = is;
			this.os = os;
		}

		/** Hlavn� metoda vl�kna. */
		public void run() {
			byte[] b = new byte[64]; // vytvo�it buffer

			try {
				while (true) {
					int nbytes = is.read(b);// p�e��st bajty
					if (nbytes == -1) break; // ov��it konec proudu
					os.write(b, 0, nbytes); // zapsat bajty
				}
				System.out.println("Vstupn� proud uzav�en.");
			} catch (IOException e) {
				System.out.println("IO chyba:");
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
}
