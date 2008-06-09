package grafika;

import java.util.ArrayList;
import java.util.List;

public class Had {

	private Platno platno;
	private int[] start;
	private int delka;
	private Smer smer;
	private List<Souradnice> souradnice;

	public Had(Platno platno, int[] start, int delka, Smer smer) {
		this.platno = platno;
		this.start = start;
		this.delka = delka;
		this.smer = smer;
		souradnice = new ArrayList<Souradnice>(delka);

		vytvorHada(start, delka, smer);
	}

	private void vytvorHada(int[] start, int delka, Smer smer) {
		switch (smer) {
		case NORTH:
			smer = Smer.SOUTH;
			break;
		case SOUTH:
			smer = Smer.NORTH;
			break;
		case EAST:
			smer = Smer.WEST;
			break;
		case WEST:
			smer = Smer.EAST;
			break;
		}
		Souradnice s;
		int[] pozice = start;
		System.out.println("start for");
		for (int i = 0; i < delka; i++) {
			s = platno.getSouradnice(pozice);
			s.setStav(Stav.HAD);
			System.out.println("setstav.had");
			souradnice.add(souradnice.size(), s);
			pozice = platno.getNextPozice(pozice, smer);
		}
		platno.repaint();
	}

	public void setSmer(Smer smer) {
		if (!smer.equals(Smer.getOpacny(this.smer))) {
			this.smer = smer;
		}
	}

	public void krok() {
		Souradnice prvni = souradnice.get(0);
		Souradnice posledni = souradnice.get(souradnice.size() - 1);
		Souradnice nova;
		int[] pozice = new int[] {prvni.getX(), prvni.getY()};
		int[] dalsiPozice = platno.getNextPozice(pozice, smer);
		nova = platno.getSouradnice(dalsiPozice);
		nova.setStav(Stav.HAD);
		souradnice.add(0, nova);

		posledni.setStav(Stav.PRAZDNE);
		souradnice.remove(souradnice.size() - 1);
		platno.repaint();
	}
}
