package org.laukvik.jdbc.syntax;

public class WhereReader extends GroupReader {

	public WhereReader(){
		add( new TextReader( "WHERE" ) );
		addEmpty();
		ConditionReader wr = new ConditionReader();
		
		add( new ListReader( wr, new ConditionSeperator()  ) );
	}
	
	public static void main(String[] args) {
		try {
			String sql = "employeeID<>1 AND firstName>5";
			WhereReader r = new WhereReader();
			System.out.println( r.consume(sql) );
		} catch (SyntaxException e) {
			e.printStackTrace();
		}
	}
	
}