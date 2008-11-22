public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Archiv archivRozbal;
		archivRozbal = new Archiv();
		if (archivRozbal.isPrazdny()) {
			archivRozbal.zabal("C:/test/zabal/novyjar.jar", "C:/test/zabal/kufr/");
		} else {
			archivRozbal.rozbal("C:/test/zabal/novyKufr/");
		}
	}
}
