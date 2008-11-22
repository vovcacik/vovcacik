import junit.framework.TestCase;

public class TestArchiv extends TestCase {

	Archiv archivRozbal;
	String cestaRozbal = "C:/test/rozbal/archiv.zip";
	Archiv archivZabal;
	String cestaZabal = "C:/test/zabal/test.zip";

	public TestArchiv(String nazev) {
		super(nazev);
	}

	protected void setUp() {
		archivRozbal = new Archiv(cestaRozbal);
		archivZabal = new Archiv(cestaZabal);
	}

	protected void tearDown() {
	}

	public void testRozbal() {
		archivRozbal.rozbal("C:/test/rozbal/");
	}

	public void testZabal() {
		archivZabal.zabal("C:/test/zabal/");
	}
}
