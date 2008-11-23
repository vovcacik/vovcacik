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
 * Tato třída zobrazuje grafiku při archivaci.
 * @author Vlastimil Ovčáčík
 *
 */
public class OknoZabal {

	private JFrame okno;
	//J komponenty patřící k nově vytvářenému archivu jar
	private JPanel panelJar;
	private JLabel labelJar;
	private JTextField pathJar;
	private JButton buttonJar;

	//J komponenty patřící ke zdrojovému adresáři
	private JPanel panelAdresar;
	private JLabel labelAdresar;
	private JTextField pathAdresar;
	private JButton buttonAdresar;

	//Tlačítko "Zabal"
	private JButton buttonHotovo;
	private JPanel panelHotovo;

	/**
	 * Otevírá JFileChooser pro výběr umístění nového archivu jar.
	 * @author Vlastimil Ovčáčík
	 *
	 */
	private class ButtonJarListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fc.setCurrentDirectory(new File(new Archiv().getRootPath()));
			int returnVal = fc.showSaveDialog(okno);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				pathJar.setText(file.getPath());
			}
		}
	}

	/**
	 * Otevírá JFileChooser pro výběr zdrojové složky (složky s archivovanými
	 * soubory)
	 * @author Vlastimil Ovčáčík
	 *
	 */
	private class ButtonAdresarListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser();
			fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fc.setCurrentDirectory(new File(new Archiv().getRootPath()));
			int returnVal = fc.showSaveDialog(okno);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				pathAdresar.setText(file.getPath());
			}
		}
	}

	/**
	 * Spouští se při stisku tlačítka "Zabal". Spouští samotnou archivaci.
	 * @author Vlastimil Ovčáčík
	 *
	 */
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
	 * Konstruktor samotného okna.
	 */
	public OknoZabal() {
		okno = new JFrame();
		okno.setTitle("Komprimovat...");
		okno.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		labelJar = new JLabel("Vyberte cestu a název nového archivu:");
		pathJar = new JTextField(30);
		buttonJar = new JButton("Procházet");
		buttonJar.addActionListener(new ButtonJarListener());

		labelAdresar = new JLabel("Vyberte složku pro archivaci:");
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
			pathJar.setText(new Archiv().getRootPath());
		}

	}

	public void setVisible(boolean visible) {
		okno.setVisible(visible);
	}
}