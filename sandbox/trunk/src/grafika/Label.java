package grafika;

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
		label.setText("ukázka textu");
		
		dialog.add(label);
	}
	
	public void setVisible(boolean viditelnost) {
		dialog.setVisible(viditelnost);
	}
}
