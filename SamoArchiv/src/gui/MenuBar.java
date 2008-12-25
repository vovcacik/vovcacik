package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import logika.Archiv;

/**
 * Menubar v hlavním okně.
 * @author Vlastimil Ovčáčík
 *
 */
@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {

	private JMenu menu;
	private JMenuItem menuItem;
	private Archiv archiv;

	/**
	 * Ovladač událostí menu.
	 * @author Vlastimil Ovčáčík
	 *
	 */
	private class OvladacUdalostiMenu implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Nový archiv")) {
				OknoZabal oknoZabal = new OknoZabal(archiv);
				oknoZabal.setVisible(true);
			} else if (e.getActionCommand().equals("Rozbal archiv")) {
				if (archiv.isPrazdny()) {
					JOptionPane.showMessageDialog(null, "Archiv je prázdný, nelze rozbalit...", "Chyba", JOptionPane.ERROR_MESSAGE);
				} else {
					OknoRozbal oknoRozbal = new OknoRozbal(archiv);
					oknoRozbal.setVisible(true);
				}
			} else if (e.getActionCommand().equals("Konec")) {
				System.exit(0);
			} else {
				System.out.println("Neznamá volba menu!");
			}
		}
	}

	/**
	 * Konstruktor menu
	 * @param archiv 
	 */
	public MenuBar(Archiv archiv) {
		super();
		this.archiv = archiv;
		OvladacUdalostiMenu ovladac = new OvladacUdalostiMenu();
		// Základní menu
		menu = new JMenu("Soubor");
		menu.setMnemonic(KeyEvent.VK_S);
		this.add(menu);

		// skupina JMenuItems
		menuItem = new JMenuItem("Nový archiv", KeyEvent.VK_N);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(ovladac);
		menu.add(menuItem);

		menuItem = new JMenuItem("Rozbal archiv", KeyEvent.VK_R);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(ovladac);
		menu.add(menuItem);

		menuItem = new JMenuItem("Konec", KeyEvent.VK_K);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
		menuItem.addActionListener(ovladac);
		menu.add(menuItem);
	}
}
