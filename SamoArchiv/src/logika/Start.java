package logika;

import gui.HlavniOkno;
import gui.OknoRozbal;
import javax.swing.JOptionPane;

public class Start {

	/**
	 * Archiv má dva možné parametry. 
	 * -r 'slozka'         Rozbalí tento archiv do cílové složky
	 * -z 'jar' 'slozka'   Zabalí složku do jar archivu
	 * Bez parametrů bude spuštěn gui.
	 * @param args
	 */
	public static void main(String[] args) {
		Archiv archiv;
		archiv = new Archiv();

		if (args.length != 0) {
			if (args[0].equals("-z") && args.length == 3) {
				archiv.zabal(args[1], args[2]);
			} else if (args[0].equals("-r") && args.length == 2) {
				archiv.rozbal(args[1]);
			} else {
				System.err.println("Parametry samorozbalovaciho archivu:\n" + "-r 'slozka'\t\tRozbali tento archiv do cilove slozky\n" + "-z 'jar' 'slozka'\tZabali slozku do jar archivu\n\n" + "Priklady:\n" + "java -jar archiv.jar -r 'C:\\slozka\\'\n" + "java -jar archiv.jar -z 'C:\\novyJar.jar' 'C:\\kArchivaci\\'");
				System.exit(0);
			}
		}
		HlavniOkno hlOkno = new HlavniOkno();
		hlOkno.setVisible(true);

		if (!archiv.isPrazdny()) {
			int vyber = JOptionPane.showConfirmDialog(null, "Archiv obsahuje soubory. Přejete si je nyní rozbalit?", "Rozbalit archiv?", JOptionPane.YES_NO_OPTION);
			if (vyber == JOptionPane.YES_OPTION) {
				OknoRozbal oknoRozbal = new OknoRozbal();
				oknoRozbal.setVisible(true);
			}
		}
	}
}
