package logic;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {
	//group 1 inetAddress of destination; group 2 port of dst
	private static final String TRACKER_URI_PATTERN = "^GET http://(.+?):?(\\d*)/.* HTTP/\\d.\\d$";
	private boolean loadedAll = false;
	private boolean close = false;
	ClientThread src;
	ClientThread dst;
	String msg;
	String dstAddress = null; //TODO rozpustit tyto atributy
	int dstPort = 80;

	public Message(ClientThread src) {
		this.src = src;
		this.dst = null;
		this.msg = "";
		loadInitialRequestLine();
	}

	private void loadInitialRequestLine() {
		String requestLine = null;
		//TODO zakázat více volání
		try {
			while (requestLine==null){
				if (src.getInput().ready()){
					requestLine = src.getInput().readLine();
				} else {
					try {
						Thread.sleep(100); //TODO kolik ms je optimum?
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} 
			//TODO vymazat adresu serveru z GET requestù GET !/announce/... HTTP/1.1
			msg += requestLine+"\n";
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (dst == null) processInitialRequestLine(requestLine); 
	}

	private void processInitialRequestLine(String requestLine) {
		Pattern pattern = Pattern.compile(TRACKER_URI_PATTERN, Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(requestLine);

		boolean found = false;
		while (matcher.find()) {
			found = true;
			dstAddress = matcher.group(1);
			System.out.println(matcher.group(2));//TODO vymazat
			if (!matcher.group(2).equals("")){
				dstPort = Integer.parseInt(matcher.group(2));//TODO otestovat
			}
		} if(!found){
			System.out.println("Remote server address (tracker) not found.\nInitial line: "+requestLine+"\n");
		}
	}

	public InetAddress getDstInetAddress() {
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getByName(dstAddress);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return inetAddress;
	}

	public int getDstPort() {
		return dstPort;
	}
	
	@Override
	public String toString() {
		return getMsgString();
	}

	String getMsgString() {
		if (!this.loadedAll){
			loadAll();
		}
		return msg;
	}

	void loadAll() {
		String line = null;
		try {
			while(src.getInput().ready()){
				line = src.getInput().readLine();
				if (line.equals("Connection: close")){
					this.close = true;
				}
				msg += line+"\n"; //TODO optimalizovat naèítání øetìzcù
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.loadedAll = true;
	}

	public boolean isResponse() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isClose() { //TODO název?
		return this.close;
	}
}
