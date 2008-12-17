package logika;

public class Tah {

	private Policko zacatek;
	private Policko pres;
	private Policko konec;
	public int uroven;

	public Tah(Policko zacatek, Policko pres, Policko konec, int uroven) {
		this.zacatek = zacatek;
		this.pres = pres;
		this.konec = konec;
		this.uroven = uroven;
	}

	public Policko getZacatek() {
		return zacatek;
	}

	public Policko getPres() {
		return pres;
	}

	public Policko getKonec() {
		return konec;
	}

}
