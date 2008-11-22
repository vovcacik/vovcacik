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
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Archiv {

	private String cestaArchiv = "";

	public Archiv(String cestaArchiv) {
		this.cestaArchiv = cestaArchiv;
	}

	void rozbal(String cestaRozbal) {
		final int BUFFER = 2048;
		try {
			BufferedOutputStream dest = null;
			BufferedInputStream is = null;
			JarEntry entry;
			JarFile jarfile = new JarFile(cestaArchiv);
			Enumeration e = jarfile.entries();

			while (e.hasMoreElements()) {
				entry = (JarEntry) e.nextElement();

				int iLomitka;
				if ((iLomitka = entry.getName().lastIndexOf('/')) != -1) {
					// Assume directories are stored parents first then
					// children.
					String dir = entry.getName().substring(0, iLomitka);
					System.out.println("Extracting directory: " + cestaRozbal + dir);
					// This is not robust, just for demonstration purposes.
					(new File(cestaRozbal + dir)).mkdir();

				}

				System.out.println("Extracting: " + entry);
				is = new BufferedInputStream(jarfile.getInputStream(entry));
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

	public void zabal(String adresar) {
		final int BUFFER = 2048;
		try {
			File f = new File(adresar);
			String files[] = f.list();
			BufferedInputStream origin = null;
			FileOutputStream dest = new FileOutputStream(cestaArchiv);
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
			// out.setMethod(ZipOutputStream.DEFLATED);
			byte data[] = new byte[BUFFER];
			// get a list of files from current directory

			for (int i = 0; i < files.length; i++) {
				System.out.println("Adding: " + files[i]);
				FileInputStream fi = new FileInputStream(adresar + files[i]);
				origin = new BufferedInputStream(fi, BUFFER);
				ZipEntry entry = new ZipEntry(files[i]);
				out.putNextEntry(entry);
				int count;
				while ((count = origin.read(data, 0, BUFFER)) != -1) {
					out.write(data, 0, count);
				}
				origin.close();
			}
			out.close();
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

		url = this.getClass().getResource("trunk");
		if (url == null) {
			System.out.println("return true");
			return true;
			// TODO vyhazuj chybu... asi?!
		}
		System.out.println(url.getPath());

		JarFile jar;
		List<JarEntry> entries = new ArrayList<JarEntry>();
		try {
			jar = new JarFile(path);
			Enumeration e = jar.entries();
			while (e.hasMoreElements()) {
				JarEntry entry = (JarEntry) e.nextElement();
				if (entry.getName().startsWith("trunk")) {
					entries.add(entry);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (JarEntry i : entries) {
			System.out.println(i.getName());
		}
		if (entries.size() == 1) {
			System.out.println("return true");
			return true;
		} else {
			System.out.println("return false");
			return false;
		}
	}
}
