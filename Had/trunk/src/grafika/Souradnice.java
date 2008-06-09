package grafika;

import java.awt.Color;

public class Souradnice {

	private Stav stav;
	private int y;
	private int x;

	public Souradnice(int x, int y, Stav stav) {
		this.x = x;
		this.y = y;
		this.stav = stav;
	}

	public Color getColor() {
		return stav.getColor();
	}

	public int getY() {
		return this.y;
	}

	public int getX() {
		return this.x;
	}

	public void setStav(Stav stav) {
		this.stav = stav;
	}

	public Stav getStav() {
		return this.stav;
	}

}
