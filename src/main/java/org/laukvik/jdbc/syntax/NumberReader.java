package org.laukvik.jdbc.syntax;

import java.util.Vector;


public class NumberReader extends Reader {
	
	Vector<NumberListener> numberListeners;

	public NumberReader(){
		numberListeners = new Vector<NumberListener>();
	}
	
	public NumberListener addNumberListener( NumberListener listener ){
		this.numberListeners.add( listener );
		return listener;
	}
	
	public void removenmberListener( NumberListener listener ){
		this.numberListeners.remove( listener );
	}
	
	public void fireNumberFound( Number number ){
		for(NumberListener l : numberListeners){
			l.found( number );
		}
	};
	
	public String consume( String sql ) throws SyntaxException {
		System.err.println( "NumberReader" );
		boolean continueToLook = true;
		int x = 0;
		while(continueToLook && x <sql.length()){
			if (Character.isDigit( sql.charAt( x ) )){
				x++;
			} else {
				continueToLook = false;
			}
			
		}
		/* Check if we have a number with remainders */
		if (sql.charAt( x ) == '.'){
			x++;
			continueToLook = true;
			while(continueToLook && x <sql.length()){
				if (Character.isDigit( sql.charAt( x ) )){
					x++;
				} else {
					continueToLook = false;
				}
				
			}
			String n = sql.substring(0,x);
			fireFoundResults( n );
			float number = Float.parseFloat(  n  );
			log( "Found float " + number );
			fireNumberFound( number );
		} else {
			if (x == 0){
				throw new SyntaxException("Could not find a number");
			}
			String n = sql.substring(0,x);
			fireFoundResults( n );
			int number = Integer.parseInt(  n  );
			log( "Found int " + number );
			fireNumberFound( number );
		}

		return sql.substring( x );
	}

	public String getPurpose() {
		return "Consumes on word";
	}
	
	public static void main(String[] args) {
		try {
			NumberReader r = new NumberReader();
			r.debug = true;
			System.out.println( r.consume( "250.79 Morten" ) );
		} catch (SyntaxException e) {
			e.printStackTrace();
		}
	}


}