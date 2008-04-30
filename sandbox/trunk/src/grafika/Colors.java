package grafika;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class Colors {

	private JDialog dialog;
	private JLabel label;
	private Color nahodnaBarva;
	private Color zlutaBarva;
	
	public Colors(){
		init();
	}
	
	private void init() {
		dialog = new JDialog();
		dialog.setTitle("barvy");
		
		nahodnaBarva = new Color(153, 51, 102, 50);
		zlutaBarva = Color.YELLOW;
		
		label = new JLabel();
		label.setText("ukázka textu");
		label.setForeground(nahodnaBarva);
		label.setBackground(zlutaBarva);
		
		dialog.add(label);
	}
	
	public void setVisible(boolean viditelnost){
		dialog.setVisible(viditelnost);
	}
}
