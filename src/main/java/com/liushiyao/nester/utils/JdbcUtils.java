package com.liushiyao.nester.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.junit.Test;


public class JdbcUtils {

  private static final Logger logger = Logger.getLogger(JdbcUtils.class);


  private Connection connection;
  private PreparedStatement pStatement;
  private ResultSet resultSet;

  //构造方法
  public JdbcUtils() {
    try {
      Class.forName(PropertyUtil.getProperty("DB_DRIVER"));
      logger.info("注册驱动成功");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /*
   * 获得数据库连接
   */
  public Connection getConnection() {
    try {
      //URL 用户名 密码
      connection = DriverManager.getConnection(
          PropertyUtil.getProperty("DB_URL")
          , PropertyUtil.getProperty("DB_USER_NAME")
          , PropertyUtil.getProperty("DB_PASSWORD"));
    } catch (Exception e) {
      logger.error("####建立数据库连接失败####");
      e.printStackTrace();
    }
    return connection;
  }

  /**
   * 释放数据库连接 （必须释放）
   */
  public void releaseConn() {
    if (resultSet != null) {
      try {
        resultSet.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * 数据库的增删改
   *
   * @return 操作是否成功
   */
  public boolean updateByPreparedStatement(String sql, List<Object> params) throws SQLException {

    boolean flag = false;
    int result = -1;//表示当用户执行添加删除和修改的时候所影响数据库的行数
    pStatement = connection.prepareStatement(sql);
    int index = 1;
    if (params != null && !params.isEmpty()) {
      for (int i = 0; i < params.size(); i++) {
        pStatement.setObject(index++, params.get(i));
      }
    }
    result = pStatement.executeUpdate();
    flag = result > 0 ? true : false;
    return flag;
  }

  @Test
  public void SQLTest() throws SQLException {


    JdbcUtils jdbcUtils = new JdbcUtils();
    String sql = "insert into user(account,passwd) values('liushiyao2','123123');";
    jdbcUtils.getConnection();
    List<Object> paras = new ArrayList<Object>();
    paras.add("002");
    paras.add("第二代");
    paras.add("2017年第二代产品");
    boolean result = jdbcUtils.updateByPreparedStatement(sql,null);
    System.out.println(result);
    jdbcUtils.releaseConn();

  }

  /**
   * 查询单条记录
   */
  public Map<String, Object> findSimpleResult(String sql, List<Object> params) throws SQLException {
    Map<String, Object> map = new HashMap<String, Object>();
    int index = 1;
    pStatement = connection.prepareStatement(sql);
    if (params != null && !params.isEmpty()) {
      for (int i = 0; i < params.size(); i++) {
        pStatement.setObject(index++, params.get(i));
      }
    }
    resultSet = pStatement.executeQuery();//返回查询结果
    ResultSetMetaData metaData = resultSet.getMetaData();
    int col_len = metaData.getColumnCount();
    while (resultSet.next()) {
      for (int i = 0; i < col_len; i++) {
        String cols_name = metaData.getColumnName(i + 1);
        Object cols_value = resultSet.getObject(cols_name);
        if (cols_value == null) {
          cols_value = "";
        }
        map.put(cols_name, cols_value);
      }
    }
    return map;
  }

  public String findSimpleResultToString(String sql, List<Object> params) throws SQLException {
    String string = "";
    int index = 1;
    pStatement = connection.prepareStatement(sql);
    if (params != null && !params.isEmpty()) {
      for (int i = 0; i < params.size(); i++) {
        pStatement.setObject(index++, params.get(i));
      }
    }
    resultSet = pStatement.executeQuery();//返回查询结果
    ResultSetMetaData metaData = resultSet.getMetaData();
    int col_len = metaData.getColumnCount();
    while (resultSet.next()) {
      for (int i = 0; i < col_len; i++) {
        String cols_name = metaData.getColumnName(i + 1);
        Object cols_value = resultSet.getObject(cols_name);
        if (cols_value == null) {
          cols_value = "";
        }
        string = string + "\"" + cols_name + "\"" + ":\"" + cols_value + "\",";
      }
    }
    return string;
  }

  /**
   * 查询多条记录
   */
  public List<Map<String, Object>> findModeResult(String sql, List<Object> params)
      throws SQLException {
    List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    int index = 1;
    pStatement = connection.prepareStatement(sql);
    if (params != null && !params.isEmpty()) {
      for (int i = 0; i < params.size(); i++) {
        pStatement.setObject(index++, params.get(i));
      }
    }
    resultSet = pStatement.executeQuery();
    ResultSetMetaData metaData = resultSet.getMetaData();
    int cols_len = metaData.getColumnCount();
    while (resultSet.next()) {
      Map<String, Object> map = new HashMap<String, Object>();
      for (int i = 0; i < cols_len; i++) {
        String cols_name = metaData.getColumnName(i + 1);
        Object cols_value = resultSet.getObject(cols_name);
        if (cols_value == null) {
          cols_value = "";
        }
        map.put(cols_name, cols_value);
      }
      list.add(map);
    }

    return list;
  }

  public String findModeResultToString(String sql, List<Object> params) throws SQLException {
    String string = "";
    int index = 1;
    pStatement = connection.prepareStatement(sql);
    if (params != null && !params.isEmpty()) {
      for (int i = 0; i < params.size(); i++) {
        pStatement.setObject(index++, params.get(i));
      }
    }
    resultSet = pStatement.executeQuery();
    ResultSetMetaData metaData = resultSet.getMetaData();
    int cols_len = metaData.getColumnCount();
    while (resultSet.next()) {
//            Map<String, Object> map = new HashMap<String, Object>();  
      for (int i = 0; i < cols_len; i++) {
        String cols_name = metaData.getColumnName(i + 1);
        Object cols_value = resultSet.getObject(cols_name);
        if (cols_value == null) {
          cols_value = "";
        }
//                map.put(cols_name, cols_value);  
        string = string + "\"" + cols_name + "\"" + ":\"" + cols_value + "\",";
      }
//            list.add(map);  
    }

    return string;
  }
  /**通过反射机制查询单条记录
   * @param sql
   * @param params
   * @param cls
   * @return
   * @throws Exception
   */
    /*public <T> T findSimpleRefResult(String sql, List<Object> params,  
            Class<T> cls )throws Exception{  
        T resultObject = null;  
        int index = 1;  
        pStatement = connection.prepareStatement(sql);  
        if(params != null && !params.isEmpty()){  
            for(int i = 0; i<params.size(); i++){  
                pStatement.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pStatement.executeQuery();  
        ResultSetMetaData metaData  = resultSet.getMetaData();  
        int cols_len = metaData.getColumnCount();  
        while(resultSet.next()){  
            //通过反射机制创建一个实例  
            resultObject = cls.newInstance();  
            for(int i = 0; i<cols_len; i++){  
                String cols_name = metaData.getColumnName(i+1);  
                Object cols_value = resultSet.getObject(cols_name);  
                if(cols_value == null){  
                    cols_value = "";  
                }  
                Field field = cls.getDeclaredField(cols_name);  
                field.setAccessible(true); //打开javabean的访问权限  
                field.set(resultObject, cols_value);  
            }  
        }  
        return resultObject;  
  
    }  
  */
  /**通过反射机制查询多条记录
   * @param sql
   * @param params
   * @param cls
   * @return
   * @throws Exception
   */
   /* public <T> List<T> findMoreRefResult(String sql, List<Object> params,  
            Class<T> cls )throws Exception {  
        List<T> list = new ArrayList<T>();  
        int index = 1;  
        pStatement = connection.prepareStatement(sql);  
        if(params != null && !params.isEmpty()){  
            for(int i = 0; i<params.size(); i++){  
                pStatement.setObject(index++, params.get(i));  
            }  
        }  
        resultSet = pStatement.executeQuery();  
        ResultSetMetaData metaData  = resultSet.getMetaData();  
        int cols_len = metaData.getColumnCount();  
        while(resultSet.next()){  
            //通过反射机制创建一个实例  
            T resultObject = cls.newInstance();  
            for(int i = 0; i<cols_len; i++){  
                String cols_name = metaData.getColumnName(i+1);  
                Object cols_value = resultSet.getObject(cols_name);  
                if(cols_value == null){  
                    cols_value = "";  
                }  
                Field field = cls.getDeclaredField(cols_name);  
                field.setAccessible(true); //打开javabean的访问权限  
                field.set(resultObject, cols_value);  
            }  
            list.add(resultObject);  
        }  
        return list;  
    }  */


}  
