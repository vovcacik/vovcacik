package grafika;

import javax.swing.JFrame;

public class HlavniOkno {

	private JFrame okno;
	
	public HlavniOkno() {
		init();
	}
	
	private void init() {
		okno = new JFrame();
		okno.setTitle("swing");
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setSize(150,150);
		okno.setLocationRelativeTo(null);
	}
	
	public void setVisible(boolean viditelnost) {
		okno.setVisible(viditelnost);
	}
	
}
