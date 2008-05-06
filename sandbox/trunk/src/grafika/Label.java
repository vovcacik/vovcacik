package grafika;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class Label {

	private JDialog dialog;
	private JLabel label;
	
	public Label() {
		init();
	}
	
	private void init() {
		dialog = new JDialog();
		dialog.setTitle("label");
		
		label = new JLabel();
		label.setText("ukázka textu\n\n");
		label.setForeground(new Color(0,255,0));
		
		dialog.add(label);
		dialog.pack();
	}
	
	public void setVisible(boolean viditelnost) {
		dialog.setVisible(viditelnost);
	}
}
