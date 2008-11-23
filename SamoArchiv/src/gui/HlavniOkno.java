package gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import logika.Archiv;

public class HlavniOkno {
	private JFrame okno;
	private MenuBar menu;
	private JTextArea vystup;
	private JScrollPane scroll;
	private String oddelovac = "\n-------------------------------------------------------------";

	public HlavniOkno() {
		okno = new JFrame();
		okno.setTitle("Adventura");
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menu = new MenuBar();
		okno.setJMenuBar(menu);

		vystup = new JTextArea(2, 20);
		Archiv archiv = new Archiv();
		if (archiv.isPrazdny()) {
			vystup.setText("Archiv je prázdný." + oddelovac);
		} else {
			vystup.setText("Aktuální archiv: " + archiv.getThisPath() + oddelovac);
		}
		vystup.setEditable(false);

		scroll = new JScrollPane(vystup);
		okno.add(scroll);
		vystup.setCaretPosition(vystup.getDocument().getLength());

		okno.setPreferredSize(new Dimension(280, 150));
		okno.pack();
		okno.setLocationRelativeTo(null);

	}

	public void setVisible(boolean b) {
		okno.setVisible(b);
	}
}
