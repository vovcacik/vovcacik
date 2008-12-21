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

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		samoArchiv = "C:\\eclipse\\workspace\\SamoArchiv\\tests\\SamoArchiv.jar";
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
		
		//zabalení
		try {
			String command = "java -jar \""+samoArchiv+"\" -z \""+samoArchiv+"\" \""+zdrojDir+"\"";
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
