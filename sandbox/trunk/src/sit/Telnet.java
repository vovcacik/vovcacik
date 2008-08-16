package sit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

/** Hlavní tøída programu Telnet. Spouští se pøíkazem "java Telnet host port". */
public class Telnet {

	/** Hlavní metoda. Pøipojí se k serveru a spustí dvì vlákna TelnetThread. */
	public static void main(String[] args) {
		// ovìøit poèet parametrù
		if (args.length < 2) {
			System.out.println("Použití: java Telnet host port");
			return;
		}

		String hostname = args[0]; // získat hostname

		// získat port
		int port = 0;
		try {
			port = Integer.parseInt(args[1]); // pøevést parametr na èíslo
		} catch (NumberFormatException e) {
			System.out.println("Neplatný port");
			System.exit(-1);
		}

		// vytvoøit adresu a soket
		InetSocketAddress addr = new InetSocketAddress(hostname, port);
		Socket socket = new Socket();

		try {
			socket.connect(addr);// pokusit se pøipojit

			// vlákno, které ète ze soketu a tiskne na konzoli
			Thread reading = new TelnetThread(socket.getInputStream(), System.out);
			// vlákno, které ète z konzole a posílá data do soketu
			Thread writing = new TelnetThread(System.in, socket.getOutputStream());
			writing.setDaemon(true); // vytvoøit démona

			// spustit vlákna
			reading.start();
			writing.start();

			reading.join(); // poèkat odpojení
			socket.close(); // uzavøit soket
		}

		/* Probuzení hlavního vlákna z èekání. Nemìlo by nastat. */
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		/* Vypršel èas pøipojení k serveru. */
		catch (SocketTimeoutException e) {
			System.out.println("Nelze se pøipojit k serveru.");
			System.exit(-1);
		}

		/* Neznámý host. */
		catch (UnknownHostException e) {
			System.out.println("Neznámý host.");
			System.exit(-1);
		}

		/*
		 * Jiná IO výjimka. Obvykle NoRouteToHostException nebo
		 * ConnectException.
		 */
		catch (IOException e) {
			System.out.println("IO chyba:");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	/**
	 * Toto vlákno èeká na data ze zadaného vstupního proudu a okamžitì je
	 * pøeposílá do výstupního proudu.
	 */
	static class TelnetThread extends Thread {

		/** Vstupní proud. */
		private InputStream is;
		/** Výstupní proud. */
		private OutputStream os;

		/** Vytvoøí nové vlákno pracující se zadanými proudy. */
		public TelnetThread(InputStream is, OutputStream os) {
			this.is = is;
			this.os = os;
		}

		/** Hlavní metoda vlákna. */
		public void run() {
			byte[] b = new byte[64]; // vytvoøit buffer

			try {
				while (true) {
					int nbytes = is.read(b);// pøeèíst bajty
					if (nbytes == -1) break; // ovìøit konec proudu
					os.write(b, 0, nbytes); // zapsat bajty
				}
				System.out.println("Vstupní proud uzavøen.");
			} catch (IOException e) {
				System.out.println("IO chyba:");
				e.printStackTrace();
				System.exit(-1);
			}
		}
	}
}
