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
import java.util.zip.ZipInputStream;
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

	public void pribal(String jar, String adresar) {

		File jarFile = new File(jar);
		File folder = new File(adresar);
		File files[] = seznam(folder.listFiles());
		for (File f : files) {
			System.out.println(f.getPath());
		}
		// get a temp file
		File tempFile;
		try {
			tempFile = File.createTempFile(jarFile.getName(), null);

			// delete it, otherwise you cannot rename your existing zip to it.
			tempFile.delete();

			boolean renameOk = jarFile.renameTo(tempFile);
			if (!renameOk) {
				throw new RuntimeException("could not rename the file " + jarFile.getAbsolutePath() + " to " + tempFile.getAbsolutePath());
			}
			byte[] buf = new byte[1024];

			ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(tempFile)));
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(jarFile)));

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
				String entryPath = files[i].getPath().substring(files[i].getPath().indexOf("C:/test/zabal/kufr2/") + "C:/test/zabal/kufr2/".length());
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
			tempFile.delete();
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
