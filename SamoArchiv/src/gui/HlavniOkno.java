package gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import logika.Archiv;

/**
 * Hlavní okno aplikace. Obsahuje textové pole a JMenuBar.
 * @author Vlastimil Ovčáčík
 */
public class HlavniOkno {
	private JFrame okno;
	private MenuBar menu;
	private JTextArea vystup;
	private JScrollPane scroll;
	private String oddelovac = "\n-------------------------------------------------------------";
	private Archiv archiv;

	/**
	 * Hlavní okno programu. 
	 * @param archiv 	Archiv, se kterým bude grafika pracovat.
	 */
	public HlavniOkno(Archiv archiv) {
		this.archiv = archiv;
		okno = new JFrame();
		okno.setTitle("Samorozbalovací archiv");
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menu = new MenuBar(archiv);
		okno.setJMenuBar(menu);

		vystup = new JTextArea(2, 20);
		
		if (archiv.isPrazdny()) {
			vystup.setText("Archiv " + archiv.getPath() + " je prázdný." + oddelovac);
		} else {
			vystup.setText("Aktuální archiv: " + archiv.getPath() + " obsahuje archivované soubory." + oddelovac);
			int vyber = JOptionPane.showConfirmDialog(null, "Archiv obsahuje soubory. Přejete si je nyní rozbalit?", "Rozbalit archiv?", JOptionPane.YES_NO_OPTION);
			if (vyber == JOptionPane.YES_OPTION) {
				OknoRozbal oknoRozbal = new OknoRozbal(archiv);
				oknoRozbal.setVisible(true);
			}
		}
		vystup.setEditable(false);

		scroll = new JScrollPane(vystup);
		okno.add(scroll);
		vystup.setCaretPosition(vystup.getDocument().getLength());

		okno.setPreferredSize(new Dimension(400, 250));
		okno.pack();
		okno.setLocationRelativeTo(null);

	}

	/**
	 * Zobrazí hlavní okno.
	 * @param b
	 */
	public void setVisible(boolean b) {
		okno.setVisible(b);
	}
}
