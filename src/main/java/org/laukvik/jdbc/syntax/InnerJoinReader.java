package org.laukvik.jdbc.syntax;

import org.laukvik.jdbc.Column;
import org.laukvik.jdbc.ParseException;
import org.laukvik.jdbc.joins.InnerJoin;

public class InnerJoinReader extends JoinReader {

	String table, left, right;
	
	public InnerJoinReader() {

		add( new TextReader("INNER JOIN") );
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
	
	public InnerJoin getInnerJoin() throws ParseException{
		return new InnerJoin( Column.parse(left), Column.parse(right) );
	}
	
	public String consume(String sql) throws SyntaxException {
		try{
			String sql2 = super.consume(sql);
			fireJoinFound( getInnerJoin() );
			return sql2;
		} catch(Exception e){
			throw new SyntaxException( e.getMessage() );
		}
	}
	
	

}