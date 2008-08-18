package sit;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/** Hlavní tøída HTTP stahovaèe. */

public class HttpDownload {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Použití: java HttpDownload URL");
			System.exit(-1);
		}

		URL url = null;
		try {
			url = new URL(args[0]); // naèíst URL
		} catch (MalformedURLException e) {
			System.out.println("Neplatné URL.");
			System.exit(-1);
		} // vytvoøit adresu pro pøipojení soketu

		SocketAddress addr = new InetSocketAddress(url.getHost(), url.getPort());
		SocketChannel socket = null;
		FileOutputStream fos = null;
		FileChannel file = null;

		try {
			// vytvoøit a otevøít soket
			socket = SocketChannel.open(addr);
			// vytvoøit soubor a souborový kanál
			String filename = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
			File f = new File(filename);
			f.createNewFile();
			System.out.println(f.getAbsolutePath());
			// vytvoøit nový soubor
			fos = new FileOutputStream(f);
			// vytvoøit výstupní proud
			file = fos.getChannel();
			// ze vstupního proudu získat kanál pro zápis
			// vytvoøit buffer
			ByteBuffer buffer = ByteBuffer.allocateDirect(256);
			// sestavit HTTP požadavek
			String http = "GET " + url.getFile() + " HTTP/1.0\n" + "Host: " + url.getHost() + "\n\n";
			// zapsat do kanálu HTTP požadavek
			int nbytes = 0;
			byte[] bytes = http.getBytes();
			// pøevést HTTP požadavek na pole bajtù
			while (nbytes < bytes.length) {
				// opakovat cyklus, dokud je co zapisovat
				int len = Math.min(buffer.capacity(), bytes.length - nbytes);
				buffer.put(bytes, nbytes, len); // vložit bajty do bufferu
				buffer.flip();
				nbytes += len;
				int n = 0;
				while (n < len) {
					// zapsat všechny bajty do soketu
					n += socket.write(buffer);
				}
			}
			while (true) {
				// poøád èíst data ze soketu
				int read = socket.read(buffer);
				if (read == -1) break;
				// signál uzavøení soketu
				buffer.flip();
				int n = 0;
				while (n < read) {
					// zapsat všechny bajty do souboru
					n += file.write(buffer);
				}
			}
		} catch (IOException e) {
			e.printStackTrace(System.err);
		} finally {
			try {
				if (socket != null) socket.close();
				if (fos != null) fos.close();
				if (file != null) file.close();
			} catch (IOException e) {
			}
		}
	}
}