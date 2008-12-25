package logika;

import static org.junit.Assert.*;
import java.io.File;
import org.junit.BeforeClass;
import org.junit.Test;

public class StartTest {


	private static String samoArchiv;
	private static String zdrojDir;
	private static String cilDir;
	private static String samoArchivPlny;
	private static String[] soubory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		samoArchiv = "C:\\eclipse\\workspace\\SamoArchiv\\tests\\SamoArchiv.jar";
		samoArchivPlny = "C:\\eclipse\\workspace\\SamoArchiv\\tests\\sandbox\\SamoArchivPlny.jar";
		zdrojDir = "C:\\eclipse\\workspace\\SamoArchiv\\tests\\data";
		cilDir = "C:\\eclipse\\workspace\\SamoArchiv\\tests\\sandbox\\cilDir";
		soubory = new String[]  {
				samoArchivPlny,
				cilDir,
				cilDir+"\\0t ex tový do ku ment.txt",
				cilDir+"\\0Prázdný Texto vý dokument.txt",
				cilDir+"\\0slož ka",
				cilDir+"\\0slož ka\\1Spaghedeity.bmp",
				cilDir+"\\0slož ka\\1 Slož kaPlná",
				cilDir+"\\0slož ka\\1 Slož kaPlná\\do k ument.docx",
		};
	}

	@Test
	public void rozbal() {
		boolean ok = true;
		for (String s : soubory) {
			File f = new File(s);
			if(f==null) {
				System.out.println("Soubor: " + s + " je null!");
				ok = false;
			}
			if(!f.exists()) {
				System.out.println("Soubor: " + s + " neexistuje!");
				ok = false;
			}
		}
		assertTrue(ok);
		if(ok) System.out.println("Test passed.");
	}

}
