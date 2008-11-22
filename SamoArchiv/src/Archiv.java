import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
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
}
