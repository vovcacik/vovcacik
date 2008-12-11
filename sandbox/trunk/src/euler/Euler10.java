package euler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Euler10 {

	public static void main(String[] args) {
		List<Double> primes = new ArrayList<Double>();
		double sum = 0;
		outer: for (double i = 2;; i++) {
			for (Double d : primes) {
				if (d > Math.sqrt(i)) break;
				if ((i / d) - Math.floor(i / d) == 0) continue outer;
			}
			if (i < 2000000) {
				sum += i;
				System.out.println(i);
				primes.add(new Double(i));
			} else {
				System.out.println(new BigDecimal(sum));
				return;
			}
		}
	}
}
