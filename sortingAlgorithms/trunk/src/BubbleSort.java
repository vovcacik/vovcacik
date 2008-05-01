import java.util.Random;



public class BubbleSort {
	public static void main(String[] args) {
		BubbleSort bs = new BubbleSort();
		
		Random nahoda = new Random();
		int length = 50000;
		int[] pole = new int[length];
		for (int i = 0; i < pole.length; i++) {
			pole[i] = nahoda.nextInt(length)-length/2;
		}
		
		double zacatek = System.currentTimeMillis();
		bs.init(pole);
		System.out.print("zacatek = " + zacatek + "\nkonec: " + System.currentTimeMillis()+"\n");
		System.out.print("rozdil = " + (System.currentTimeMillis() - zacatek));
	}

	public BubbleSort() {
		super();
	}

	public void init(int[] prvky) {
		int pomocna;

		for (int s = 1; s < prvky.length; s++) {
			for (int e = prvky.length - 1; e >= s; e--) {
				if (prvky[e - 1] > prvky[e]) {
					pomocna = prvky[e - 1];
					prvky[e - 1] = prvky[e];
					prvky[e] = pomocna;
				}
			}
		}
	}
}
