package com.louddt.tag.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 *
 */
public class DBUtil {

	public static final String DB_TYPE_MYSQL = "mysql";
	public static final String DB_TYPE_ORACLE = "oracle";
	public static final String DB_TYPE_PGSQL = "postgresql";
	public static final String DB_TYPE_MSSQL = "sqlserver";

	private DBUtil(){}
	
	/**
	 * 获取数据库连接
	 * @param url
	 * @param userName
	 * @param password
	 * @return
	 */
	public static Connection getConnection(String driverClass,String url,String userName,String password){
		try{
			Class.forName(driverClass).newInstance();
			Connection connection = (Connection) DriverManager.getConnection(url, userName, password);
			return connection;
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 获取oracle的数据库连接
	 * @param ip 数据库ip地址
	 * @param port 端口
	 * @param instance 实例名称
	 * @param userName 用户名
	 * @param password 用户密码
	 * @return
	 */
	public static Connection getOracleConnection(String ip,int port,String instance,String userName,String password){
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver"); 
			String url = "jdbc:oracle:thin:@"+ip+":"+port+":"+instance;
			Connection conn = DriverManager.getConnection(url, userName, password); 
			return conn;
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * 获取sqlserver的数据库连接
	 * @param ip 数据库ip
	 * @param port 数据库端口
	 * @param databaseName 数据库名称
	 * @param userName 用户名
	 * @param password 用户密码
	 * @return
	 */
	public static Connection getSqlServerConnect(String ip,int port,String databaseName,String userName,String password){
		try{
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			String url = "";
			if(StringUtil.isEmptyOrNull(databaseName)){
				url = "jdbc:sqlserver://"+ip+":"+port+";DatabaseName=" + databaseName;
			}else{
				url = "jdbc:sqlserver://"+ip+":"+port+";";
			}
			Connection conn = DriverManager.getConnection(url, userName, password); 
			return conn;
		}catch(Exception e){
			System.out.println(e.getMessage()+"");
			return null;
		}
	}
	
	/**
	 * 获取mysql的数据库连接
	 * @param ip 数据库ip
	 * @param port 数据库端口
	 * @param databaseName 数据库名称
	 * @param userName 数据库用户名
	 * @param password 数据库密码
	 * @return
	 */
	public static Connection getMySqlConnection(String ip,int port,String databaseName,String userName,String password){
		try{
			String driverClass = "com.mysql.jdbc.Driver";
			String url = "";
			if(StringUtil.isEmptyOrNull(databaseName)){
				url = "jdbc:mysql://"+ip+":"+port+"/?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&useSSL=false&oldsyntax=true";
			}else{
				url = "jdbc:mysql://"+ip+":"+port+"/"+databaseName+"?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&useSSL=false&oldsyntax=true";
			}
			Connection conn = DBUtil.getConnection(driverClass,url, userName, password);
			return conn;
		}catch(Exception e){
			return null;
		}
	}

	public static Connection getPostgreSqlConnection(String ip, Integer port, String databaseName, String usename, String pwd) {
		try{
			Class.forName("org.postgresql.Driver");
			String url = "";
			if(StringUtil.isEmptyOrNull(databaseName)){
				url = "jdbc:postgresql://"+ip+":"+port+"/";
			}else{
				url = "jdbc:postgresql://"+ip+":"+port+"/"+databaseName;
			}
			Connection conn = DriverManager.getConnection(url, usename, pwd);
			return conn;
		}catch(Exception e){
			return null;
		}
	}

	/**
	 * 关闭数据库资源
	 * @param conn
	 * @param ps
	 * @param rs
	 */
	public static void close(Connection conn,PreparedStatement ps,ResultSet rs){
		closeResultSet(rs);
		closePreparedStatement(ps);
		closeConn(conn);
	}
	
	/**
	 * 关闭数据库的资源
	 * @param conn
	 * @param st
	 * @param rs
	 */
	public static void close(Connection conn,Statement st,ResultSet rs){
		closeResultSet(rs);
		closeStatement(st);
		closeConn(conn);
	}
	
	/**
	 * 关闭数据库链接
	 * @param conn
	 */
	public static void closeConn(Connection conn){
		try{
			if(conn != null){
				conn.close();
			}
		}catch(Exception e){}
	}
	
	/**
	 * 关闭Statement
	 * @param st
	 */
	public static void closeStatement(Statement st){
		try{
			if(st != null){
				st.close();
			}
		}catch(Exception e){}
	}
	
	/**
	 * 关闭PreparedStatement
	 * @param ps
	 */
	public static void closePreparedStatement(PreparedStatement ps){
		try{
			if(ps != null){
				ps.close();
			}
		}catch(Exception e){}
	}
	
	/**
	 * 关闭ResultSet
	 * @param rs
	 */
	public static void closeResultSet(ResultSet rs){
		try{
			if(rs != null){
				rs.close();
			}
		}catch(Exception e){}
	}

	/**
	 * 获取远程关系型数据库中可用的数据库名列表
	 */
	public static List<String> queryDatabaseNames(Connection con) throws SQLException {
		List<String> remoteDatabaseNames = new ArrayList<>();
		DatabaseMetaData meta = con.getMetaData();
		try (ResultSet resultSet = meta.getCatalogs()) {
			while (resultSet.next()) {
				String db = resultSet.getString("TABLE_CAT");
				remoteDatabaseNames.add(db);
			}
		}
		return remoteDatabaseNames;
	}

	/**
	 * 获取远程关系型数据库包含的数据表列表
	 */
	public static List<String> queryTableNames(Connection con) throws SQLException {
		List<String> remoteTableNames = new ArrayList<>();
		DatabaseMetaData meta = con.getMetaData();
		try (ResultSet rs = meta.getTables(null, null, "%", null)) {
			while (rs.next()) {
				String tableName = rs.getString(3);
				remoteTableNames.add(tableName);
			}
		}
		return remoteTableNames;
	}

	public static List<String> queryColumnNames(String dbType, Connection con, String tableName) throws SQLException {
		List<String> columnNames = new ArrayList<>();

		Statement stmt = con.createStatement();
		String sql;
		if(DB_TYPE_MYSQL.equals(dbType)) {
			sql = String.format("select * from %s limit 1", tableName);
		}else if(DB_TYPE_MSSQL.equals(dbType)){
			sql = String.format("select top 1 * from %s", tableName);
		}else if(DB_TYPE_ORACLE.equals(dbType)){
			sql = String.format("select * from %s where rownum = 1",tableName);
		}else {
			return new ArrayList<String>();
		}
		try (ResultSet rs = stmt.executeQuery(sql)) {
			ResultSetMetaData meta = rs.getMetaData();
			for (int i = 1; i <= meta.getColumnCount(); i++) {
				String colName = meta.getColumnName(i);
				columnNames.add(colName);
			}
		}
		return columnNames;
	}

	public static String executeSQL(List<String> sqlCols,String tableName){
		StringBuilder sqlStr = new StringBuilder("insert into ");
		sqlStr.append(tableName).append("(");
		boolean colFlag = false;

		StringBuilder askStr = new StringBuilder();
		for (String sqlCol : sqlCols) {
			if(colFlag){
				sqlStr.append(SymbolConstants.COMMA);
				askStr.append(SymbolConstants.COMMA);
			}
			colFlag = true;
			sqlStr.append(sqlCol);
			askStr.append(SymbolConstants.QUESTION);
		}
		sqlStr.append(") values (").append(askStr).append(")");
		return sqlStr.toString();
	}
}
