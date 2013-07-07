/*
 *
 * Copyright (c) 2006 Morgans AS.
 * Munkedamsveien 53b, 0250 Oslo, Norway
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Morgans
 * AS. ("Confidential Information"). You shall not disclose such Confidential 
 * Information and shall use it only in accordance with the terms of the license 
 * agreement you entered into with Morgans.
 * 
 */
package org.laukvik.jdbc.syntax;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Pattern p = Pattern.compile( "\\(([0-9]*)/([0-9]*)/([0-9]*)\\)" );
		Matcher m = p.matcher( "(05/8/1) Reise (125125)" );
		m.find();
		
		System.out.println( m.group( 1 ) );
		System.out.println( m.group( 2 ) );
		System.out.println( m.group( 3 ) );
		
	}

}