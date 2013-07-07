package org.laukvik.jdbc.syntax;


public class CommaReader extends GroupReader {

	public CommaReader() {
		EmptyReader empty = new EmptyReader();
		empty.setRequired( false );
		
		addOptional( empty );
		add( "," );
		addOptional( empty );
	}
	
	public static void main(String[] args) {
		try {
			CommaReader cr = new CommaReader();
			String sql = cr.consume( "  ,	  asf" );
			System.out.println( "Remainder: " + sql );
		} catch (SyntaxException e) {
			e.printStackTrace();
		}
	}
	
}