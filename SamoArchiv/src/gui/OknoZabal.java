package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private JPanel panelJar;
	private JPanel panelAdresar;
	private JLabel labelJar;
	private JTextField pathJar;
	private JButton buttonJar;
	private JLabel labelAdresar;
	private JTextField pathAdresar;
	private JButton buttonAdresar;
	private JButton buttonHotovo;
	private JPanel panelHotovo;

	private class ButtonJarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.setCurrentDirectory(new File(new Archiv().getThisPath()));
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
			String pJar = pathJar.getText();
			String pAdresar = pathAdresar.getText();
			if (!(pAdresar.endsWith("/") || pAdresar.endsWith("\\"))) {
				pAdresar += "/";
			}
			archiv.zabal(pJar, pAdresar);
			okno.dispose();
		}

	}

	/**
	 * Konstruktor vytvoří logiku hry a gui.
	 */
	public OknoZabal() {
		okno = new JFrame();
		okno.setTitle("Komprimovat...");
		okno.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		labelJar = new JLabel("Vyberte cestu a název nového archivu:");
		pathJar = new JTextField(30);
		buttonJar = new JButton("Procházet");
		buttonJar.addActionListener(new ButtonJarListener());

		labelAdresar = new JLabel("Vyberte složku pro extrakci:");
		pathAdresar = new JTextField(30);
		buttonAdresar = new JButton("Procházet");
		buttonAdresar.addActionListener(new ButtonAdresarListener());
		buttonHotovo = new JButton("Vytvoř...");
		buttonHotovo.addActionListener(new ButtonHotovoListener());

		panelJar = new JPanel();
		panelJar.setLayout(new FlowLayout());
		panelJar.add(labelJar);
		panelJar.add(pathJar);
		panelJar.add(buttonJar);

		panelAdresar = new JPanel();
		panelAdresar.setLayout(new FlowLayout());
		panelAdresar.add(labelAdresar);
		panelAdresar.add(pathAdresar);
		panelAdresar.add(buttonAdresar);

		panelHotovo = new JPanel();
		FlowLayout mng = new FlowLayout();
		mng.setAlignment(FlowLayout.RIGHT);
		panelHotovo.setLayout(mng);
		panelHotovo.add(buttonHotovo);

		okno.add(panelJar, BorderLayout.NORTH);
		okno.add(panelAdresar, BorderLayout.CENTER);
		okno.add(panelHotovo, BorderLayout.SOUTH);
		okno.setMinimumSize(new Dimension(100, 50));
		okno.pack();
		okno.setLocationRelativeTo(null);

		if (new Archiv().isPrazdny()) {
			pathJar.setText(new Archiv().getThisPath());
		}

	}

	/**
	 * Zviditelnuje dve hlavni okno - Hlavni okno a okno s Mapou
	 * @param visible hodnota true zviditelni okna
	 */
	public void setVisible(boolean visible) {
		okno.setVisible(visible);
	}
}