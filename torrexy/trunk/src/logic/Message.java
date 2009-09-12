package logic;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {
	private static final String TRACKER_URI_PATTERN = "^GET http://(.+?):?(\\d*)/.* HTTP/\\d.\\d$";
	private boolean loadedAll = false;
	ClientThread src;
	ClientThread dst;
	String msg;
	String dstAddress = null;
	int dstPort = 80;

	public Message(ClientThread src) {
		this.src = src;
		this.dst = null;
		this.msg = "";
		loadInitialRequestLine();
	}

	private void loadInitialRequestLine() {
		String requestLine = null;
		try {
			while (requestLine==null){
				if (src.getInput().ready()){
					requestLine = src.getInput().readLine();//todo null msg
				}
			}
			msg += requestLine+"\n";
		} catch (IOException e) {
			e.printStackTrace();
		}
		Pattern pattern = Pattern.compile(TRACKER_URI_PATTERN, Pattern.MULTILINE);
		Matcher matcher = pattern.matcher(requestLine);

		boolean found = false;
		while (matcher.find()) {
			found = true;
			dstAddress = matcher.group(1);
			System.out.println(matcher.group(2));
			if (!matcher.group(2).equals("")){
				dstPort = Integer.parseInt(matcher.group(2));
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

	private void loadAll() {
		String line = null;
		try {
			while(src.getInput().ready()){
				line = src.getInput().readLine();
				msg += line+"\n";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.loadedAll = true;
	}
}
