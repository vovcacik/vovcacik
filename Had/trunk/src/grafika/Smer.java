package grafika;

public enum Smer {

	NORTH(new int[] {0, -1}), EAST(new int[] {1, 0}), SOUTH(new int[] {0, 1}), WEST(new int[] {-1, 0});

	private int[] krok;

	private Smer(int[] krok) {
		this.krok = krok;
	}

	public int[] getKrok() {
		return this.krok;
	}

	public static Smer getOpacny(Smer smer) {
		switch (smer) {
		case NORTH:
			return SOUTH;
		case EAST:
			return WEST;
		case SOUTH:
			return NORTH;
		case WEST:
			return EAST;
		}
		return null;
	}

}
