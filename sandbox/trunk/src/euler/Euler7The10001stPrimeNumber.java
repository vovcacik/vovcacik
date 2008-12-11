package euler;

public class Euler7The10001stPrimeNumber {

	public static void main(String[] args) {
		int index = 0;
		outer: for (int i = 2;; i++) {
			for (int d = 2; d < i; d++) {
				if (i % d == 0) continue outer;
			}
			index += 1;
			System.out.println(index + ".  -  " + i);
			if (index == 10001) {
				System.out.println("vysledek: " + i);
				break outer;
			}
		}
	}
}
