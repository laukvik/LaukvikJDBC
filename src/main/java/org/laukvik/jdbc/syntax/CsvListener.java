package org.laukvik.jdbc.syntax;

public interface CsvListener {

	public void rowFound( int rowIndex, String [] values );
	public void headersFound( String [] values );
	
}