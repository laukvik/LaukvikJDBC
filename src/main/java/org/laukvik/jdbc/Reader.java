package org.laukvik.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.laukvik.csv.CSVListener;
import org.laukvik.csv.CSVReader;
import org.laukvik.jdbc.data.ColumnData;
import org.laukvik.jdbc.data.Data;

public class Reader extends CSVReader implements CSVListener {
	
	File file; 
	String tableName;
	Table table;
	Data data;
	
	public Reader( File file, String tableName ) throws FileNotFoundException, IOException {
		super( COMMA );
		this.file = file;
		this.tableName = tableName;
		addCSVListener( this );
		this.table = new Table( tableName );
		this.data = new Data();
		setHasHeaders( true );
		read( new FileInputStream( file ) );
	}
	
	public ColumnData getColumnData() throws IOException{
//		System.out.println( "Rows: "  + data.getColumnCount() + "x"+ data.getRowCount() );
		return data;
	}

	public void foundRow(int rowIndex, String[] values) {
//		System.out.println( "Found row: " + values[ 0 ] );
		data.add( values );
	}


	public void foundHeaders( String[] headers) {
//		System.out.println( "Found headers: " + headers[ 0 ] );
		
		for (String header : headers){
			table.addColumn( header );
		}
		this.data = new Data( table.listColumns() );
	}

	public static void main(String[] args) {

	}

}