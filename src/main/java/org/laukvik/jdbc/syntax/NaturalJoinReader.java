package org.laukvik.jdbc.syntax;

import org.laukvik.jdbc.ParseException;
import org.laukvik.jdbc.Table;
import org.laukvik.jdbc.joins.NaturalJoin;

public class NaturalJoinReader extends GroupReader {

	String table;
	
	public NaturalJoinReader(){
		add( new TextReader("NATURAL JOIN") );
		addEmpty();
		add( new WordReader() ).addReaderListener( new ReaderListener(){
			public void found(String values) {
				table = values;
			}}  
		);
	}
	
	public NaturalJoin getJoin() throws ParseException{
		return new NaturalJoin( null, Table.parse( table ) );
	}

}