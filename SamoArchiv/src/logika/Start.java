package logika;

import gui.HlavniOkno;
import gui.OknoRozbal;
import javax.swing.JOptionPane;

public class Start {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HlavniOkno hlOkno = new HlavniOkno();
		hlOkno.setVisible(true);

		Archiv archiv;
		archiv = new Archiv();

		if (!archiv.isPrazdny()) {
			int vyber = JOptionPane.showConfirmDialog(null, "Archiv obsahuje soubory. Přejete si je nyní rozbalit?", "Rozbalit archiv?", JOptionPane.YES_NO_OPTION);
			if (vyber == JOptionPane.YES_OPTION) {
				OknoRozbal oknoRozbal = new OknoRozbal();
				oknoRozbal.setVisible(true);
			}
		}

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
