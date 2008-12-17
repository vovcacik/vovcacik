package logika;

public class Policko {

	private int[] xy;
	private boolean obsazene;
	private boolean platne;

	public Policko(int[] xy, boolean obsazene, boolean platne) {
		this.xy = xy;
		this.obsazene = obsazene;
		this.platne = platne;
	}

	public int[] souradnice() {
		return xy;
	}

	public void setObsazene(boolean obsazene) {
		this.obsazene = obsazene;
	}

	public boolean obsazene() {
		return this.obsazene;
	}

	public boolean getPlatne() {
		return this.platne;
	}

}
