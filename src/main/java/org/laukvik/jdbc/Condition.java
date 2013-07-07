package org.laukvik.jdbc;

import org.laukvik.jdbc.data.ColumnData;

public abstract class Condition {

	
	public Condition(){
	}

	public abstract boolean accepts( ColumnData data, String [] values  );
	
}