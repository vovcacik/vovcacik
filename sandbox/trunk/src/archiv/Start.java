package archiv;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Start {
	public static void main(String a[]) {
		try {
			File inFolder = new File("C:/test/zabal/kufr2/");
			File outFolder = new File("C:/test/zabal/out.zip");
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(outFolder)));
			BufferedInputStream in = null;
			byte[] data = new byte[1000];
			File files[] = seznam(inFolder.listFiles());
			for (File f : files) {
				System.out.println(f.getPath());
			}
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					continue;
				}
				in = new BufferedInputStream(new FileInputStream(files[i].getPath()), 1000);
				String entryPath = files[i].getPath().substring(files[i].getPath().indexOf("C:/test/zabal/kufr2/") + "C:/test/zabal/kufr2/".length());
				System.out.println(entryPath);
				out.putNextEntry(new ZipEntry(entryPath));
				int count;
				while ((count = in.read(data, 0, 1000)) != -1) {
					out.write(data, 0, count);
				}
				out.closeEntry();
			}
			out.flush();
			out.close();
		} catch (Exception e) {
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