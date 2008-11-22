import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Archiv {

	public Archiv() {
	}

	void rozbal(String cestaRozbal) {
		final int BUFFER = 2048;
		try {
			File adresarRozbalit = new File(cestaRozbal);
			adresarRozbalit.mkdirs();
			BufferedOutputStream dest = null;
			BufferedInputStream is = null;
			ZipEntry entry;
			ZipFile zipFile = new ZipFile("C:/test/zabal/novyjar.jar");
			Enumeration e = zipFile.entries();

			while (e.hasMoreElements()) {
				entry = (ZipEntry) e.nextElement();

				int iLomitka;
				String jmeno = entry.getName().replace('\\', '/');
				if ((iLomitka = jmeno.lastIndexOf('/')) != -1) {
					String dir = entry.getName().substring(0, iLomitka);
					System.out.println("Extracting directory: " + cestaRozbal + dir);
					(new File(cestaRozbal + dir)).mkdirs();
				}

				System.out.println("Extracting: " + entry);
				is = new BufferedInputStream(zipFile.getInputStream(entry));
				int count;
				byte data[] = new byte[BUFFER];
				FileOutputStream fos = new FileOutputStream(cestaRozbal + entry.getName());
				dest = new BufferedOutputStream(fos, BUFFER);
				while ((count = is.read(data, 0, BUFFER)) != -1) {
					dest.write(data, 0, count);
				}
				dest.flush();
				dest.close();
				is.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public boolean isPrazdny() {
		URL url = this.getClass().getResource("Archiv.class");
		System.out.println(url.getPath());
		String path = url.getPath();
		path = path.substring(path.indexOf("file:/") + "file:/".length(), path.lastIndexOf('!'));

		System.out.println(path);

		ZipFile zip;
		try {
			zip = new ZipFile(path);
			Enumeration e2 = zip.entries();
			List<ZipEntry> entries2 = new ArrayList<ZipEntry>();
			while (e2.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) e2.nextElement();
				System.out.println("getname  " + entry.getName());
				if (entry.getName().startsWith("/trunk")) {
					System.out.println("FALSE");
					return false;
				}
			}
			System.out.println("TRUE");
			return true;
		} catch (IOException e1) {
			e1.printStackTrace();
			return true;
		}
	}

	public void zabal(String newJar, String adresar) {
		URL url = this.getClass().getResource("Archiv.class");
		String path = url.getPath();
		System.out.println("url path " + path);
		path = path.substring("file:/".length(), path.lastIndexOf('!'));
		System.out.println("zkracena path " + path);

		File jarFile = new File(path);
		File folder = new File(adresar);
		File files[] = seznam(folder.listFiles());
		for (File f : files) {
			System.out.println(f.getPath());
		}
		// get a temp file
		File secFile;
		try {
			secFile = new File(newJar);
			secFile.createNewFile();

			byte[] buf = new byte[1024];

			ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(jarFile)));
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(secFile)));

			ZipEntry entry = zin.getNextEntry();
			while (entry != null) {
				String name = entry.getName();
				boolean notInFiles = true;
				for (File f : files) {
					if (f.getName().equals(name)) {
						notInFiles = false;
						break;
					}
				}
				if (notInFiles) {
					// Add ZIP entry to output stream.
					out.putNextEntry(new ZipEntry(name));
					// Transfer bytes from the ZIP file to the output file
					int len;
					while ((len = zin.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
				}
				entry = zin.getNextEntry();
			}
			// Close the streams
			zin.close();
			// Compress the files
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					continue;
				}
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(files[i].getPath()));
				// Add ZIP entry to output stream.
				String entryPath = files[i].getPath().substring(files[i].getPath().indexOf("C:/test/zabal/kufr/") + "C:/test/zabal/kufr/".length());
				System.out.println("entrypath: " + entryPath);
				out.putNextEntry(new ZipEntry("/trunk" + entryPath));
				// Transfer bytes from the file to the ZIP file
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				// Complete the entry
				out.closeEntry();
				in.close();
			}
			// Complete the ZIP file
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static File[] seznam(File[] list) {
		List<File> seznam = new ArrayList<File>();
		for (File f : list) {
			seznam.add(f);
		}
		for (int i = 0; i < list.length; i++) {
			if (list[i].isDirectory()) {
				for (File f2 : seznam(list[i].listFiles())) {
					seznam.add(f2);
				}
			}
		}
		File[] ret = new File[seznam.size()];
		seznam.toArray(ret);
		return ret;
	}

}
