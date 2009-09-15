package cz.ovcacik.httpParser;


import java.util.List;

public abstract class Message {

	/** 
	 * @uml.property name="headers"
	 */
	protected List headers;
	/** 
	 * @uml.property name="data"
	 */
	protected List data;
		
			
			
			public abstract void addHeader(String header);



				
				/**
				 */
				public abstract void addData(String data);
				
			

	
}
