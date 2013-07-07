package org.laukvik.jdbc.conditions;

import org.laukvik.jdbc.Condition;
import org.laukvik.jdbc.data.ColumnData;

public class And extends Condition {

	Condition conditionA;
	Condition conditionB;
	
	public And(Condition conditionA, Condition conditionB) {
		this.conditionA = conditionA;
		this.conditionB = conditionB;
	}
	
	public boolean accepts(  ColumnData data, String [] values ) {
		return false;
	}
	
	public String toString(){
		return "(" + conditionA + " AND " + conditionB + ")";
	}

}
