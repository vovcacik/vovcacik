package logika;

import java.io.File;
import java.util.zip.ZipEntry;

public class ZipZaznam {

	ZipEntry zipEntry;

	public static ZipZaznam getZipZaznam(String name) {
		return (new ZipZaznam(name));
	}

	public static ZipZaznam getZipZaznam(ZipEntry zipEntry) {
		return zipEntry==null ? null : new ZipZaznam(zipEntry);
	}

	public static ZipZaznam getZipZaznam(Object object) {
		ZipEntry entry;
		try {
			entry = (ZipEntry) object;
		} catch (ClassCastException e) {
			e.printStackTrace();
			return null;
		}
		return getZipZaznam(entry);
	}

	private ZipZaznam(String name) {
		this.zipEntry = new ZipEntry(name);
	}

	private ZipZaznam(ZipEntry zipEntry) {
		this.zipEntry = zipEntry;
	}

	public String getName() {
		return zipEntry.getName().replace("\\", "/");
	}

	public ZipEntry getZipEntry() {
		return this.zipEntry;
	}

	public boolean isInTrunk() {
		return getName().startsWith(Archiv.TRUNK);
	}

	public void mkdirs(String pathCilDir) {
		int indexLomitka;
		String jmeno = getName();
		if ((indexLomitka = jmeno.lastIndexOf('/')) != -1) {
			String dir = jmeno.substring(Archiv.TRUNK.length(), indexLomitka);
			System.out.println("Extracting directory: " + pathCilDir + dir);
			(new File(pathCilDir + dir)).mkdirs();
		}
	}
	
	@Override
	public String toString() {
		return zipEntry.toString();
	}

}
