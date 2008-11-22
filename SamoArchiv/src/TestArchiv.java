import junit.framework.TestCase;

public class TestArchiv extends TestCase {

	Archiv archiv;
	String cestaRozbal = "C:/test/rozbal/archiv.zip";

	public TestArchiv(String nazev) {
		super(nazev);
	}

	protected void setUp() {
		archiv = new Archiv(cestaRozbal);
	}

	protected void tearDown() {
	}

	public void testRozbal() {

	}
}
