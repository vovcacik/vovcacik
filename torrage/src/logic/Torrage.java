package logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.klomp.snark.bencode.BDecoder;
import org.klomp.snark.bencode.BEValue;

public class Torrage {
	public static void main(String[] args) {
		try {
			BDecoder dec = new BDecoder(new FileInputStream("c:\\Documents and Settings\\Vlasta\\plocha\\test.txt"));
			BEValue value;
			try {
				int indicator = dec.getNextIndicator();
				value = dec.bdecode();
				while(value != null && indicator=='l') {
					System.out.println(value.getList());
//					Torrent torrent = new Torrent(value.getMap());
					indicator = dec.getNextIndicator();
					value = dec.bdecode();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
