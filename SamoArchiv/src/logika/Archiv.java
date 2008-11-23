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

/**
 * Tato třída obsahuje metody potřebné pro komprimaci a extrahování. Pokud
 * archiv obsahuje archivované soubory, jsou tyto soubory umístěny v např.
 * archiv.jar!trunk/
 * @author Vlastimil Ovčáčík
 */
public class Archiv {

	public Archiv() {
	}

	/**
	 * Metoda rozbalí obsah složky trunk tohoto archivu do zadané složky.
	 * @param pathCilDir Cílová složka, do které se rozbalí archiv.
	 */
	public void rozbal(String pathCilDir) {
		String pathZdrojJar = this.getRootPath();
		pathCilDir = pathCilDir.replace('\\', '/');
		if (!pathCilDir.endsWith("/")) {
			pathCilDir = pathCilDir + "/";
		}
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
				// pokud entry není v trunku tak přeskoč, protože nechceme
				// rozbalit i zdrojové soubory
				if (!entry.getName().startsWith("/trunk")) {
					continue;
				}

				// pokud je rozbalované entry v podsložce, vytvoř potřebné
				// adresáře v cílovém adresáři
				int indexLomitka;
				String jmeno = entry.getName().replace('\\', '/');
				if ((indexLomitka = jmeno.lastIndexOf('/')) != -1) {
					String dir = entry.getName().substring("/trunk".length(), indexLomitka);
					System.out.println("Extracting directory: " + pathCilDir + dir);
					(new File(pathCilDir + dir)).mkdirs();
				}

				System.out.println("Extracting: " + entry);
				is = new BufferedInputStream(zdrojJar.getInputStream(entry));
				int count;
				byte data[] = new byte[BUFFER];
				// výstupní proud, musíme odstranit řetězec '/trunk', aby nebyl
				// vytvořen takový adresář v cílové složce
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

	/**
	 * Metoda zabal komprimuje obsah zdrojového adresáře do nového archivu jar,
	 * jehož název (celá cesta) je dána parametrem pathNovaJar
	 * @param pathNovyJar Archiv jar, který bude vytvořen
	 * @param pathZdrojAdresar Adresář jehož obsah je určen k archivaci.
	 */
	public void zabal(String pathNovyJar, String pathZdrojAdresar) {
		pathNovyJar = pathNovyJar.replace('\\', '/');
		pathZdrojAdresar = pathZdrojAdresar.replace('\\', '/');
		if (!pathZdrojAdresar.endsWith("/")) {
			pathZdrojAdresar = pathZdrojAdresar + "/";
		}

		File zdrojovyJar = new File(this.getRootPath());
		File zdrojAdresar = new File(pathZdrojAdresar);
		File files[] = getSeznamPodsouboru(zdrojAdresar.listFiles());
		for (File f : files) {
			System.out.println(f.getPath());
		}
		/*
		 * Následuje část, která má za úkol připravit cílový jar. Do něj se musí
		 * zkopírovat class soubory aplikace, nikoliv však soubory uvnitř
		 * trunku.
		 */
		File novyJar; // cílový jar soubor
		try {
			novyJar = new File(pathNovyJar);
			// kontroluje zde nechceme zapisovat do právě otevřeného archivu
			if (novyJar.equals(zdrojovyJar)) {
				OknoZabal oknoZabal = new OknoZabal();
				oknoZabal.setVisible(true);
				JOptionPane.showMessageDialog(null, "Zvolte jiný než aktuální archiv!", "Chyba", JOptionPane.ERROR_MESSAGE);
				return;
			}
			novyJar.createNewFile();

			byte[] buf = new byte[1024];

			ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(zdrojovyJar)));
			ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(novyJar)));

			ZipEntry entry = zin.getNextEntry();
			while (entry != null) {
				String name = entry.getName();
				name = name.replace("\\", "/");
				boolean neduplikovany = true;
				System.out.println("\n\n-----------------------------\nEntry name: " + name);
				for (File f : files) {
					String fName = f.getPath();
					fName = fName.replace("\\", "/");
					// převedeme z absolutní cesty na relativní cestu (relativní
					// k pathZdrojAdresar)
					fName = fName.substring(fName.indexOf(pathZdrojAdresar) + pathZdrojAdresar.length());
					// pro správné porovnání cesty souboru a entry musíme přidat
					// '/trunk/' cestě souboru
					fName = "/trunk/" + fName;
					System.out.println("in folder: " + fName);
					if (fName.equals(name)) {
						System.out.println("NEZARAZENO: " + fName);
						neduplikovany = false;
						break;
					}
				}

				// entry ze zdrojového jaru nebude zkopírován do nového jaru
				// pokud ve zdrojové složce existuje s tímto souborem konflikt
				if (neduplikovany) {
					out.putNextEntry(new ZipEntry(name));
					int len;
					while ((len = zin.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
				}
				entry = zin.getNextEntry();
			}
			zin.close();

			/*
			 * Následující kód zapíše obsah zdrojové složky do cílového jar
			 * souboru, ve kterém už je z předcházející části zkopírován tento
			 * program. Nyní budeme zapisovat pouze do složky trunk.
			 */

			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					continue; // složky nezapisuji, protože se jinak tvoří
					// duplikáty
				}
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(files[i].getPath()));
				// převedeme abs. path na relativní (k pathZdrojAdresar)
				String entryPath = files[i].getPath().substring(files[i].getPath().indexOf(pathZdrojAdresar) + pathZdrojAdresar.length());
				System.out.println("entrypath: " + entryPath);
				// zápis do cílového jar do složky trunk
				out.putNextEntry(new ZipEntry("/trunk" + entryPath));
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				in.close();
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metoda přijímá pole souborů/složek, které projde a vrací kompletní pole
	 * jejich podsouborů nebo podsložek.
	 * @param list Pole souborů a složek, ze kterých chceme podsoubory
	 * @return Pole podsouborů a podsložek
	 */
	private static File[] getSeznamPodsouboru(File[] list) {
		List<File> seznam = new ArrayList<File>();
		for (File f : list) {
			seznam.add(f);
		}
		for (int i = 0; i < list.length; i++) {
			if (list[i].isDirectory()) {
				for (File f2 : getSeznamPodsouboru(list[i].listFiles())) {
					seznam.add(f2);
				}
			}
		}
		File[] ret = new File[seznam.size()];
		seznam.toArray(ret);
		return ret;
	}

	/**
	 * Tato metoda zjišťuje zda aktuální archiv obsahuje nějaké archivované
	 * soubory nebo ne. Vztahuje se jen ke složce trunk.
	 * @return false pokud složka trunk obsahuje soubory, jinak true
	 */
	public boolean isPrazdny() {
		String path = getRootPath();
		System.out.println(path);

		ZipFile zip;
		try {
			zip = new ZipFile(path);
			Enumeration e = zip.entries();
			while (e.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) e.nextElement();
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

	/**
	 * Metoda vrací absolutní cestu k právě spuštěnému jar archivu ve tvaru
	 * např. 'C:/slozka/podslozka/archiv.jar'
	 * @return Path aktuálně spuštěného archivu jar.
	 */
	public String getRootPath() {
		URL url = this.getClass().getResource("Archiv.class");
		String path = url.getPath();
		path = path.substring(path.indexOf("file:/") + "file:/".length(), path.lastIndexOf('!'));
		return path.replaceAll("%20", " ");
	}
}
