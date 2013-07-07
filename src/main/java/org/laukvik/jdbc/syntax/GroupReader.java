package org.laukvik.jdbc.syntax;

import java.util.Vector;

public class GroupReader extends Reader {
	
	Vector<Reader> readers;
	
	
	public GroupReader(){
		this.readers = new Vector<Reader>();
	}
	
	public Reader add( Reader reader ){
		this.readers.add( reader );
		return reader;
	}
	
	public Reader getReader( int index ){
		return readers.get( index );
	}
	
	public Reader addOptional( Reader reader ){
		reader.setRequired( false );
		this.readers.add( reader );
		return reader;
	}
	
	public void addOptional( String text ){
		TextReader r = new TextReader( text );
		r.setRequired( false );
		this.readers.add( r );
	}
	
	public Reader addEmpty() {
		return add( new EmptyReader() );
	}

	public void addEither( Reader... readers ) {
		add( new Either( readers ) );
	}
	
	public void add( String text ) {
		add( new TextReader( text ) );
	}
	
	/**
	 * Usss
	 * 
	 * @param left
	 * @param right
	 * @param reader
	 */
	public void addBetween(String left, Reader reader, String right) {
		add( new Between( left, right, reader  ) );
	}


	public String consume( String sql ) throws SyntaxException {
		int max = readers.size();
		/* Iterate through all readers */
		for (int x=0; x<max; x++){
			Reader r = readers.get( x );
//			log( r.getClass().getSimpleName() + "\t" + sql );
			
			if (r.isRequired()){
				/* Required readers */ 
				sql = r.consume( sql );
				
			} else {
				/* Optional readers */
				if (sql.length() == 0){
					x = readers.size();
				} else {
					try {
						sql = r.consume( sql );
					} catch (Exception e) {
//						e.printStackTrace();
					}
				}
			}
			
//			System.out.println( "\t\tafter: " + sql );
		}
		return sql;
	}

	public String getPurpose(){
		return "Consume text for multiple readers";
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			GroupReader group = new GroupReader();
			group.add( new TextReader( "SELECT" ) );
			group.addEmpty();
			group.add( new ListReader( new ColumnReader()  ) );
			group.addEmpty();
			group.add( new TextReader( "FROM" ) );
			group.addEmpty();
			group.add( new TableReader() );
			
			GroupReader joins = new GroupReader();
			joins.addEmpty();
			joins.add( new MultipleJoinReader() );
			group.addOptional( joins );
	
			GroupReader where = new GroupReader();
			where.addEmpty();
			where.add( new TextReader( "WHERE" ) );
			where.addEmpty();
			where.add( new ListReader( new ConditionReader(), new ArrayReader( "AND"  )  ) );
			group.addOptional( where );
			
			System.out.println( group.consume( "SELECT	 email, first,last     FROM Employee INNER JOIN Employee ON Employee.customerID=Customer.customerID WHERE customerID>5 OR email=morten AND employeeID=12 AND first=Janne OR lastName=Laukvik" ) );
			
		} catch (SyntaxException e) {
			e.printStackTrace();
		}
	}


}