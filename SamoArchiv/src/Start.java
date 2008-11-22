public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Archiv archivRozbal;
		String cestaRozbal = "C:/test/rozbal/archiv.jar";
		archivRozbal = new Archiv(cestaRozbal);
		archivRozbal.isPrazdny();
		archivRozbal.pribal("C:/test/zabal/novyjar.jar", "C:/test/zabal/kufr2/");
	}

}
