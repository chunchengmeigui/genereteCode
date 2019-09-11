package com.dabao.databaseUtil.entity;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataConnect {
	private static String DRIVER;
	private static String URL;
	private static String USERNAME;
	private static String PASSWORD;

	private static final String SQL = "SELECT * FROM ";// 数据库操作

	static {
//    	System.out.println("我是静态代码块");
		
	}

	{
//    	 System.out.println("我是构造代码块！");
		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println("can not load jdbc driver" + e);
		}
	}

	/**
	 * 获取数据库连接
	 *
	 * @return
	 */
	public static Connection getConnection() {
		Connection conn = null;
		try {
			try {
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			System.out.println("get connection failure" + e);
		}
		return conn;
	}

	/**
	 * 关闭数据库连接
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				System.out.println("close connection failure" + e);
			}
		}
	}

	/**
	 * 获取数据库下的所有表名
	 */
	public static List<String> getTableNames() {
		List<String> tableNames = new ArrayList<String>();
		Connection conn = getConnection();
		ResultSet rs = null;
		try {
			// 获取数据库的元数据
			DatabaseMetaData db = conn.getMetaData();
			// 从元数据中获取到所有的表名
			rs = db.getTables(null, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				tableNames.add(rs.getString(3));
			}
		} catch (SQLException e) {
			System.out.println("getTableNames failure" + e);
		} finally {
			try {
				rs.close();
				closeConnection(conn);
			} catch (SQLException e) {
				System.out.println("close ResultSet failure" + e);
			}
		}
		return tableNames;
	}

	/**
	 * 获取表中所有字段名称
	 * 
	 * @param tableName 表名
	 * @return
	 */
	public static List<String> getColumnNames(String tableName) {
		List<String> columnNames = new ArrayList<String>();
		// 与数据库的连接
		Connection conn = getConnection();
		PreparedStatement pStemt = null;
		String tableSql = SQL + tableName;
		try {
			pStemt = conn.prepareStatement(tableSql);
			// 结果集元数据
			ResultSetMetaData rsmd = pStemt.getMetaData();
			// 表列数
			int size = rsmd.getColumnCount();
			for (int i = 0; i < size; i++) {
				columnNames.add(rsmd.getColumnName(i + 1));
			}
		} catch (SQLException e) {
			System.out.println("getColumnNames failure" + e);
		} finally {
			if (pStemt != null) {
				try {
					pStemt.close();
					closeConnection(conn);
				} catch (SQLException e) {
					System.out.println("getColumnNames close pstem and connection failure" + e);
				}
			}
		}
		return columnNames;
	}

	/**
	 * 获取表中所有字段类型
	 * 
	 * @param tableName
	 * @return
	 */
	public static List<String> getColumnTypes(String tableName) {
		List<String> columnTypes = new ArrayList<String>();
		// 与数据库的连接
		Connection conn = getConnection();
		PreparedStatement pStemt = null;
		String tableSql = SQL + tableName;
		try {
			pStemt = conn.prepareStatement(tableSql);
			// 结果集元数据
			ResultSetMetaData rsmd = pStemt.getMetaData();
			// 表列数
			int size = rsmd.getColumnCount();
			for (int i = 0; i < size; i++) {
				columnTypes.add(rsmd.getColumnTypeName(i + 1));
			}
		} catch (SQLException e) {
			System.out.println("getColumnTypes failure" + e);
		} finally {
			if (pStemt != null) {
				try {
					pStemt.close();
					closeConnection(conn);
				} catch (SQLException e) {
					System.out.println("getColumnTypes close pstem and connection failure" + e);
				}
			}
		}
		return columnTypes;
	}

	/**
	 * 获取表中字段的所有注释
	 * 
	 * @param tableName
	 * @return
	 */
	public static List<String> getColumnComments(String tableName) {
		List<String> columnTypes = new ArrayList<String>();
		// 与数据库的连接
		Connection conn = getConnection();
		PreparedStatement pStemt = null;
		String tableSql = SQL + tableName;
		List<String> columnComments = new ArrayList<String>();// 列名注释集合
		ResultSet rs = null;
		try {
			pStemt = conn.prepareStatement(tableSql);
			rs = pStemt.executeQuery("show full columns from " + tableName);
			while (rs.next()) {
				columnComments.add(rs.getString("Comment"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
					closeConnection(conn);
				} catch (SQLException e) {
					System.out.println();
					System.out.println("getColumnComments close ResultSet and connection failure" + e);
				}
			}
		}
		return columnComments;
	}

	/**
	 * 根据数据库连接和表明获取主键名(利用正则表达式)
	 * 
	 * @param table 数据库中的表名
	 * @return 执行成功返回一个主键名的字符数组，否则返回null或抛出一个异常
	 * @exception 抛出sql执行异常
	 * @author yuyu
	 */
	public static String[] getPrimaryKey(String table) {
		Connection conn = getConnection();
		String sql = "SHOW CREATE TABLE " + table;
		try {

			PreparedStatement pre = conn.prepareStatement(sql);
			ResultSet rs = pre.executeQuery();
			if (rs.next()) {

				// 正则匹配数据
				Pattern pattern = Pattern.compile("PRIMARY KEY \\(\\`(.*)\\`\\)");
				Matcher matcher = pattern.matcher(rs.getString(2));
				matcher.find();
				String data = matcher.group();
				// 过滤对于字符
				data = data.replaceAll("\\`|PRIMARY KEY \\(|\\)", "");
				// 拆分字符
				String[] stringArr = data.split(",");

				return stringArr;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String getPrimaryName(String table){  
		Connection conn = getConnection();
        DatabaseMetaData md;
		try {
			md = conn.getMetaData();
			ResultSet rs = md.getPrimaryKeys(null, null, table);  
	        while(rs.next()){  
	            System.out.println(rs.getString("TABLE_CAT"));  	//数据库名
	            System.out.println(rs.getString("TABLE_SCHEM"));  	//
	            System.out.println(rs.getString("TABLE_NAME"));  	//表名
	            System.out.println(rs.getString("COLUMN_NAME"));  	//字段名
	            System.out.println(rs.getString("KEY_SEQ"));  		//主键的优先级排序
	            System.out.println(rs.getString("PK_NAME"));  		//主键
	        }  
	          
	        rs.close(); 
		} catch (SQLException e) {
			e.printStackTrace();
		}  
         
        return null;  
    }  

	public static String getDRIVER() {
		return DRIVER;
	}

	public static void setDRIVER(String dRIVER) {
		DRIVER = dRIVER;
	}

	public static String getURL() {
		return URL;
	}

	public static void setURL(String uRL) {
		URL = uRL;
	}

	public static String getUSERNAME() {
		return USERNAME;
	}

	public static void setUSERNAME(String uSERNAME) {
		USERNAME = uSERNAME;
	}

	public static String getPASSWORD() {
		return PASSWORD;
	}

	public static void setPASSWORD(String pASSWORD) {
		PASSWORD = pASSWORD;
	}

	public static String getSql() {
		return SQL;
	}

	public static String getDriver() {
		return DRIVER;
	}

	public static String getUrl() {
		return URL;
	}

	public static String getUsername() {
		return USERNAME;
	}

	public static String getPassword() {
		return PASSWORD;
	}

	public static void main(String[] args) {
		String tableName = "ylf_attend_leave";
		System.out.println("ColumnNames:" + getColumnNames(tableName));
		System.out.println("ColumnTypes:" + getColumnTypes(tableName));
		System.out.println("ColumnComments:" + getColumnComments(tableName));
	}

}
