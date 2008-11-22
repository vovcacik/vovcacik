import junit.framework.TestCase;

public class TestArchiv extends TestCase {

	Archiv archivRozbal;
	String cestaRozbal = "C:/test/rozbal/archiv.jar";
	Archiv archivZabal;
	String cestaZabal = "C:/test/zabal/build.jar";

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
		// archivRozbal.rozbal("C:/test/rozbal/jar/");
	}

	public void testZabal() {
		// archivZabal.zabal("C:/test/zabal/kufr2/");
	}

	public void testIsPrazdny() {
		// archivRozbal.isPrazdny();
	}

	public void testPribal() {
		archivZabal.pribal("C:/test/zabal/novyjar.jar", "C:/test/zabal/kufr2/");
	}
}
