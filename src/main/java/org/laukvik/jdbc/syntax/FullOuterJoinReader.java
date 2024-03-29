package org.laukvik.jdbc.syntax;

import org.laukvik.jdbc.Column;
import org.laukvik.jdbc.ParseException;
import org.laukvik.jdbc.joins.OuterJoin;

public class FullOuterJoinReader extends GroupReader {

	String table, left, right;
	
	public FullOuterJoinReader() {

		addEither( new TextReader("FULL OUTER JOIN"),  new TextReader("OUTER JOIN") );
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
	
	public OuterJoin getJoin() throws ParseException{
		return new OuterJoin( Column.parse(left), Column.parse(right) );
	}
	
	

}