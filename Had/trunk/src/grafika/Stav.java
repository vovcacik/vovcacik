package grafika;

import java.awt.Color;

public enum Stav {

	HAD(Color.RED), PRAZDNE(Color.LIGHT_GRAY);

	private Color barva;

	private Stav(Color barva) {
		this.barva = barva;
	}

	public Color getColor() {
		return barva;
	}
}
