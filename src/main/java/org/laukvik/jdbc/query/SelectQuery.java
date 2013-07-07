package org.laukvik.jdbc.query;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Vector;

import org.laukvik.jdbc.Column;
import org.laukvik.jdbc.Condition;
import org.laukvik.jdbc.Join;
import org.laukvik.jdbc.Manager;
import org.laukvik.jdbc.Sort;
import org.laukvik.jdbc.Table;
import org.laukvik.jdbc.data.ColumnData;
import org.laukvik.jdbc.data.Data;
import org.laukvik.jdbc.joins.LeftJoin;
import org.laukvik.jdbc.joins.OuterJoin;

/**
 * A class to programatically create SQL queries
 * 
 * 
 * @author Morten
 *
 */
public class SelectQuery {

	public Table table;
	public Vector<Column> columns;
	public Vector<Join> joins;
	public Vector<Column> groupBys;
	public Vector<Condition> conditions;
	public Vector<Sort> sorts;
	public int limit;
	public int offset;
	String sql;

	public SelectQuery(){
		columns = new Vector<Column>();
		joins = new Vector<Join>();
		groupBys = new Vector<Column>();
		conditions = new Vector<Condition>();
		sorts = new Vector<Sort>();
	}
	
	public void setOffset(int offset) {
		if (offset < 0){
			throw new IllegalArgumentException("Offset cant be a negative number");
		}
		this.offset = offset;
	}
	
	public void setTable( Table table ){
		this.table = table;
	}
	
	public Table getTable() {
		return table;
	}
	
	public void addJoin( Join join ){
		joins.add( join );
	}
	
	public Vector<Join> getJoins() {
		return joins;
	}
	
	public void addColumn( Column column ){
		columns.add( column );
	}
	
	public void setCondition( Condition condition ){
		conditions.removeAllElements();
		conditions.add( condition );
	}
	
	public void addSort( Sort sort ){
		sorts.add( sort );
	}
	
	public void addGroupBy( Column column ){
		groupBys.add( column );
	}
	
	public void setLimit( int limit ){
		if (limit < 1){
			throw new IllegalArgumentException( "Limit must be 1 or greater!" );
		}
		this.limit = limit;
	}

	public String toString(){
		return toSQL();
	}
	
	/**
	 * Returns the query as it would by SQL standards.
	 * 
	 */
	public String toSQL(){
		StringBuffer buffer = new StringBuffer();
		
		buffer.append( "SELECT " );

		
		for (int x=0; x<columns.size(); x++){
			buffer.append( (x>0 ? "," : "") + "\n  " + columns.get(x)  );
		}
		
		buffer.append( "\nFROM " );
	
		buffer.append( "\n  " + table );
		
		
		for(Join j : joins){
			buffer.append( "\n" +  j );
		}
		
		if (conditions.size() > 0){
			buffer.append( "\nWHERE " );
			for (Condition c : conditions){
				buffer.append( "\n  " + c );
			}	
		}
		
		buffer.append( "\nORDER BY " );
		for (Sort s : sorts){
			buffer.append( "\n  " + s );
		}
		
		
		if(offset > 0){
			buffer.append( "\nOFFSET " + offset );
		}
		
		if(limit > 0){
			buffer.append( "\nLIMIT " + limit );
		}
		
		return buffer.toString();
	}
	

	
	public ColumnData createData() throws SQLException{

		Manager mgr = new Manager();
		ColumnData main = mgr.getColumnData( table );
		
		for (Join j : joins){
			main = j.join( main, mgr.getColumnData( j.right.getTable() ) );
		}
		
		Data d = new Data( main.listColumns() );
		
		for (int y=offset; y<main.getRowCount(); y++){
			
			boolean allConditionsAccepted = true;
			for (Condition con : conditions){
				if (!con.accepts( main, main.getRow( y ) )){
					allConditionsAccepted = false;
				}
			}
			if (allConditionsAccepted){
				if (limit == 0 || d.getRowCount() < limit){
					d.add( main.getRow(y) );
				}
			}
		}
		
		/* Sorter her */

		return d;
	}
	
	public static void main( String [] args ) throws IOException, SQLException{
	
		Table employee = new Table("Employee");
		employee.addColumn( "email" );
		Column departmentID = employee.addColumn( "departmentID" );

		
		Table department = new Table("Department");
		Column departmentDepartmentID = department.addColumn("departmentID");
		Column departmentCompanyID = department.addColumn("companyID");
		
		Table company = new Table("Company");
		Column companyID = company.addColumn( new Column("companyID") );

		/* Create a new query */
		SelectQuery q = new SelectQuery();
//		q.addColumn( employee.ALL );
		q.addColumn( department.ALL );
		q.addColumn( Column.ALL );
		q.setTable( employee );
		q.addJoin( new LeftJoin( departmentID, departmentDepartmentID ) );
		q.addJoin( new OuterJoin( departmentCompanyID, companyID ) );
		
		/* Example using departmendID=1 */
//		q.setCondition( new Equals( departmentDepartmentID, new SmallInt(33) ) );
	
		/* Example using lastName='Laukvik' */
//		q.setCondition( new Equals( lastName, new VarChar("Jones") ) );		
		
		/* Example using IN ('Laukvik','Østensen','Pedersen') */
//		q.setCondition( new In( lastName, new VarChar("Steinberg"), new VarChar("Rafferty"), new VarChar("Jones") ) );
		
		/* Example using departmentID=employeeID */
//		q.setCondition( new Equals( departmentID, departmentDepartmentID ) );
		
		/* Example using departmentID > 12 */
//		q.setCondition( new Greater( departmentID, new SmallInt(12) ) );
		
		/* Example using lastName='Laukvik' or lastName='Pedersen' */
//		q.setCondition( new Or( new Equals( lastName, new VarChar("Laukvik") ), new Equals( lastName, new VarChar("Pedersen") )  ) );
		


		q.addSort( new Sort( departmentID , Sort.ASCENDING ) );
//		q.setOffset( -5 );
		q.setLimit( 2 );
		
		

//		new ResultSetViewer( new ColumnDataModel( q.createData() ), q.toSQL() );
		
	
	}

}