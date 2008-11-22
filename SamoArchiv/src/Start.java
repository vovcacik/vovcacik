public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Archiv archivRozbal;
		String cestaRozbal = "C:/test/rozbal/archiv.jar";
		archivRozbal = new Archiv(cestaRozbal);
		archivRozbal.isPrazdny();

	}

}
