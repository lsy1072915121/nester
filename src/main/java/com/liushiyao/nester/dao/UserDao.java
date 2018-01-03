package com.liushiyao.nester.dao;

import com.google.gson.Gson;
import com.liushiyao.nester.common.MD5;
import com.liushiyao.nester.entity.User;
import com.liushiyao.nester.utils.JdbcUtils;
import com.liushiyao.nester.utils.JsonUtil;
import com.mysql.jdbc.StringUtils;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class UserDao {


  public void insertUser(User user) {

    JdbcUtils jdbcUtils = new JdbcUtils();
    jdbcUtils.getConnection();
    String sql = "INSERT INTO user (account,passwd,create_time) VALUE (?,?,?)";
    List parms = new ArrayList();
    parms.add(user.getAccount());
    parms.add(user.getPasswd());
    parms.add(new Date(System.currentTimeMillis()));
    try {
      jdbcUtils.updateByPreparedStatement(sql, parms);
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      jdbcUtils.releaseConn();
    }

  }

  public User checkUser(String account, String passwd) {
    JdbcUtils jdbcUtils = new JdbcUtils();
    jdbcUtils.getConnection();
    String sql = "SELECT *FROM user WHERE account=? AND passwd=?";
    List parms = new ArrayList();
    parms.add(account);
    parms.add(MD5.getMD5(passwd));
    try {
      String result = jdbcUtils.findSimpleResultToString(sql, parms);
      if (!StringUtils.isNullOrEmpty(result)) {
        User user = (User) JsonUtil.fromJson(toJson(result), User.class);
        return user;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      jdbcUtils.releaseConn();
    }
    return null;
  }

  private String toJson(String string) {
    return "{" + string + "}";
  }

  @Test
  public void checkUserTest() {


    String s = "{\n"
        + "  \"uid\": \"1\",\n"
        + "  \"account\": \"1072915121\",\n"
        + "  \"passwd\": \"c3f308f50284fb43e6ad5995af563053\",\n"
        + "  \"age\": \"\",\n"
        + "  \"sex\": \"\",\n"
        + "  \"device_id\": \"\",\n"
        + "  \"create_time\": \"\",\n"
        + "  \"update_time\": \"\"\n"
        + "}";
    System.out.println(new Gson().fromJson(s,User.class));
  }


}
