package grafika;

import java.awt.Color;

public class Souradnice {

	private int[] souradnice;
	private Color[] barvy = new Color[] {Color.WHITE, Color.LIGHT_GRAY, Color.GRAY, Color.DARK_GRAY, Color.BLACK};
	private int indexBarvy;

	public Souradnice(int[] souradnice) {
		this.souradnice = souradnice;
		indexBarvy = 0;
	}

	public void click() {
		indexBarvy = (indexBarvy + 1) % barvy.length;
	}

	public int getX() {
		return souradnice[0];
	}

	public int getY() {
		return souradnice[1];
	}

	public Color getColor() {
		return barvy[indexBarvy];
		// Random random = new Random();
		// return new Color(random.nextInt(255), random.nextInt(255),
		// random.nextInt(255));
	}

	public void reset() {
		indexBarvy = 0;
	}

}
