package org.laukvik.jdbc.conditions;

import org.laukvik.jdbc.Column;
import org.laukvik.jdbc.Condition;
import org.laukvik.jdbc.Value;
import org.laukvik.jdbc.data.ColumnData;

public class Greater extends Condition{
	
	Column column; 
	Value value;
	
	public Greater( Column column, Value value ){
		super();
		this.column = column;
		this.value = value;
	}
	
	public String toString(){
		return column + " > " + value;
	}

	public boolean accepts( ColumnData data, String [] values ) {
		return false;
	}

}