package org.laukvik.jdbc.syntax;

/**
 * 
 * 
 * @author morten
 *
 */
public class ArrayReader extends GroupReader {

	public ArrayReader( String... items ){
		for (String s : items){
			addOptional( new TextReader( s ) );
		}
	}

}
