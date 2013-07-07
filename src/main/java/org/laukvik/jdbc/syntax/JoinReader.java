package org.laukvik.jdbc.syntax;

import java.util.Vector;

import org.laukvik.jdbc.Join;

public class JoinReader extends GroupReader {

	Vector<JoinReaderListener> listeners;
	
	public JoinReader(){
		listeners = new Vector<JoinReaderListener>();
	}
	
	public void addJoinReaderListener( JoinReaderListener listener ){
		listeners.add( listener );
	}
	
	public void removeJoinReaderListener( JoinReaderListener listener ){
		listeners.remove( listener );
	}
	
	public void fireJoinFound( Join join ){
		for (JoinReaderListener l : listeners){
			l.found( join );
		}
	}
	
}