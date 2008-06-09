package grafika;

import javax.swing.JFrame;

public class Window {

	private JFrame hlavniOkno;
	private Platno platno;

	public Window() {
		hlavniOkno = new JFrame("Had");
		hlavniOkno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hlavniOkno.setResizable(false);

		platno = new Platno(hlavniOkno, 200, 200, 10, 10, new int[] {5, 5});

		hlavniOkno.add(platno);
		hlavniOkno.pack();
	}

	public void setVisible(boolean b) {
		hlavniOkno.setVisible(b);
	}

}
