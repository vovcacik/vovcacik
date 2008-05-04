package ukazkaSynchronized;

public class Sync {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sync s = new Sync();
		s.init();
	}

	public void init() {
		metodaA(0);
	}
	
	public synchronized void metodaA(int i) {
		if (i<3) {
			System.out.println("metodaA " + i);
			metodaB(i);
			System.out.println("metodaA " + i + " konec");
		} else {
			System.out.println("metodaA " + i + " na dnì");
		}
	}
	public void metodaB(int i) {
		System.out.println("metodaB " + i);
		metodaC(i);
		System.out.println("metodaB " + i + " konec");
	}
	public void metodaC(int i) {
		System.out.println("metodaC " + i);
		int y = i + 1;
		metodaA(y);
		System.out.println("metodaC " + i + " konec");
	}
	
}
