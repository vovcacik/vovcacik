package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class HlavniOkno {
	private JFrame okno;
	private MenuBar menu;
	private JPanel jizniPanel;
	private JPanel vychodniPanel;
	private JTextField vstup;
	private JTextArea vystup;
	private JScrollPane scroll;

	public HlavniOkno() {
		okno = new JFrame();
		okno.setTitle("Adventura");
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menu = new MenuBar();
		okno.setJMenuBar(menu);

		vystup = new JTextArea(20, 40);
		vystup.setText("neco");
		vystup.setEditable(false);

		scroll = new JScrollPane(vystup);
		okno.add(scroll);
		vystup.setCaretPosition(vystup.getDocument().getLength());

		vstup = new JTextField(30);
		vstup.addActionListener(null);

		jizniPanel = new JPanel();
		jizniPanel.add(new JLabel("Zadej prikaz"));
		jizniPanel.add(vstup);

		okno.add(jizniPanel, BorderLayout.SOUTH);
		okno.setMinimumSize(new Dimension(680, 500));
		okno.pack();
		okno.setLocationRelativeTo(null);

	}

	public void setVisible(boolean b) {
		okno.setVisible(b);
	}
}
