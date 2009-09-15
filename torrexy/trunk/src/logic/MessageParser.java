package logic;

import java.io.BufferedReader;
import java.io.IOException;

public class MessageParser {
	public static Message parse(BufferedReader input, ClientThread src, ClientThread dst) throws IOException{
		Message message = null;
		if(input.ready()) {
			String initialLine = input.readLine();
			message = Message.getInstance(initialLine, src, dst);
		}
//		if (message == null) throw new IllegalArgumentException(); //TODO lepší vyjímku?
		while (input.ready()) {
			String headerLine = input.readLine();
			if (headerLine.equals("")) break;
			message.addHeader(headerLine);
		}
		long dataLeft = message.getDataLength();
		while (input.ready() && dataLeft==0) {
			String dataLine = input.readLine();
			message.addData(dataLine);
			dataLeft -= dataLine.length();
		}
		return message;
	}
}
