package logika;

import junit.framework.TestCase;

public class TestArchiv extends TestCase {

	Archiv archivRozbal;
	Archiv archivZabal;

	public TestArchiv(String nazev) {
		super(nazev);
	}

	protected void setUp() {
		archivRozbal = new Archiv();
		archivZabal = new Archiv();
	}

	protected void tearDown() {
	}

	public void testRozbal() {
		// archivRozbal.rozbal("C:/test/rozbal/archiv.jar",
		// "C:/test/rozbal/jar/");
	}

	public void testIsPrazdny() {
		// archivRozbal.isPrazdny();
	}

	public void testZabal() {
		// archivZabal.zabal("C:/test/zabal/novyjar.jar","C:/test/zabal/kufr/");
	}
}
