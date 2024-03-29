package org.laukvik.jdbc.syntax;

import org.laukvik.jdbc.Column;
import org.laukvik.jdbc.ParseException;
import org.laukvik.jdbc.joins.LeftJoin;

public class LeftOuterJoinReader extends GroupReader {

	String table, left, right;
	
	public LeftOuterJoinReader() {

		addEither( new TextReader("LEFT OUTER JOIN"),  new TextReader("LEFT JOIN") );
		addEmpty();
		add( new WordReader() ).addReaderListener( new ReaderListener(){
			public void found(String values) {
				table = values;
			}}  );
		addEmpty();
		add( new TextReader("ON") );
		addEmpty();
		add( new ColumnReader() ).addReaderListener( new ReaderListener(){
			public void found(String values) {
				left = values;
			}}  );
		add( new TextReader("=") );
		add( new ColumnReader() ).addReaderListener( new ReaderListener(){
			public void found(String values) {
				right = values;
			}}  );
	}
	
	public LeftJoin getJoin() throws ParseException{
		return new LeftJoin( Column.parse(left), Column.parse(right) );
	}
	
	

}