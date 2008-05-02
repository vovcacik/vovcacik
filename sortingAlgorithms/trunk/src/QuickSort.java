import java.util.Random;

public class QuickSort {

	public static void main(String[] args) {
		QuickSort qs = new QuickSort();
		
		Random nahoda = new Random();
		int length = 15471859;
		int[] pole = new int[length];
		for (int i = 0; i < pole.length; i++) {
			pole[i] = nahoda.nextInt(length)-length/2;
		}
		
		qs.init(pole);
	}

	public QuickSort() {
		super();
	}

	public void init(int[] values) {
		double zacatek = System.currentTimeMillis();
		quickSort(values, 0, values.length - 1);
		System.out.print("zacatek = " + zacatek + "\nkonec: " + System.currentTimeMillis()+"\n");
		System.out.print("rozdil = " + (System.currentTimeMillis() - zacatek));
	}

	private void quickSort(int[] values, int start, int end) {
		int value; // pomocná promìnná
		int pivot = values[(start + end) / 2];
		int left = start;
		int right = end;

		do {
			while (values[left] < pivot && left < end)
				left++;
			while (values[right] > pivot && right > start)
				right--;

			if (left <= right) {
				value = values[left];
				values[left] = values[right];
				values[right] = value;
				left++;
				right--;
			}
		} while (left <= right);

		if (start < right)
			quickSort(values, start, right);
		if (left < end)
			quickSort(values, left, end);
	}

}
