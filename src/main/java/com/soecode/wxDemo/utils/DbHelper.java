package com.soecode.wxDemo.utils;

import java.sql.*;

public class DbHelper {
   static String  username = "iot";  //数据库用户名
   static String password = "IOT9r9a9y!@#";//数据库密码
   static String url = "jdbc:mysql://120.26.97.0:3306/iot?useSSL=false&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=GMT%2b8&allowPublicKeyRetrieval=true";
   Connection conn=null;
   /**
    * 设置驱动
    */
   protected void setDriverClass() {
      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
      } catch (ClassNotFoundException var4) {
         throw new IllegalStateException("Could not load JDBC driver class ", var4);
      }
   }

   /**
    * 获得mysql 的连接
    * @return connection
    * @throws SQLException
    */
   protected Connection getConnection() throws SQLException {
      setDriverClass();
      conn = DriverManager.getConnection(url,username,password);
      return conn;
   }

   /**
    * 执行sql查询
    * @param sql
    * @return
    * @throws SQLException
    */
   public ResultSet executeQuery(String sql) throws SQLException {
      if(conn==null) {
         conn = getConnection();
      }
      Statement statement = conn.createStatement();
      ResultSet resultSet_01 = statement.executeQuery(sql);

      return resultSet_01;
   }

   /**
    * 执行 update delete create drop delete 等语句
    * @param sql
    * @return
    * @throws SQLException
    */
   public boolean execute(String sql) throws SQLException{
      if(conn==null) {
         conn = getConnection();
      }
      Statement statement = conn.createStatement();
      boolean isSucess = statement.execute(sql);
      return isSucess;
   }
   public void close() throws SQLException {
      if(conn!=null) {
         conn.close();
         conn = null;
      }
   }
     public static void main(String[] args) throws ClassNotFoundException, SQLException {
        //1.加载驱动
        Class.forName("com.mysql.cj.jdbc.Driver");
        System.out.println( "加载驱动成功!" );
        //2.用户信息和url
        String url = "jdbc:mysql://120.26.97.0:3306/iot?useSSL=false&useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&serverTimezone=GMT%2b8&allowPublicKeyRetrieval=true";
        String username = "iot";  //数据库用户名
        String password = "IOT9r9a9y!@#";//数据库密码
        //3.连接成功，数据库对象Connection代表数据库
        Connection connection = DriverManager.getConnection(url,username,password);
        //4.执行SQL的对象Statement
        Statement statement = connection.createStatement();


        String sql = "SELECT * FROM card_ptdj";
        ResultSet resultSet_01 = statement.executeQuery(sql);//查询的结果集，封装了所有的查询结果  statement.executeQuery()执行sql语句
        System.out.println("连接成功");//resultSet_01 .getObject获取指定的数据类型
        while(resultSet_01.next()){

        }
        //关闭
        resultSet_01.close();
        statement.close();
        connection.close();
    }
}
