package logika;

import java.io.File;
import java.util.zip.ZipEntry;

/**
 * Třída obaluje instance ZipEntry a poskytuje pro ně dodatečné metody.
 * @author Vlastimil Ovčáčík
 */
class ZipZaznam {

	ZipEntry zipEntry;

	/**
	 * Metoda vytvoří instanci ZipZaznam podle daného jména (param name)
	 * @param name jméno nové instance ZipZaznam
	 * @return instanci ZipZaznam
	 */
	static ZipZaznam getZipZaznam(String name) {
		return (new ZipZaznam(name));
	}

	/**
	 * Metoda zabalí zipEntry do třídy ZipZaznam a vrátí její instanci.
	 * @param zipEntry
	 * @return instance ZipZaznam
	 */
	static ZipZaznam getZipZaznam(ZipEntry zipEntry) {
		return zipEntry == null ? null : new ZipZaznam(zipEntry);
	}

	/**
	 * Metoda se pokusí přetypovat object na ZipEntry a vrátí instanci
	 * ZipZaznam, jinak null.
	 * @param object Objekt ZipEntry.
	 * @return Pokud je možné object přetypovat na ZipEntry vrací instanci
	 *         ZipZaznam, jinak null.
	 */
	static ZipZaznam getZipZaznam(Object object) {
		ZipEntry entry;
		try {
			entry = (ZipEntry) object;
		} catch (ClassCastException e) {
			e.printStackTrace();
			return null;
		}
		return getZipZaznam(entry);
	}

	/**
	 * Vrací správně formátované jméno záznamu.
	 * @return jméno záznamu
	 */
	String getName() {
		return zipEntry.getName().replace("\\", "/");
	}

	/**
	 * Vrátí instanci ZipEntry, která odpovídá tomuto zipZaznamu.
	 * @return odpovídající instance ZipEntry
	 */
	ZipEntry getZipEntry() {
		return this.zipEntry;
	}

	/**
	 * Určuje zda je tento záznam v trunku.
	 * @return true pokud je v trunku, jinak false
	 */
	boolean isInTrunk() {
		return getName().startsWith(Archiv.TRUNK);
	}

	/**
	 * Do základní složky (directory) vytvoří všechny neexistující složky, které
	 * jsou v cestě tohoto záznamu.
	 * @param directory
	 */
	void mkdirs(String directory) {
		int indexLomitka;
		String jmeno = getName();
		if ((indexLomitka = jmeno.lastIndexOf('/')) != -1) {
			String dir = jmeno.substring(Archiv.TRUNK.length(), indexLomitka);
			(new File(directory + dir)).mkdirs();
		}
	}

	@Override
	public String toString() {
		return zipEntry.toString();
	}

	/**
	 * Konstruktor vytvoří ZipZaznam podle daného jména záznamu.
	 * @param name Jméno záznamu.
	 */
	private ZipZaznam(String name) {
		this.zipEntry = new ZipEntry(name);
	}

	/**
	 * Konstruktor vytvoří ZipZaznam podle dané instance zipEntry.
	 * @param zipEntry
	 */
	private ZipZaznam(ZipEntry zipEntry) {
		this.zipEntry = zipEntry;
	}
}
