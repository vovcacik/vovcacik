package logika;

import gui.OknoZabal;
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
import javax.swing.JOptionPane;

public class Archiv {

	public Archiv() {
	}

	public void rozbal(String pathZdrojJar, String pathCilDir) {
		pathZdrojJar = pathZdrojJar.replace('\\', '/');
		pathCilDir = pathCilDir.replace('\\', '/');
		final int BUFFER = 2048;
		try {
			File cilDir = new File(pathCilDir);
			cilDir.mkdirs();
			BufferedOutputStream dest = null;
			BufferedInputStream is = null;
			ZipEntry entry;
			ZipFile zdrojJar = new ZipFile(pathZdrojJar);
			Enumeration entries = zdrojJar.entries();

			while (entries.hasMoreElements()) {
				entry = (ZipEntry) entries.nextElement();
				System.out.println("ENTRY: " + entry.getName());
				if (!entry.getName().startsWith("/trunk")) {
					continue;
				}
				int iLomitka;
				String jmeno = entry.getName().replace('\\', '/');
				if ((iLomitka = jmeno.lastIndexOf('/')) != -1) {
					String dir = entry.getName().substring("/trunk".length(), iLomitka);
					System.out.println("Extracting directory: " + pathCilDir + dir);
					(new File(pathCilDir + dir)).mkdirs();
				}

				System.out.println("Extracting: " + entry);
				is = new BufferedInputStream(zdrojJar.getInputStream(entry));
				int count;
				byte data[] = new byte[BUFFER];
				FileOutputStream fos = new FileOutputStream(pathCilDir + entry.getName().substring("/trunk".length()));
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
		String path = getThisPath();
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

	public String getThisPath() {
		URL url = this.getClass().getResource("Archiv.class");
		String path = url.getPath();
		path = path.substring(path.indexOf("file:/") + "file:/".length(), path.lastIndexOf('!'));
		return path.replaceAll("%20", " ");
	}

	public void zabal(String newJar, String adresar) {
		newJar = newJar.replace('\\', '/');
		adresar = adresar.replace('\\', '/');
		URL url = this.getClass().getResource("Archiv.class");
		String path = this.getThisPath();

		File jarFile = new File(path);
		File folder = new File(adresar);
		File files[] = seznam(folder.listFiles());
		for (File f : files) {
			System.out.println(f.getPath());
		}

		File secFile;
		try {
			secFile = new File(newJar);
			if (secFile.equals(jarFile)) {
				OknoZabal oknoZabal = new OknoZabal();
				oknoZabal.setVisible(true);
				JOptionPane.showMessageDialog(null, "Zvolte jiný než aktuální archiv!", "Chyba", JOptionPane.ERROR_MESSAGE);
				return;
			}
			secFile.createNewFile();

			byte[] buf = new byte[1024];

			ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(jarFile)));
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(secFile)));

			ZipEntry entry = zin.getNextEntry();
			while (entry != null) {
				String name = entry.getName();
				name = name.replaceAll("\\\\", "/");
				boolean notInFiles = true;
				System.out.println("\n\n-----------------------------\nEntry name: " + name);
				for (File f : files) {
					String fname = f.getPath();
					String adr = adresar;
					adr = adr.replaceAll("\\\\", "/");
					fname = fname.replaceAll("\\\\", "/");
					fname = fname.substring(fname.indexOf(adr) + adr.length());
					fname = "/trunk/" + fname;
					System.out.println("in folder: " + fname);
					if (fname.equals(name)) {
						System.out.println("NEZARAZENO: " + fname);
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
				String entryPath = files[i].getPath().substring(files[i].getPath().indexOf(adresar) + adresar.length());
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
