package euler;

import java.math.BigDecimal;

public class Euler6 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double squares = 0;
		double squaresOfSum = 0;

		for (int i = 1; i <= 100; i++) {
			squares += i * i;
			squaresOfSum += i;
		}
		squaresOfSum *= squaresOfSum;
		double vysledek = squaresOfSum - squares;
		System.out.println(new BigDecimal(vysledek));
	}

}
