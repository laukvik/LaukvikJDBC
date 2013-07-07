package org.laukvik.jdbc.conditions;

import org.laukvik.jdbc.Condition;
import org.laukvik.jdbc.data.ColumnData;

public class Or extends Condition{

	Condition conditionA;
	Condition conditionB;
	
	public Or(Condition conditionA, Condition conditionB) {
		this.conditionA = conditionA;
		this.conditionB = conditionB;
	}

//	public boolean accepts(ResultSetRow row) {
//		return conditionA.accepts(row) || conditionB.accepts(row);
//	}
	
	public boolean accepts( ColumnData data, String [] values ) {
		return false;
	}
	
	public String toString(){
		return "(" + conditionA + " OR " + conditionB + ")";
	}

}
