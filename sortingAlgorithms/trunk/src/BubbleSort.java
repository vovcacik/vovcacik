

public class BubbleSort {
	public static void main(String[] args) {
		BubbleSort bs = new BubbleSort();
		bs.init(new int[] { 5, 4, -9, 78, -25, 4, 42, -100, 42 });
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

		for (int i : prvky) {
			System.out.print(i + ", ");
		}
	}
}
