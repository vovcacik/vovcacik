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

/** Hlavn� t��da HTTP stahova�e. */

public class HttpDownload {
	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Pou�it�: java HttpDownload URL");
			System.exit(-1);
		}

		URL url = null;
		try {
			url = new URL(args[0]); // na��st URL
		} catch (MalformedURLException e) {
			System.out.println("Neplatn� URL.");
			System.exit(-1);
		} // vytvo�it adresu pro p�ipojen� soketu

		SocketAddress addr = new InetSocketAddress(url.getHost(), url.getPort());
		SocketChannel socket = null;
		FileOutputStream fos = null;
		FileChannel file = null;

		try {
			// vytvo�it a otev��t soket
			socket = SocketChannel.open(addr);
			// vytvo�it soubor a souborov� kan�l
			String filename = url.getFile().substring(url.getFile().lastIndexOf('/') + 1);
			File f = new File(filename);
			f.createNewFile();
			System.out.println(f.getAbsolutePath());
			// vytvo�it nov� soubor
			fos = new FileOutputStream(f);
			// vytvo�it v�stupn� proud
			file = fos.getChannel();
			// ze vstupn�ho proudu z�skat kan�l pro z�pis
			// vytvo�it buffer
			ByteBuffer buffer = ByteBuffer.allocateDirect(256);
			// sestavit HTTP po�adavek
			String http = "GET " + url.getFile() + " HTTP/1.0\n" + "Host: " + url.getHost() + "\n\n";
			// zapsat do kan�lu HTTP po�adavek
			int nbytes = 0;
			byte[] bytes = http.getBytes();
			// p�ev�st HTTP po�adavek na pole bajt�
			while (nbytes < bytes.length) {
				// opakovat cyklus, dokud je co zapisovat
				int len = Math.min(buffer.capacity(), bytes.length - nbytes);
				buffer.put(bytes, nbytes, len); // vlo�it bajty do bufferu
				buffer.flip();
				nbytes += len;
				int n = 0;
				while (n < len) {
					// zapsat v�echny bajty do soketu
					n += socket.write(buffer);
				}
			}
			while (true) {
				// po��d ��st data ze soketu
				int read = socket.read(buffer);
				if (read == -1) break;
				// sign�l uzav�en� soketu
				buffer.flip();
				int n = 0;
				while (n < read) {
					// zapsat v�echny bajty do souboru
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