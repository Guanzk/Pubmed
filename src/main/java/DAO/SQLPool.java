package DAO;


import Config.MySQLConfig;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class SQLPool {

	private static final Logger logger = LoggerFactory.getLogger(SQLPool.class);

	//在静态代码块中创建数据库连接池
	private static ComboPooledDataSource ds = null;

	static{
		try{
			//通过代码创建C3P0数据库连接池
            ds = new ComboPooledDataSource();
            ds.setDriverClass(MySQLConfig.get("JDBC_DRIVER"));
            ds.setJdbcUrl(MySQLConfig.get("DB_URL"));
            ds.setUser(MySQLConfig.get("USER"));
            ds.setPassword(MySQLConfig.get("PASS"));
            ds.setInitialPoolSize(10);
            ds.setMinPoolSize(5);
            ds.setMaxPoolSize(2000);
            ds.setTestConnectionOnCheckin(true);
            ds.setIdleConnectionTestPeriod(300);
            ds.setCheckoutTimeout(5000);


			logger.info("------Init the SQLPool------");

			//通过读取C3P0的xml配置文件创建数据源，C3P0的xml配置文件c3p0-config.xml必须放在src目录下
			//ds = new ComboPooledDataSource();//使用C3P0的默认配置来创建数据源
			//ds = new ComboPooledDataSource("MySQL");//使用C3P0的命名配置来创建数据源
		}catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public static Connection getConnection() {
		//从数据源中获取数据库连接
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void release(Connection conn,Statement st,ResultSet rs){
		if(rs!=null){
			try{
				//关闭存储查询结果的ResultSet对象
				rs.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if(st!=null){
			try{
				//关闭负责执行SQL命令的Statement对象
				st.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}

		if(conn!=null){
			try{
				//将Connection连接对象还给数据库连接池
				conn.close();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
