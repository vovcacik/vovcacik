package logika;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipFile;

public class Unpack {
	
	public static void unpack(File cilDir, ZipFile jarFile, java.util.List<ZipZaznam> entries) {
		final int BUFFER = 2048;
		try {
			cilDir.mkdirs();
			BufferedOutputStream dest = null;
			BufferedInputStream is = null;

			for (ZipZaznam entry : entries) {
				entry.mkdirs(cilDir.getAbsolutePath());

				is = new BufferedInputStream(jarFile.getInputStream(entry.getZipEntry()));
				int count;
				byte data[] = new byte[BUFFER];

				FileOutputStream fos = new FileOutputStream(
						cilDir.getAbsolutePath() + entry.getName().substring(Archiv.TRUNK.length()));
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

}
