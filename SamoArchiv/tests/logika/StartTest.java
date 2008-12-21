package logika;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class StartTest {


	private static String samoArchiv;
	private static String zdrojDir;
	private static String cilDir;
	private static String samoArchivPlny;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		samoArchiv = "C:\\eclipse\\workspace\\SamoArchiv\\tests\\SamoArchiv.jar";
		samoArchivPlny = "C:\\eclipse\\workspace\\SamoArchiv\\tests\\SamoArchivPlny.jar";
		zdrojDir = "C:\\eclipse\\workspace\\SamoArchiv\\tests\\data\\";
		cilDir = "C:\\eclipse\\workspace\\SamoArchiv\\tests\\sandbox\\";
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void pridej() {
//		java -jar "C:\Eclipse\workspace\samoarchiv\tests\samoarchiv.jar" -z "C:\eclipse\workspace\samoarchiv\tests\b.jar" c            "C:\eclipse\workspace\samoarchiv\tests\data"
//		java -jar "C:\eclipse\workspace\SamoArchiv\tests\SamoArchiv.jar" -z "C:\eclipse\workspace\SamoArchiv\tests\SamoArchivPlny.jar" "C:\eclipse\workspace\SamoArchiv\tests\data\"
		
		
		//zabalení
		try {
			String command = "java -jar \""+samoArchiv+"\" -z \""+samoArchivPlny+"\" \""+zdrojDir+"\"";
			Process proces = Runtime.getRuntime().exec(command);
			Thread.sleep(5000);
			assertEquals(0, proces.exitValue());
		} catch (IOException e) {
			fail("I/O chyba");
			e.printStackTrace();
		} catch (InterruptedException e) {
			fail("čekání na vytvoření nového archivu přerušeno");
			e.printStackTrace();
		}
	}
}
