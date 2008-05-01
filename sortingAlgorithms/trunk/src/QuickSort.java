public class QuickSort {

	public static void main(String[] args) {
		QuickSort qs = new QuickSort();
		qs.init(new int[] { -15, 25, 45, -59, -78, 2, -5, 96, 85, -84, 26, -35,
				45, -41, -65, 0, 45, 65, 42, -12, -59 });
	}

	public QuickSort() {
		super();
	}

	public void init(int[] values) {
		quicksort(values, 0, values.length - 1);

		// tisk
		for (int i : values) {
			System.out.print(i + ", ");
		}
	}

	private void quicksort(int[] values, int start, int end) {
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
			quicksort(values, start, right);
		if (left < end)
			quicksort(values, left, end);
	}

}
