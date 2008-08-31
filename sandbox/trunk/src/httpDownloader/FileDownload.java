package httpDownloader;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/*
 * Command line program to download data from URLs and save it to local files.
 * Run like this: java FileDownload
 * http://schmidt.devlib.org/java/file-download.html @author Marco Schmidt
 */
public class FileDownload {
	public static void download(String address, String localFileName) {
		OutputStream out = null;
		URLConnection conn = null;
		InputStream in = null;
		try {
			URL url = new URL(address);
			System.out.println(url.getFile());
			out = new BufferedOutputStream(new FileOutputStream(localFileName));
			conn = url.openConnection();
			in = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int numRead;
			long numWritten = 0;
			while ((numRead = in.read(buffer)) != -1) {
				out.write(buffer, 0, numRead);
				numWritten += numRead;
			}
			System.out.println(localFileName + "\t" + numWritten);
		} catch (Exception exception) {
			exception.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException ioe) {
			}
		}
	}

	public static void download(String address) {
		int lastSlashIndex = address.lastIndexOf('/');
		if (lastSlashIndex >= 0 && lastSlashIndex < address.length() - 1) {
			download(address, address.substring(lastSlashIndex + 1));
		} else {
			System.err.println("Could not figure out local file name for " + address);
		}
	}

	public static void main(String[] args) {
		String prefix = "http://dl.getdropbox.com/u/17/Dropbox ";
		String verze = "0.6.314";
		String postfix = ".exe";
		download((prefix + verze + postfix).replaceAll(" ", "%20"));
	}
}