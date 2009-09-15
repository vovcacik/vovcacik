package logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Message {
	//group 1 inetAddress of destination; group 2 port of dst
	private static final String TRACKER_URI_PATTERN = "^GET http://(.+?):?(\\d*)/.* HTTP/\\d.\\d$";
	private static final String CONTENT_LENGTH_PATTERN = "^Content-Length: (\\d+)$";
	private boolean loadedAll = false;
	private boolean close = false;
	private List<String> headers;
	private List<String> data;
	ClientThread src;
	ClientThread dst;
	String msg;
	String dstAddress = null; //TODO rozpustit tyto atributy
	int dstPort = 80;
	private long dataLength = 0;
	

	public Message(ClientThread src, ClientThread dst) {
		this.src = src;
		this.dst = dst;
		this.msg = "";
		this.headers = new ArrayList<String>();
		this.data = new ArrayList<String>();
		loadInitialRequestLine();
	}

	public static Message getInstance(String initialLine, ClientThread src, ClientThread dst) {
		if (initialLine.startsWith("GET")) return new GETRequest(src, dst);
		return null;
	}
	
	void addHeader(String headerLine) {
		headers.add(headerLine);
		if (headerLine.startsWith("Content-length: ")) {
			Pattern pattern = Pattern.compile(CONTENT_LENGTH_PATTERN);
			Matcher matcher = pattern.matcher(headerLine);
			if (matcher.find()) {
				dataLength = Integer.parseInt(matcher.group(1));//TODO otestovat
			}
		}
	}
	
	public void addData(String dataLine) {
		data.add(dataLine);
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

	public void deliver() {
		dst.send(this); //ošetøit NPE
		if(close) {
			src.close();
			dst.close();
		}
	}

	public void setDst(ClientThread dst) {
		this.dst = dst;
	}

	public long getDataLength() {
		return this.dataLength ;
	}
}
