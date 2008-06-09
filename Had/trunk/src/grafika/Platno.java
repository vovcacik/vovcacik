package grafika;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Platno extends JPanel {

	private class Klavesnice extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				had.setSmer(Smer.NORTH);
				break;
			case KeyEvent.VK_RIGHT:
				had.setSmer(Smer.EAST);
				break;
			case KeyEvent.VK_DOWN:
				had.setSmer(Smer.SOUTH);
				break;
			case KeyEvent.VK_LEFT:
				had.setSmer(Smer.WEST);
				break;
			}
		}
	}

	private class Tik extends TimerTask {
		@Override
		public void run() {
			had.krok();
		}
	}

	private int sirkaSloupce;
	private int vyskaRadku;
	private int column;
	private int rows;
	private List<Souradnice> souradnice;
	private Had had;

	public Platno(JFrame hlavniOkno, int xPixel, int yPixel, int column, int rows, int[] iniPoziceHada) {
		hlavniOkno.addKeyListener(new Klavesnice());
		setPreferredSize(new Dimension(xPixel, yPixel));
		this.column = column;
		this.rows = rows;
		sirkaSloupce = xPixel / column;
		vyskaRadku = yPixel / rows;

		souradnice = new ArrayList<Souradnice>(column * rows);
		for (int y = 0; y < rows; y++) {
			for (int x = 0; x < column; x++) {
				souradnice.add(new Souradnice(x, y, Stav.PRAZDNE));
			}
		}

		this.had = new Had(this, iniPoziceHada, 4, Smer.SOUTH);
		Timer timer = new Timer();
		timer.schedule(new Tik(), 0, 300);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// vykresli souradnice
		for (Souradnice s : souradnice) {
			kresliBunku(g, new int[] {s.getX(), s.getY()}, s.getColor());
		}
	}

	private void kresliBunku(Graphics g, int[] souradnice, Color barva) {
		g.setColor(barva);
		g.fillRect(souradnice[0] * sirkaSloupce, souradnice[1] * vyskaRadku, sirkaSloupce, vyskaRadku);
		g.setColor(Stav.PRAZDNE.getColor());
	}

	public Souradnice getSouradnice(int[] pozice) {
		int index = pozice[0] + pozice[1] * column;
		return souradnice.get(index);
	}

	public int[] getNextPozice(int[] pozice, Smer smer) {
		int[] krok = smer.getKrok();
		return new int[] {pozice[0] + krok[0], pozice[1] + krok[1]};
	}

	public void konec() {

	}

	public boolean checkPozice(int[] pozice) {
		return (pozice[0] >= 0 && pozice[0] < column) && (pozice[1] >= 0 && pozice[1] < rows);
	}
}
