package org.laukvik.jdbc.syntax;

import org.laukvik.jdbc.Column;

public interface ColumnListener {

	public void found( Column column );
	
}