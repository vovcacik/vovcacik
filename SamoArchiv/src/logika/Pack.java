package logika;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Tato třída poskytuje metody pro technickou stránku zabalení nového JAR archivu.
 * @author Vlastimil Ovčáčík
 */
class Pack {

	private File zdrojAdresar;
	private File[] files;
	private ZipOutputStream out;

	/**
	 * Konstruktor přejímá soubor File novyJar, který reprezentuje existující soubor jar, do kterého
	 * se zabalí zadaný adresář (zdrojAdresar). Z pole files se zapíší všechny soubory do nového jar
	 * souboru. 
	 * Pole files jsou podsoubory zdrojAdresar.
	 * @param novyJar		Soubor jar, do kterého se zapíší soubory ze zdrojAdresar
	 * @param zdrojAdresar	Zdrojový adresář, ze kterého pochází soubory určené k zabalení.
	 * @param files 		Konkrétní soubory, které budou zapsány do nového archivu.
	 */
	Pack(File novyJar, File zdrojAdresar, File[] files) {
		this.zdrojAdresar = zdrojAdresar;
		this.files = files;
		try {
			this.out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(novyJar)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Vytvoří nový jar soubor a zabalí do něj soubory ze zdrojové složky. Parametrem se udává, 
	 * který jar archiv je zdrojový.
	 * @param jarFile	Zdrojový jar archiv. (pozn.: většinou tento jar archiv)
	 */
	void zabal(File jarFile) {
		makeNewJar(jarFile);
		zabalAdresar();
	}

	/**
	 * Tato metoda připraví cílový jar. Do něj se musí
	 * zkopírovat class soubory aplikace, nikoliv však soubory uvnitř
	 * trunku.
	 * @param jarFile 
	 */
	private void makeNewJar(File jarFile) {
		try {
			byte[] buf = new byte[1024];
			ZipInputStream zin = new ZipInputStream(new BufferedInputStream(new FileInputStream(jarFile)));

			ZipZaznam entry = ZipZaznam.getZipZaznam(zin.getNextEntry());
			while (entry != null) {
				String name = entry.getName();
				if (name.startsWith(Archiv.TRUNK)) {
					entry = ZipZaznam.getZipZaznam(zin.getNextEntry());
					continue;
				}
				out.putNextEntry(ZipZaznam.getZipZaznam(name).getZipEntry());
				int len;
				while ((len = zin.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				entry = ZipZaznam.getZipZaznam(zin.getNextEntry());
			}
			zin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Zapíše obsah zdrojové složky do cílového jar
	 * souboru. Nyní budeme zapisovat pouze do složky trunk.
	 */
	private void zabalAdresar() {
		try {
			byte[] buf = new byte[1024];
			
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) continue; // složky nezapisuji, protože se jinak tvoří duplikáty
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(files[i].getPath()));
				
				// převedeme abs. path na relativní (k pathZdrojAdresar)
				String entryPath = files[i].getPath().substring(files[i].getPath().indexOf(zdrojAdresar.getAbsolutePath()) + zdrojAdresar.getAbsolutePath().length());
				
				// zápis do cílového jar do složky trunk
				out.putNextEntry(ZipZaznam.getZipZaznam(Archiv.TRUNK + entryPath).getZipEntry());
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
}