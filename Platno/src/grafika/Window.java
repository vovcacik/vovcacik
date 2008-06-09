package grafika;

import java.awt.BorderLayout;
import javax.swing.JFrame;

public class Window {

	private JFrame hlavniOkno;
	private Platno hlavniPlatno;

	public Window() {
		hlavniOkno = new JFrame("Plátno");
		hlavniOkno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		hlavniOkno.setLocationRelativeTo(null);

		hlavniPlatno = new Platno();

		hlavniOkno.add(hlavniPlatno, BorderLayout.CENTER);
		hlavniOkno.pack();
	}

	public void init() {
		hlavniOkno.setVisible(true);
	}
}
