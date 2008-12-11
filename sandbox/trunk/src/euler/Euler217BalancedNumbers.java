package euler;

import java.math.BigDecimal;

public class Euler217BalancedNumbers {

	/**
	 * Problem 217 Balanced numbers
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.err.println("Zadejte pouze jeden argument");
			System.exit(1);
		}
		int pocet = Integer.parseInt(args[0]);
		if (pocet <= 0) {
			System.err.println("Argument je kladne celéé cislo");
			System.exit(1);
		}
		double sumaBalancu = T((double) pocet);
		System.out.println("Suma balancovanyh cisel: \n" + new BigDecimal(sumaBalancu));
		double vysledek = sumaBalancu % Math.pow(3, 15);
		System.out.println("Vysledek: \n" + new BigDecimal(vysledek));
	}

	private static double T(double n) {
		double sum = 0;
		double history = 0.99;
		for (double i = 1; i <= Math.pow(10, n) - 1; i++) {
			if (balanced(i)) {
				sum += i;
			}
			if (history - (Math.pow(10, n) - i) / Math.pow(10, n) >= 0.01) {
				System.out.println((Math.pow(10, n) - i) * 100 / Math.pow(10, n));
				history = (Math.pow(10, n) - i) / Math.pow(10, n);
			}
		}
		return sum;
	}

	private static boolean balanced(double d) {
		double length = Math.floor(Math.log10(d)) + 1;
		double lowHalf = 0;
		double highHalf = 0;
		for (double i = 0; i < Math.ceil(length / 2); i++) {
			lowHalf += intAt(d, i);
		}
		for (double i = Math.floor(length / 2); i < length; i++) {
			highHalf += intAt(d, i);
		}
		if (lowHalf == highHalf) {
			return true;
		} else {
			return false;
		}
	}

	private static double intAt(double d, double pos) {
		for (double k = (Math.floor(Math.log10(d))); k > pos; k--) {
			while (d >= Math.pow(10, k)) {
				d -= Math.pow(10, k);
			}
		}
		return Math.floor(d / Math.pow(10, pos));
	}
}