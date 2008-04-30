package grafika;

import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Images {

	private JDialog dialog;
	private JLabel label;
	private ImageIcon image;
	private URL cesta = this.getClass().getResource("/zdroje/images1.gif");
	
	public Images() {
		init();
	}
	
	private void init() {
		if(cesta != null) {
			image = new ImageIcon(cesta);
		} else {
			System.out.println(cesta);
		}
		
		dialog = new JDialog();
		dialog.setTitle("obrázky");
		
		label = new JLabel("obrázek", image, SwingConstants.CENTER);
		
		dialog.add(label);
		dialog.pack();
	}
	
	public void setVisible(boolean viditelnost) {
		dialog.setVisible(viditelnost);
	}
}
