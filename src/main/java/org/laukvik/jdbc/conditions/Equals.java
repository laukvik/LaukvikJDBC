package org.laukvik.jdbc.conditions;

import org.laukvik.jdbc.Column;
import org.laukvik.jdbc.Condition;
import org.laukvik.jdbc.Value;
import org.laukvik.jdbc.data.ColumnData;
import org.laukvik.jdbc.type.SmallInt;

public class Equals extends Condition{
	
	Column column, anotherColumn;
	Value value;
	
	private static int COLUMN_COMPARISON = 1;
	private static int VALUE_COMPARISON = 2;
	int type;
	
	public Equals( Column column, Value value ){
		this.column = column;
		this.value = value;
		this.type = VALUE_COMPARISON;
	}

	public Equals( Column column, Column anotherColumn ){
		this.column = column;
		this.anotherColumn = anotherColumn;
		this.type = COLUMN_COMPARISON;
	}
	
	public String toString(){
		if (type == VALUE_COMPARISON){
			if (value instanceof SmallInt){
				return "" + column + " = " + value + "";
			} else {
				return "" + column + " = '" + value + "'";
			}
		} else {
			return column + " = " + anotherColumn;
		}
	}

	public boolean accepts( ColumnData data, String [] values ) {
		String lval = values[ data.indexOf( column ) ];
		if (type == VALUE_COMPARISON){
			System.out.println( lval + "=" + value );
			if (lval == null){
				return false;
			}
			return lval.equalsIgnoreCase( value.toString() );
		} else {
			String rval = values[ data.indexOf( anotherColumn ) ];
			return lval.equalsIgnoreCase( rval );
		}
	}

}