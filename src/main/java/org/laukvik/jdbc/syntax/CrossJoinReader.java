package org.laukvik.jdbc.syntax;

import org.laukvik.jdbc.ParseException;
import org.laukvik.jdbc.Table;
import org.laukvik.jdbc.joins.CrossJoin;

public class CrossJoinReader extends JoinReader {

	String table;
	
	public CrossJoinReader() {
		add( new TextReader("CROSS JOIN") );
		addEmpty();
		add( new WordReader() ).addReaderListener( new ReaderListener(){
			public void found(String values) {
				table = values;
			}}  );
	}
	
	public CrossJoin getJoin() throws ParseException{
		return new CrossJoin( null, Table.parse( table ) );
	}

}