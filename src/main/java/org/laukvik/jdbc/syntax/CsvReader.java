package org.laukvik.jdbc.syntax;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Vector;

public class CsvReader extends Reader {
	
	public final static char QUOTE = '"';
	public final static char COMMA = ',';
	public final static char LINEFEED = '\n';
	
	Vector<CsvListener> listeners;
	
	Reader reader, columnSeperator, rowSeperator;

	public CsvReader() {
		super();
	}

	public String getPurpose() {
		return "Consumes one column entry";
	}
	
	public String consumeQuote(String sql) throws SyntaxException {	
		StringBuffer buffer = new StringBuffer();
		boolean isStillSearching = true;
		boolean foundQuote = false;
		int x = 1;
		while(isStillSearching && x < sql.length()){
			char c = sql.charAt( x );
			if (c == QUOTE){
				if (x < sql.length()-1 && sql.charAt( x+1 ) == QUOTE){
					isStillSearching = true;
					x++;
				} else {
					isStillSearching = false;
					foundQuote = true;
				}
			} else {
				isStillSearching = true;
			}

			if(isStillSearching){
				buffer.append(c);
				x++;
			}	
		}
		if (!foundQuote){
			throw new SyntaxException( "Could not find an ending quote" );
		}
		String rest = sql.substring( x+1 );
		
		fireFoundResults( buffer.toString() );
		return rest;
	}
	
	public String consumeUnQuote(String sql) throws SyntaxException {
		/* Save a variable to hold found text */
		StringBuffer buffer = new StringBuffer();
		String rest = "";
		for (int x=0; x<sql.length(); x++){
			char c = sql.charAt( x );
			if (c == COMMA || c == LINEFEED){
				rest = sql.substring( x );
				x = sql.length();
			} else {
				buffer.append( c );
			}
		}
		
		fireFoundResults( buffer.toString() );
		
		return rest;
	}
	
	public String consume(String sql) throws SyntaxException {
		boolean useQuotation = (sql.charAt( 0 ) == QUOTE);
//		boolean isLinefeed = (sql.charAt( 0 ) == LINEFEED);
		if (useQuotation){
			return consumeQuote( sql );
		} else {
			return consumeUnQuote( sql );
		}
	}
	
	public static String parse( BufferedInputStream is) throws  Exception {
		while (is.available() > 0){
			
		}
		
		return null;
	}
	
	public static void main( String[] args ){
		
		BufferedInputStream is = new BufferedInputStream( new ByteArrayInputStream( "1999,Chevy,\"Venture \"\"Extended Edition\"\"\",4900.00".getBytes() ) );
		try {
			while (is.available() > 0){
				int b = is.read();
				if (b == QUOTE){
					
				}
				
				System.out.println( b + " " + (b==13) );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		try {
//			CsvReader csv = new CsvReader();
//			ListReader list = new ListReader( csv, new CommaReader() );
//
//			csv.addReaderListener( new ReaderListener(){
//				public void found(String values) {
//					System.err.println( "Found: " + values );
//				}} ); 
//
//
//			list.consume( "1997,Ford,E350,\"ac, abs, moon\",3000.00\nAAA2000" );
////			list.consume( "1999,Chevy,\"Venture \"\"Extended Edition\"\"\",4900.00" );
//			
//		} catch (SyntaxException e) {
//			e.printStackTrace();
//		}
	}

}
