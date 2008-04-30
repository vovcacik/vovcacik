package grafika;

import javax.swing.JDialog;

public class Dialog {

	private JDialog dialog;
	
	public Dialog() {
		init();
	}
	
	private void init() {
		dialog = new JDialog();
		dialog.setTitle("Dialogvé okno");
		dialog.setSize(200, 100);
		dialog.setLocation(600,800);
	}
	
	public void setVisible(boolean viditelnost) {
		dialog.setVisible(viditelnost);
	}
	
}
