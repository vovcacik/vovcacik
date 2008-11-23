package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import logika.Archiv;

/**
 * Třída HlavníOkno je hlavním oknem GUI adventury. Inicializuje logiku a
 * všechny grafické prvky. Také odesílá příkazy do logiky.
 * @author Vlasta
 */
public class OknoZabal {

	private JFrame okno;
	private JPanel panel;
	private JTextField pathJar;
	private JButton buttonJar;
	private JTextField pathAdresar;
	private JButton buttonAdresar;
	private JButton buttonHotovo;

	private class ButtonJarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int returnVal = fc.showSaveDialog(okno);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				pathJar.setText(file.getPath());
			}

		}
	}

	/**
	 * Tato třída poskytuje odeslání příkazu z vstupního řádku
	 * @author Vlasta
	 */
	private class ButtonAdresarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int returnVal = fc.showSaveDialog(okno);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				pathAdresar.setText(file.getPath());
			}
		}
	}

	private class ButtonHotovoListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Archiv archiv = new Archiv();
			String pJar = pathJar.getText();
			String pAdresar = pathAdresar.getText();
			if (!(pAdresar.endsWith("/") || pAdresar.endsWith("\\"))) {
				pAdresar += "/";
			}
			archiv.zabal(pJar, pAdresar);

		}

	}

	/**
	 * Konstruktor vytvoří logiku hry a gui.
	 */
	public OknoZabal() {
		okno = new JFrame();
		okno.setTitle("Okno zabal");
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pathJar = new JTextField(30);
		buttonJar = new JButton("Vyber jar");
		buttonJar.addActionListener(new ButtonJarListener());
		pathAdresar = new JTextField(30);
		buttonAdresar = new JButton("Vyber adresář");
		buttonAdresar.addActionListener(new ButtonAdresarListener());
		buttonHotovo = new JButton("Vytvoř...");
		buttonHotovo.addActionListener(new ButtonHotovoListener());

		panel = new JPanel();
		panel.setLayout(new GridLayout(3, 2));
		panel.add(pathJar);
		panel.add(buttonJar);
		panel.add(pathAdresar);
		panel.add(buttonAdresar);
		panel.add(buttonHotovo);

		okno.add(panel, BorderLayout.CENTER);
		okno.setMinimumSize(new Dimension(340, 250));
		okno.pack();
		okno.setLocationRelativeTo(null);

	}

	/**
	 * Zviditelnuje dve hlavni okno - Hlavni okno a okno s Mapou
	 * @param visible hodnota true zviditelni okna
	 */
	public void setVisible(boolean visible) {
		okno.setVisible(visible);
	}

	/**
	 * Odesila prikaz do logiky hry, zaroven tiskne prikaz do gui a tiskne i
	 * odpoved logiky na prikaz. Vraci epilog.
	 * @param prikaz prikaz ke zpracovani
	 */
	public void odesliPrikaz(String prikaz) {
		return;
	}
}