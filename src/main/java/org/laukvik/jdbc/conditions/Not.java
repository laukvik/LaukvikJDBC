package org.laukvik.jdbc.conditions;

import org.laukvik.jdbc.Condition;
import org.laukvik.jdbc.data.ColumnData;

public class Not extends Condition {

	Condition conditionA;
	
	public Not(Condition conditionA ) {
		this.conditionA = conditionA;
	}
	
	public boolean accepts(  ColumnData data, String [] values ) {
		return false;
	}
	
	public String toString(){
		return "NOT (" + conditionA + ")";
	}

}
