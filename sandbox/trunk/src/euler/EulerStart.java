package euler;

import java.math.BigDecimal;

public class EulerStart {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double sumaBalancu = T(47);
		System.out.println(new BigDecimal(sumaBalancu));
		double vysledek = sumaBalancu % Math.pow(3, 15);
		System.out.println(new BigDecimal(vysledek));
	}

	private static double T(double n) {
		double sum = 0;
		for (double i = Math.pow(10, n) - 1; i > 0; i--) {
			if (balanced(i)) {
				sum += i;
			}
			System.out.println((Math.pow(10, n) - i) / Math.pow(10, n));
			System.out.println("\f");
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