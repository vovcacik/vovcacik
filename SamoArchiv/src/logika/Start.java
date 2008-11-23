package logika;

import gui.HlavniOkno;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HlavniOkno hlOkno = new HlavniOkno();
		hlOkno.setVisible(true);

		Archiv archiv;
		archiv = new Archiv();
		for (String s : args) {
			System.out.println(s);
		}
		if (args[0].equals("-z")) {
			archiv.zabal(args[1], args[2]);
		} else if (args[0].equals("-r")) {
			archiv.rozbal(args[1], args[2]);
		} else {
			System.err.println("zadny argument...");
		}
	}
}
