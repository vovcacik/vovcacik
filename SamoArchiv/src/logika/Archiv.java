package logika;

import gui.OknoZabal;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.JOptionPane;

/**
 * Tato třída obsahuje metody potřebné pro komprimaci a extrahování. Pokud
 * archiv obsahuje archivované soubory, jsou tyto soubory umístěny v např.
 * archiv.jar!trunk/
 * @author Vlastimil Ovčáčík
 */
public class Archiv {

	static final String TRUNK = "/trunk";
	private ZipFile jarZipFile;
	private List<ZipZaznam> entries;
	private File jarFile;
	private String path;

	public Archiv() {
		path = this.getRootPath();
		try {
			jarZipFile = new ZipFile(path);
			jarFile = new File(path);
		} catch (IOException e) {
			e.printStackTrace();
		}

		//vytvoříme seznam záznamu v celém JAR archivu
		entries = new ArrayList<ZipZaznam>();
		Enumeration entriesEnum = jarZipFile.entries();
		while(entriesEnum.hasMoreElements()) {
			ZipZaznam zaznam = ZipZaznam.getZipZaznam((ZipEntry)entriesEnum.nextElement());
			entries.add(zaznam);
		}
	}

	/**
	 * Metoda rozbalí obsah složky trunk tohoto archivu do zadané složky.
	 * @param pathCilDir Cílová složka, do které se rozbalí archiv.
	 */
	public void rozbal(String pathCilDir) {
		File cilDir = new File(pathCilDir);
		List<ZipZaznam> entriesInTrunk = new ArrayList<ZipZaznam>();
		for (ZipZaznam entry : entries) {
			if (entry.isInTrunk()) {
				entriesInTrunk.add(entry);
			}
		}

		Unpack.unpack(cilDir, jarZipFile, entriesInTrunk);
	}

	/**
	 * Metoda zabal komprimuje obsah zdrojového adresáře do nového archivu jar,
	 * jehož název (celá cesta) je dána parametrem pathNovaJar
	 * @param pathNovyJar Archiv jar, který bude vytvořen
	 * @param pathZdrojAdresar Adresář jehož obsah je určen k archivaci.
	 */
	public void zabal(String pathNovyJar, String pathZdrojAdresar) {
		pathNovyJar = getFormattedPath(pathNovyJar, true);
		pathZdrojAdresar = getFormattedPath(pathZdrojAdresar, true);
		
		File novyJar = new File(pathNovyJar);
		File zdrojAdresar = new File(pathZdrojAdresar);
		File files[] = getSeznamPodsouboru(zdrojAdresar.listFiles());
		
		// kontroluje zde nechceme zapisovat do právě otevřeného archivu
		if (novyJar.equals(jarFile)) {
			OknoZabal oknoZabal = new OknoZabal(this);
			oknoZabal.setVisible(true);
			JOptionPane.showMessageDialog(null, "Zvolte jiný než aktuální archiv!", "Chyba", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		try {
			novyJar.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Pack packer = new Pack(novyJar, zdrojAdresar, files);
		packer.zabal(jarFile);
	}
	
	/**
	 * Tato metoda zjišťuje zda aktuální archiv obsahuje nějaké archivované
	 * soubory nebo ne. Vztahuje se jen ke složce trunk.
	 * @return false pokud složka trunk obsahuje soubory, jinak true
	 */
	public boolean isPrazdny() {
		for (ZipZaznam entry : entries) {
			if (entry.isInTrunk()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Vrací řetězec s cestou k tomuto archivu.
	 * @return cesta k archivu
	 */
	public String getPath() {
		return this.path;
	}
	
	/**
	 * Metoda vrací absolutní cestu k právě spuštěnému jar archivu ve tvaru
	 * např. 'C:/složka/podsložka/archiv.jar'
	 * @return Path aktuálně spuštěného archivu jar.
	 */
	private String getRootPath() {
		URL url = this.getClass().getResource("Archiv.class");
		String path = url.getPath();
		path = path.substring(path.indexOf("file:/") + "file:/".length(), path.lastIndexOf('!'));
		return path.replaceAll("%20", " ");
	}

	/**
	 * Formátuje cestu.
	 * @param path cesta k formátování
	 * @param isDir určuje zda cesta reprezentuje složku nebo ne
	 * @return zformátovaná cesta
	 */
	private String getFormattedPath(String path, boolean isDir) {
		path = path.replace('\\', '/');
		if (isDir && !path.endsWith("/")) {
			path = path + "/";
		}
		return path;
	}

	/**
	 * Metoda přijímá pole souborů/složek, které projde a vrací kompletní pole
	 * jejich souborů a podsložek.
	 * @param list Pole souborů a složek, ze kterých chceme podsoubory
	 * @return Pole souborů a podsložek
	 */
	private static File[] getSeznamPodsouboru(File[] list) {
		List<File> seznam = new ArrayList<File>();
		for (File f : list) {
			seznam.add(f);
		}
		for (int i = 0; i < list.length; i++) {
			if (list[i].isDirectory()) {
				for (File f : getSeznamPodsouboru(list[i].listFiles())) {
					seznam.add(f);
				}
			}
		}
		File[] ret = new File[seznam.size()];
		seznam.toArray(ret);
		return ret;
	}
}
