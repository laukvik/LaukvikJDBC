package org.laukvik.jdbc.syntax;

import java.util.Vector;


/**
 * The purpose of this class is to read everything until it can't understand anymore. If 
 * it meets illegal syntax it will throw a SyntaxException.
 * 
 * @author morten
 *
 */
public abstract class Reader {
	
	public static final String SELECT   	= "SELECT";
	public static final String ALL      	= "*";
	public static final String DISTINCT 	= "DISTINCT";
	public static final String ON		 	= "ON";
	public static final String AS	      	= "AS";
	public static final String FROM     	= "FROM";
	public static final String WHERE    	= "WHERE";
	public static final String GROUPBY  	= "GROUP BY";
	public static final String HAVING   	= "HAVING";
	public static final String ORDER    	= "ORDER BY";
	public static final String ASCENDING    = "ASC";
	public static final String DESCENDING   = "DESC";
	public static final String OFFSET   	= "OFFSET";
	public static final String LIMIT    	= "LIMIT";
	
	public static final String CROSSJOIN   		= "CROSS JOIN";
	public static final String INNERJOIN   		= "INNER JOIN";
	public static final String LEFTJOIN   		= "LEFT JOIN";
	public static final String LEFTOUTERJOIN	= "LEFT OUTER JOIN";
	public static final String NATURALJOIN 		= "NATURAL JOIN";
	public static final String OUTERJOIN   		= "OUTER JOIN";
	public static final String FULLOUTERJOIN   	= "FULL OUTER JOIN";
	public static final String RIGHTJOIN   		= "RIGHT JOIN";
	public static final String RIGHTOUTERJOIN   = "RIGHT OUTER JOIN";
	
	public static final String [] JOINS = { CROSSJOIN, INNERJOIN, LEFTJOIN, LEFTOUTERJOIN, NATURALJOIN, OUTERJOIN, FULLOUTERJOIN, RIGHTJOIN, RIGHTOUTERJOIN };
	
	boolean debug = false;
	boolean required = true;
	Vector<Object> items;
	Vector<ReaderListener> listeners;
	
	public Reader() {
		items = new Vector<Object>();
		listeners = new Vector<ReaderListener>();
	}
	
	public void addResults( String values ){
		items.add( values );
		fireFoundResults( values );
//		System.out.println( "Found " + object );
	}
	
	public void fireFoundResults( String values ){
		for (ReaderListener l : listeners){
			l.found( values );
		}
	}
	
	public void addReaderListener( ReaderListener listener ){
		listeners.add( listener );
	}
	
	public void removeReaderListener( ReaderListener listener ){
		listeners.remove( listener );
	}
	
	public Vector<Object> getResults(){
		return items;
	}
	
	public Object getResult( int index ){
		return items.get( index );
	}
	
	public void setRequired( boolean isRequired ){
		this.required = isRequired;
	}
	
	public boolean isRequired() {
		return required;
	}
	
	public void log( Object message ){
		if (debug){
			System.out.println( message );
		}
	}
	
	public String nextMatch( String sql, String... strings ){
		int earliestIndex = sql.length();
		String earliestString = null;
		for (String s : strings){
			int matchIndex = sql.indexOf( s );
			if (matchIndex != -1){
				if (matchIndex < earliestIndex){
					earliestString = s;
				}
			}
		}
		return earliestString;
	}

	public abstract String consume( String sql ) throws SyntaxException;
	public abstract String getPurpose();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}

}