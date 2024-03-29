package org.laukvik.jdbc.data;

import java.util.Vector;

import org.laukvik.jdbc.Column;
import org.laukvik.jdbc.Value;

public class Data implements ColumnData{

	Column [] columns;
	Value [] columnClasses;
	Vector<String[]> rows;
	
	public Data( Column [] left, Column [] right ){
		columns = new Column[ left.length + right.length ];
		for (int x=0; x<left.length; x++){
			columns[ x ] = left[ x ];
		}
		for (int x=0; x<right.length; x++){
			columns[ x + left.length ] = right[ x ];
		}
		columnClasses = new Value[ columns.length ];
		this.rows = new Vector<String[]>();	
	}
	
	public Data( Column [] columns ){
		this.columns = columns;
		columnClasses = new Value[ columns.length ];
		this.rows = new Vector<String[]>();	
	}

	public Data() {
		columns = new Column[ 0 ];
		columnClasses = new Value[ 0 ];
		this.rows = new Vector<String[]>();	
	}

	public void add( String [] row ){
		this.rows.add( row );
	}

	public Column getColumn(int columnIndex) {
		return columns[ columnIndex ];
	}

	public int getColumnCount() {
//		System.out.println( columns.length + "" );
		return columns.length;
	}

	public String[] getRow(int rowIndex) {
		return rows.get( rowIndex );
	}

	public int getRowCount() {
		return rows.size();
	}

	public String getValue(int columnIndex, int rowIndex) {
//		if (columnIndex < 0){
//			return null;
//		}
		if (rowIndex >= rows.size()){
			return null;
		}
		String [] r = rows.get( rowIndex );
		
		if (columnIndex > r.length-1){
			return null;
		}
		
		return r[ columnIndex ];
	}

	public String getValue(Column column, int rowIndex) {
		return getValue( indexOf(column), rowIndex  );
	}

	public Column[] listColumns() {
		return columns;
	}

	public int indexOf(Column column) {
		for (int x=0; x<columns.length; x++){
			if (columns[x].getTable().getName().equalsIgnoreCase( column.getTable().getName() )){
				if (columns[x].getName().equalsIgnoreCase( column.getName() )){
					return x;
				}
			}
		}
		return -1;
	}

	public void add( String[] left, String[] right ) {
		String [] cols = new String[ left.length + right.length ];
		
		for (int x=0; x<left.length; x++){
			cols[ x ] = left[ x ];
		}
		for (int x=0; x<right.length; x++){
			cols[ x + left.length ] = right[ x ];
		}
		add( cols );
	}

	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}
	
}