package euler;

public class EulerStart {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println(T(1d));
	}

	private static double T(double n) {
		double sum = 0;
		for (double i = Math.pow(10, n) - 1; i > 0; i--) {
			if (balanced(i)) {
				sum += i;
			}
		}
		return sum;
	}

	private static boolean balanced(double i) {
		String bali = String.valueOf((int) i);
		double firstsum = 0;
		double secondsum = 0;
		for (int j = 0; j <= (bali.length() / 2); j++) {
			firstsum += Integer.parseInt(bali.substring(j, j + 1));
			secondsum += Integer.parseInt(bali.substring(bali.length() - 1 - j, bali.length() - j));
		}
		if (firstsum == secondsum) return true;
		return false;
	}
}