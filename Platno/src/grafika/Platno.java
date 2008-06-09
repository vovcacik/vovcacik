package grafika;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Platno extends JPanel {

	private class OvladacMysi extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent event) {
			super.mouseClicked(event);
			int[] pozice = zjistiPozici(event.getX(), event.getY());
			getSouradnici(pozice).click();
			repaint();
		}
	}

	private class PohybMysi extends MouseMotionAdapter {

		int[] posledni = new int[] {-1, -1};

		@Override
		public void mouseMoved(MouseEvent event) {
			super.mouseMoved(event);
			int[] pozice = zjistiPozici(event.getX(), event.getY());
			if (pozice[0] != posledni[0] || pozice[1] != posledni[1]) {
				int[][] tvar = new int[][] { {-2, 0}, {-1, 0}, {0, 0}, {1, 0}, {2, 0}, {-1, -1}, {1, -1},};
				for (Souradnice s : souradnice) {
					s.reset();
				}
				for (int[] s : tvar) {
					getSouradnici(new int[] {pozice[0] + s[0], pozice[1] + s[1]}).click();
				}
				repaint();
				posledni = pozice;
			}
			System.out.print("[" + event.getX() + "," + event.getY() + "]\n" + "x: " + pozice[0] + "     y: " + pozice[1] + "\n\n");
		}
	}

	private class Robot extends TimerTask {
		private Random random = new Random();

		@Override
		public void run() {
			for (int i = 0; i < RADKY * SLOUPCE; i++) {
				int index = random.nextInt(RADKY * SLOUPCE);
				souradnice.get(index).click();
			}
			repaint();
		}
	}

	private static final int SLOUPCE = 10;
	private static final int RADKY = 10;
	public static final int[] ROZMERY = new int[] {300, 300};
	private List<Souradnice> souradnice;
	private int period = 500;

	public Platno() {
		setPreferredSize(new Dimension(ROZMERY[0] + 1, ROZMERY[1] + 1));
		addMouseListener(new OvladacMysi());
		addMouseMotionListener(new PohybMysi());
		souradnice = new ArrayList<Souradnice>(RADKY * SLOUPCE);
		for (int y = 0; y < RADKY; y++) {
			for (int x = 0; x < SLOUPCE; x++) {
				souradnice.add(new Souradnice(new int[] {x, y}));
			}
		}

		Robot robot = new Robot();
		Timer vlakno = new Timer();
		// vlakno.schedule(robot, 500, period);
	}

	private Souradnice getSouradnici(int[] pozice) {
		int index = pozice[0] + (pozice[1] * SLOUPCE);
		return souradnice.get(index);
	}

	private void kresliMrizku(Graphics g) {
		int krokX = ROZMERY[0] / SLOUPCE;
		int krokY = ROZMERY[1] / RADKY;
		g.setColor(Color.BLUE);
		for (int x = 0; x <= ROZMERY[0]; x = x + krokX) {
			g.drawLine(x, 0, x, ROZMERY[1]);
		}
		for (int y = 0; y <= ROZMERY[1]; y = y + krokY) {
			g.drawLine(0, y, ROZMERY[0], y);
		}
	}

	private void kresliSouradnice(Graphics g) {
		int krokX = ROZMERY[0] / SLOUPCE;
		int krokY = ROZMERY[1] / RADKY;
		for (Souradnice s : souradnice) {
			int x1, y1, x2, y2;
			x1 = s.getX() * krokX + 1;
			y1 = s.getY() * krokY + 1;
			x2 = s.getX() * krokX + krokX - 1;
			y2 = s.getY() * krokY + krokY - 1;

			g.setColor(s.getColor());
			g.fillRect(x1, y1, x2, y2);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		kresliSouradnice(g);
		kresliMrizku(g);
	}

	private int[] zjistiPozici(int x, int y) {
		if (x >= ROZMERY[0]) x = ROZMERY[0] - 1;
		if (y >= ROZMERY[1]) y = ROZMERY[1] - 1;
		return new int[] {x / (ROZMERY[0] / SLOUPCE), y / (ROZMERY[1] / RADKY),};
	}
}
