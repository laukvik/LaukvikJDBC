package org.laukvik.jdbc;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;


public class TextDriver implements java.sql.Driver {
	

	private static final String URL_PREFIX = "jdbc:TextDriver";

	static
	{
		try{
			DriverManager.registerDriver( new TextDriver() );
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void log( Object message ){
//		System.out.println( this.getClass().getName() + ":\t" + message.toString() );
	}
	
	public boolean acceptsURL( String url ) throws SQLException {
		log( "acceptsURL=" + url );
		if (url.startsWith( URL_PREFIX )){
			String folder = url.substring( url.lastIndexOf(":")+1  );
			File home = new File( folder );
			
			if (home.exists() && home.isDirectory()){
				log("Ok");
				return true;
			} else {
				log("Not exist or not a folder");
				throw new SQLException("Folder does not exist: " + folder );
			}
		} else {
			log("Dont accept");
			return false;
		}
	}

	public Connection connect( String url, Properties info) throws SQLException {
		if (acceptsURL(url)){
			return new TextConnection( url, info );	
		}
		return null;
	}

	public int getMajorVersion() {
		return 1;
	}

	public int getMinorVersion() {
		return 0;
	}

	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		return new DriverPropertyInfo[0];
	}

	public boolean jdbcCompliant() {
		return false;
	}
	

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
//		Class.forName("org.laukvik.jdbc.TextDriver");
//		Connection connection = DriverManager.getConnection( "jdbc:TextDriver:/Users/morten/Projects/Research/src/org/laukvik/jdbc" );
////		Connection connection = DriverManager.getConnection( "jdbc:TextDriver:/Users/morten/Desktop" );
//
//		String sql = "SELECT Employee.LastName FROM Employee"; 
//		
//		Statement st = null;
//		ResultSet rs = null;
//		try {
//			st = connection.createStatement();
//			rs = st.executeQuery( sql );
//
//		} catch (SQLException e){
//			e.printStackTrace();
//		} finally {
//			try{ rs.close(); } catch(Exception e){}
//			try{ st.close(); } catch(Exception e){}
//		}
//		
//		new ResultSetViewer( new ResultSetTableModel(rs), sql );

	}

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}