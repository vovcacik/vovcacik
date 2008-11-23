public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Archiv archiv;
		archiv = new Archiv();
		if (archiv.isPrazdny()) {
			archiv.zabal("C:/test/zabal/novyjar.jar", "C:/test/zabal/kufr/");
		} else {
			archiv.rozbal("C:/test/zabal/novyjar.jar", "C:/test/zabal/novyKufr/");
		}
	}
}
