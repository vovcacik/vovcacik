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
public class OknoRozbal {

	private JFrame okno;
	private JPanel panel;
	private JTextField pathAdresar;
	private JButton buttonAdresar;
	private JButton buttonHotovo;

	private class ButtonAdresarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setCurrentDirectory(new File(new Archiv().getThisPath()));
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
			String pAdresar = pathAdresar.getText();
			if (!(pAdresar.endsWith("/") || pAdresar.endsWith("\\"))) {
				pAdresar += "/";
			}
			archiv.rozbal(archiv.getThisPath(), pAdresar);

		}

	}

	/**
	 * Konstruktor vytvoří logiku hry a gui.
	 */
	public OknoRozbal() {
		okno = new JFrame();
		okno.setTitle("Okno rozbal");
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		pathAdresar = new JTextField(30);
		buttonAdresar = new JButton("Vyber adresář");
		buttonAdresar.addActionListener(new ButtonAdresarListener());
		buttonHotovo = new JButton("Rozbal...");
		buttonHotovo.addActionListener(new ButtonHotovoListener());

		panel = new JPanel();
		panel.setLayout(new GridLayout(2, 2));
		panel.add(pathAdresar);
		panel.add(buttonAdresar);
		panel.add(buttonHotovo);

		okno.add(panel, BorderLayout.CENTER);
		okno.setMinimumSize(new Dimension(340, 250));
		okno.pack();
		okno.setLocationRelativeTo(null);

	}

	public void setVisible(boolean visible) {
		okno.setVisible(visible);
	}
}