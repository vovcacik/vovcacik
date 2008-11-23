package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.JDialog;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import logika.Archiv;

/**
 * Vlastni komponenta menu. Vytvari okno s napovedou a okno O programu.
 * @author Vlasta
 */
@SuppressWarnings("serial")
public class MenuBar extends JMenuBar {

	private JMenu menu;
	private JMenuItem menuItem;
	private JDialog oknoNapoveda = new JDialog();

	/**
	 * Zpracovává události pri kliknutí na položku menu.
	 * @author Vlasta
	 */
	private class OvladacUdalostiMenu implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getActionCommand().equals("Nový archiv")) {
				OknoZabal oknoZabal = new OknoZabal();
				oknoZabal.setVisible(true);
			} else if (e.getActionCommand().equals("Rozbal archiv")) {
				if (new Archiv().isPrazdny()) {
					JOptionPane.showMessageDialog(null, "Archiv je prázdný, nelzerozbalit...", "Chyba", JOptionPane.ERROR_MESSAGE);
				} else {
					OknoRozbal oknoRozbal = new OknoRozbal();
					oknoRozbal.setVisible(true);
				}
			} else if (e.getActionCommand().equals("Konec")) {
				System.exit(0);
			} else {
				System.out.println("Neznama volba menu!");
			}
		}
	}

	/**
	 * Konstruktor menu
	 * @param hlavniOkno
	 */
	public MenuBar() {
		super();
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

		// Druhé menu s napovedou
		// menu = new JMenu("Pomoc");
		// menu.setMnemonic(KeyEvent.VK_P);
		// this.add(menu);
		//		
		// menuItem = new JMenuItem("Příručka", KeyEvent.VK_P);
		// menuItem.addActionListener(ovladac);
		// menu.add(menuItem);
		// menuItem = new JMenuItem("O programu", KeyEvent.VK_O);
		// menuItem.addActionListener(ovladac);
		// menu.add(menuItem);
	}
}
