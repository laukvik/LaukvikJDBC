package org.laukvik.jdbc;

import java.util.Vector;

import org.laukvik.csv.CSV;

public class Table {
	
	public static final Table EVERYTHING = new Table("*");

	String name;
	Vector<Column> columns;
	public Column ALL;
	public CSV csv;
	
	public Table( String name ){
		this.name = name.trim();
		columns = new Vector<Column>();
		ALL = new Column("*", this);
	}
	
	public static Table parse( String sql ) throws ParseException{
		return new Table( sql );
	}

	
	public String getName() {
		return name;
	}
	
	public String toString(){
		return name;
	}

	public Column addColumn( String column ) {
		return addColumn( new Column( column ));
	}
	
	public Column addColumn( Column column ){
		column.setTable( this );
		columns.add( column );
		return column;
	}

	
	/**
	 * Lists the columns foudn in database
	 * 
	 * @return
	 */
	public Column [] listRealColumns(){

		Vector<Column> items = new Vector<Column>();
		
		for(int x=0; x<csv.getColumnCount(); x++){
			items.add( new Column( csv.getHeader( x ), this  ) );
		}
		
		Column [] arr = new Column[ items.size() ];
		items.toArray( arr );
		
		return arr;
	}
	
	/**
	 * Lists the columns found in query
	 * 
	 * @return
	 */
	public Column [] listColumns(){

		Vector<Column> items = new Vector<Column>();
		for(Column c : columns){
			if (!c.isAll()){
				items.add( c );
			}
		}
		
		Column [] arr = new Column[ items.size() ];
		items.toArray( arr );
		
		return arr;
	}

}