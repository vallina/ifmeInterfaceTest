package com.hulu.db;

import sun.rmi.transport.*;

import java.io.InputStream;
import java.sql.*;
import java.sql.Connection;
import java.util.Properties;

/**
 * Created by yumi on 2017/3/7.
 */
public class DBUtils {

    // 驱动包名和数据库url
    private static String urlrelation = null;
    private static String urlimv = null;

    private static String driverClass = null;
    // 数据库用户名和密码
    private static String userName = null;
    private static String password = null;

    private static Connection conn = null;
    private Statement stmt = null;

    /**
     * 初始化驱动程序
     * 静态代码块中（只加载一次）
     */
    static {
        try {
            //读取db.properties文件
            Properties prop = new Properties();
            InputStream in = DBUtils.class.getResourceAsStream("/db.properties");

            //加载文件
            prop.load(in);
            //读取信息
            //url = prop.getProperty("url");
            urlrelation = prop.getProperty("urlrelation");
            urlimv = prop.getProperty("urlimv");
            driverClass = prop.getProperty("driverClass");
            userName = prop.getProperty("user");
            password = prop.getProperty("password");

            //注册驱动程序
            Class.forName(driverClass);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("找不到驱动程序类 ，加载驱动失败！");
        }
    }


    /**
     * 打开数据库驱动连接
     */
    public static Connection getConn() {
        try {
            conn = DriverManager.getConnection(urlrelation, userName, password);
            System.out.println("\n数据库连接成功!");
        } catch (SQLException e) {
            System.out.println("\n数据库连接失败!");
            e.printStackTrace();
        }
        return conn;
    }



    /**
     * 打开数据库驱动连接
     */
    public static Connection getConn(String urlNew) {
        try {
            conn = DriverManager.getConnection(urlNew, userName, password);
            System.out.println("\n数据库连接成功!");
        } catch (SQLException e) {
            System.out.println("\n数据库连接失败!");
            e.printStackTrace();
        }
        return conn;
    }







    /**
     * 关闭数据库连接
     *
     * @param conn：Connection类型的参数
     */
    public static void closeConn(Connection conn) {
        try {
            if (conn == null) {
                return;
            }
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 关闭连接
     *
     * @throws Exception
     */
    public void DBLinkClose() throws Exception {
        try {
            closeConn(conn);
            System.out.println("\n关闭连接成功!");
        } catch (Exception e) {
            System.out.println("\n关闭连接失败!");
            e.printStackTrace();
        }
    }


    /**
     * 创建数据库连接的createStatement
     *
     * @throws Exception
     */
    public void createState() throws Exception {
        //Connection connect = null;
        try {
            conn = DBUtils.getConn();
            stmt = conn.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //return stmt;
    }




    /**
     * 创建数据库连接的createStatement
     *
     * @throws Exception
     */
    public void createState(String urladdress) throws Exception {
        //Connection connect = null;
        try {
            conn = DBUtils.getConn(urladdress);
            stmt = conn.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //return stmt;
    }





    /**
     * 查询数据库
     *
     * @param sql：要执行的查询sql语句
     * @return 返回ResultSet类型的查询结果
     * @throws Exception
     */
    public ResultSet dbselect(String sql) throws Exception {
        ResultSet rs = null;
        try {

            if (sql == null) {
                System.out.println("数据库查询sql：空值不执行查询");
            } else {
                System.out.println("数据库查询sql：" + sql + "--------");
                rs = stmt.executeQuery(sql);
            }
        } catch (Exception e) {

        }
        return rs;
    }


    public static void main(String[] args) throws Exception {
        DBUtils dbUtils = new DBUtils();

        dbUtils.createState(urlrelation);
       // dbUtils.createState(urlimv);
        ResultSet res = null;

        res = dbUtils.dbselect("select * from imv_user_relation limit 1");
       // res = dbUtils.dbselect("select * from imv_user limit 2");

        while (res.next()) {

            System.out.println(res.getString(2));
        }
        dbUtils.DBLinkClose();
    }
}










































































