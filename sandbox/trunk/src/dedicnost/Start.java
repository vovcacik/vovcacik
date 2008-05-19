package dedicnost;

public class Start {

	private static Rozhrani instance;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		instance = new Predek();
		boolean vysledek = instance.equals(instance.getInstance());
		
		System.out.println(vysledek);
	}

}
