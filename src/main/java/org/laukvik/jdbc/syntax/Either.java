package org.laukvik.jdbc.syntax;


public class Either extends Reader {

	Reader [] readers;
	
	public Either( Reader...readers  ){
		this.readers = readers;
	}
	
	public void setReaders(Reader... readers) {
		this.readers = readers;
	}

	public String consume(String sql) throws SyntaxException {
		boolean successful = false;
		for (int x=0; x<readers.length; x++){
			Reader r = readers[ x ];
			try{
				sql = r.consume( sql );
				successful = true;
//				log( "Either: Successful " + r.getClass().getName() );
				x = readers.length;
			} catch(SyntaxException e2){
//				e2.printStackTrace();
//				log( "Either: Failed " + r.getClass().getName() + " " + e2.getMessage() );
			}
		}
		if (successful){
//			fireFoundResults( "qwr" );
			return sql;
		} else {
			throw new SyntaxException( "Either of the readers where successful" );	
		}
	}

	public String getPurpose() {
		return "Consumes either of them or an exception will be thrown";
	}

	/**
	 * @param args
	 */
	public static void main( String[] args) {
		try {
			NumberReader n = new NumberReader();
			TextReader text = new TextReader("a");
			Either r = new Either( n, text );
			System.out.println( r.consume( "a,1,a,5" ) );
		} catch (SyntaxException e) {
			e.printStackTrace();
		}
	}

}