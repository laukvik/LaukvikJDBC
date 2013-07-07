package org.laukvik.jdbc.syntax;

import java.util.Vector;

import org.laukvik.jdbc.Join;

public class MultipleJoinReader extends GroupReader implements JoinReaderListener {
	
	CrossJoinReader cross;
	InnerJoinReader inner;
	LeftOuterJoinReader left;
	RightOuterJoinReader right;
	NaturalJoinReader natural;
	FullOuterJoinReader outer;
	
	Vector<JoinReaderListener> listeners;
	
	public MultipleJoinReader(){
		listeners = new Vector<JoinReaderListener>();

		cross = new CrossJoinReader();
		natural = new NaturalJoinReader();
		inner = new InnerJoinReader();
		left = new LeftOuterJoinReader();
		right = new RightOuterJoinReader();
		outer = new FullOuterJoinReader();
		
		cross.addJoinReaderListener( this );
		
		add( new Either( cross, inner, left, right, outer, natural  ) );
	}

	public void found(Join join) {
		fireTableFound( join );
	}
	
	public String getPurpose() {
		return "Consumes all joins in the SQL";
	}
	
	public JoinReaderListener addJoinListener( JoinReaderListener listener ){
		listeners.add( listener );
		return listener;
	}
	
	public void removeJoinListener( JoinReaderListener listener ){
		listeners.remove( listener );
	}
	
	public void fireTableFound( Join join ){
		for (JoinReaderListener l : listeners){
			l.found( join );
		}
	}
	
	public static void main( String[] args) {
		MultipleJoinReader r = new MultipleJoinReader();
		try {
			r.addJoinListener( new JoinReaderListener(){

				public void found(Join join) {
					System.out.println( "Found join: " + join );
				}});
			r.consume( "NATURAL JOIN Department RIGHT OUTER JOIN Department ON Employee.departmendID=Department.departmentID"  );
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}