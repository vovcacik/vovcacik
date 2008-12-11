package euler;

public class Euler9 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int a, b, c;
		for (a = 1; a < 1000; a++) {
			for (b = 1; b < 1000; b++) {
				for (c = 1; c < 1000; c++) {
					if ((a + b + c) != 1000) {
						continue;
					}
					pythagor(a, b, c);
				}
			}
		}
	}

	private static boolean pythagor(int a, int b, int c) {
		if (Math.pow(a, 2) + Math.pow(b, 2) == Math.pow(c, 2)) {
			System.out.println("Vysledek: " + a + ", " + b + ", " + c);
			return true;
		}
		return false;
	}

}
