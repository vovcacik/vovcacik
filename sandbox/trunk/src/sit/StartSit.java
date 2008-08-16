package sit;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class StartSit {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		InetAddress localhost;
		try {
			localhost = InetAddress.getByName("router");
			System.out.println(localhost.isReachable(5000));
			System.out.println(localhost);
			System.out.println(localhost.getHostAddress());
			System.out.println(localhost.getHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
