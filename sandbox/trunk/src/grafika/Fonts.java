package grafika;

import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;

public class Fonts {

	private JDialog dialog;
	private JLabel label1;
	private JLabel label2;
	private Font font1 = new Font("Serif", Font.BOLD, 20);
	private Font font2 = new Font("Comic Sans MS", Font.ITALIC, 25);
	
	public Fonts() {
		init();
	}
	
	private void init() {
		dialog = new JDialog();
		dialog.setTitle("fonty");
		
		label1 = new JLabel();
		label1.setText("ukázka textu1");
		label1.setFont(font1);
		
		label2 = new JLabel();
		label2.setText("ukázka textu2");
		label2.setFont(font2);
		
		dialog.add(label1);
		dialog.add(label2);
	}
	
	public void setVisible(boolean viditelnost){
		dialog.setVisible(viditelnost);
	}
}
