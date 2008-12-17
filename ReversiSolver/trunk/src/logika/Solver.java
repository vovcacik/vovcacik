package logika;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Solver {

	private List<Policko> herniPole;
	private int[][] zakladniPoziceKamenu;
	private Stack<Tah> aktualniTahy;
	private Stack<Tah> mozneTahy;

	public Solver() {
		herniPole = new ArrayList<Policko>();
		aktualniTahy = new Stack<Tah>();
		mozneTahy = new Stack<Tah>();
		// zakladniPoziceKamenu = new int[][] { {2, 2}, {3, 1}, {3, 2}, {3, 3},
		// {3, 4}, {4, 2}};
		// zakladniPoziceKamenu = new int[][] { {1, 3}, {2, 3}, {3, 3}, {4, 3},
		// {5, 3}, {3, 1}, {3, 2}, {3, 3}, {3, 4}, {3, 5}};
		// zakladniPoziceKamenu = new int[][] { {2, 0}, {3, 0}, {4, 0}, {2, 1},
		// {3, 1}, {4, 1}, {2, 2}, {3, 2}, {4, 2}, {2, 3}, {4, 3}};
		zakladniPoziceKamenu = new int[][] { {3, 1}, {2, 2}, {3, 2}, {4, 2}, {1, 3}, {5, 3}, {4, 3}, {3, 3}, {2, 3}, {0, 4}, {6, 4}, {5, 4}, {3, 4}, {2, 4}, {1, 4}};

		// vytvoøení herního pole
		for (int y = 0; y < 7; y++) {
			for (int x = 0; x < 7; x++) {
				Policko policko = null;
				// zneplatnìní rohových polí
				if ((x < 2 && (y < 2 || y > 4)) || ((x > 4 && (y < 2 || y > 4)))) {
					policko = new Policko(new int[] {x, y}, false, false);
				} else {
					policko = new Policko(new int[] {x, y}, false, true);
				}
				if (policko != null) herniPole.add(policko);

			}
		}

		// posazení kamenù
		for (Policko pole : herniPole) {
			for (int[] kamen : zakladniPoziceKamenu) {
				if (pole.souradnice()[0] == kamen[0] && pole.souradnice()[1] == kamen[1]) {
					pole.setObsazene(true);
				}
			}

		}

	}

	public String res() {
		while (true) {
			if (!this.tah()) break;
		}

		String path = "";
		for (Tah tah : aktualniTahy) {
			path += tah.uroven + ".: \n" + tah.getZacatek().souradnice()[0] + ", " + tah.getZacatek().souradnice()[1] + "\n" + +tah.getPres().souradnice()[0] + ", " + tah.getPres().souradnice()[1] + "\n" + +tah.getKonec().souradnice()[0] + ", " + tah.getKonec().souradnice()[1] + "\n\n\n";
		}
		return path;
	}

	private boolean tah() {
		boolean konecHry = false;
		y: for (int y = 0; y < 7; y++) {
			for (int x = 0; x < 7; x++) {
				Policko pole = getPolicko(x, y);
				if (pole.obsazene() && pole.souradnice()[0] == 3 && pole.souradnice()[1] == 3) {
					konecHry = true;
				} else if (pole.obsazene()) {
					konecHry = false;
					break y;
				}
			}
		}
		if (konecHry) {
			return false;
		}

		if (!najdiTahy()) {
			if (mozneTahy.empty()) {
				aktualniTahy.removeAllElements();
				return false;
			}
			Tah tah = aktualniTahy.pop();
			vratTah(tah);
			while (tah.uroven != mozneTahy.peek().uroven) {
				tah = aktualniTahy.pop();
				vratTah(tah);
			}
		}
		Tah tah = mozneTahy.pop();
		aktualniTahy.push(tah);
		provedTah(tah);
		return true;
	}

	private boolean najdiTahy() {
		boolean naselTah = false;

		for (int y = 0; y < 7; y++) {
			x: for (int x = 0; x < 7; x++) {
				Policko pole = getPolicko(x, y);
				if (pole == null) break x;
				Policko[][] okolni = new Policko[][] { {getPolicko(x, y - 2), getPolicko(x, y - 1)}, {getPolicko(x + 2, y), getPolicko(x + 1, y)}, {getPolicko(x, y + 2), getPolicko(x, y + 1)}, {getPolicko(x - 2, y), getPolicko(x - 1, y)},};
				for (int i = 0; i < okolni.length; i++) {
					if ((okolni[i][0] != null && okolni[i][1] != null) && (okolni[i][0].getPlatne() && okolni[i][1].getPlatne() && (pole.getPlatne())) && (okolni[i][0].obsazene() && okolni[i][1].obsazene() && (!pole.obsazene()))) {
						int uroven;
						if (aktualniTahy.empty()) {
							uroven = 0;
						} else {
							uroven = aktualniTahy.peek().uroven + 1;
						}
						Tah tah = new Tah(okolni[i][0], okolni[i][1], pole, uroven);
						mozneTahy.push(tah);
						naselTah = true;
						// System.out.println(tah.getPres().souradnice()[0] + "
						// " + tah.getPres().souradnice()[1]);
					}
				}
			}
		}
		return naselTah;
	}

	private void provedTah(Tah tah) {
		getPolicko(tah.getZacatek().souradnice()[0], tah.getZacatek().souradnice()[1]).setObsazene(false);
		getPolicko(tah.getPres().souradnice()[0], tah.getPres().souradnice()[1]).setObsazene(false);
		getPolicko(tah.getKonec().souradnice()[0], tah.getKonec().souradnice()[1]).setObsazene(true);
	}

	private void vratTah(Tah tah) {
		getPolicko(tah.getZacatek().souradnice()[0], tah.getZacatek().souradnice()[1]).setObsazene(true);
		getPolicko(tah.getPres().souradnice()[0], tah.getPres().souradnice()[1]).setObsazene(true);
		getPolicko(tah.getKonec().souradnice()[0], tah.getKonec().souradnice()[1]).setObsazene(false);
		switch (tah.uroven) {
		case 0:
			System.out.println("########################################");
		case 1:
			System.out.println("#########################");
		case 2:
			System.out.println("##########");
		}
	}

	private Policko getPolicko(int x, int y) {
		for (Policko pole : herniPole) {
			if (pole.souradnice()[0] == x && pole.souradnice()[1] == y) return pole;
		}
		return null;
	}
}
