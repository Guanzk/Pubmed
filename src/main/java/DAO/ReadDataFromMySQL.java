package DAO;

import Config.MySQLConfig;

import java.sql.*;

/**
 * 根据Redis得到的索引aid 从MySQL中提取相关数据
 * @author1 chenshuo
 * @author2 DrRic
 **/

public class ReadDataFromMySQL {
	static final String JDBC_DRIVER = MySQLConfig.get("JDBC_DRIVER");
	static final String DB_URL = MySQLConfig.get("DB_URL");
	static final String USER = MySQLConfig.get("USER");
	static final String PASS = MySQLConfig.get("PASS");
    static Connection conn;
    static Statement stmt ;
    public static String ans ;
    static ResultSet rs;

    /**
	 *  Get the connection from MySQL
	 * */
	public static Connection getConnection(){
		Connection conn = null;
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
		} catch(SQLException e){
			e.printStackTrace();
		} catch (ClassNotFoundException c){
			c.printStackTrace();
		}
		return conn;
	}

	/**
	 *  Close the connection
	 * */
	public static void close(AutoCloseable auto){
		if (auto != null){
			try{
				auto.close();
			} catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	public static long getTime(){
		return System.currentTimeMillis();
	}


}
