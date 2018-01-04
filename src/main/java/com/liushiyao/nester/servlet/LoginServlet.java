package com.liushiyao.nester.servlet;

import com.liushiyao.nester.common.BusiResult;
import com.liushiyao.nester.common.BusiStatus;
import com.liushiyao.nester.common.MD5;
import com.liushiyao.nester.entity.User;
import com.liushiyao.nester.utils.JdbcUtils;
import com.liushiyao.nester.utils.JsonUtil;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.junit.Test;

@WebServlet(name = "loginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {


  private static final Logger logger = Logger.getLogger(Servlet.class);


  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter printWriter = response.getWriter();
    printWriter.println("Request Method Error!!!'");
  }


  public void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    request.setCharacterEncoding("utf-8");
    response.setContentType("text/html;charset=utf-8");
    PrintWriter printWriter = response.getWriter();
    String account = request.getParameter("account");
    String passwd = request.getParameter("passwd");
    if (!StringUtils.isNullOrEmpty(account) && !StringUtils.isNullOrEmpty(passwd)) {
      String md5Passwd = MD5.getMD5(passwd);
      List parms = new ArrayList();
      BusiResult busiResult = new BusiResult(BusiStatus.SUCCESS);
      parms.add(account);
      parms.add(md5Passwd);
      parms.add(account);
      JdbcUtils jdbcUtils = new JdbcUtils();
      jdbcUtils.getConnection();
      String sql = "SELECT *FROM user WHERE EXISTS(SELECT *FROM user WHERE account=? AND passwd=?) AND account=?";
      try {
        String result = jdbcUtils.findModeResultToString(sql, parms);
        logger.info(request);
        if (!StringUtils.isNullOrEmpty(result)) {
          logger.info("用户：" + account + "登录成功");
          busiResult.setData(result);
          printWriter.println(busiResult.toJson());
        } else {
          logger.info("用户：" + account + "登录失败");
          printWriter.println(new BusiResult(BusiStatus.PARAMETERILLEGAL).toJson());
        }
      } catch (SQLException e) {
        e.printStackTrace();
      } finally {
        jdbcUtils.releaseConn();
      }
    } else {
      printWriter.println(new BusiResult(BusiStatus.PARAMETERILLEGAL).toJson());
    }


  }

  @Test
  public void userTest() {

    List parms = new ArrayList();
    parms.add("1234567890");
    parms.add("liushiyao");
    parms.add("1234567890");
    JdbcUtils jdbcUtils = new JdbcUtils();
    jdbcUtils.getConnection();
    String sql = "SELECT *FROM user WHERE EXISTS(SELECT *FROM user WHERE account=? AND passwd=?) AND account=?";
    try {
      String result = jdbcUtils.findModeResultToString(sql, parms);
      System.out.println(result);
      if (!StringUtils.isNullOrEmpty(result)) {
        User user = (User) JsonUtil.fromJson("{" + result + "}", User.class);
        System.out.println(user.getAccount());
      } else {

      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {
      jdbcUtils.releaseConn();
    }

  }

  @Test
  public void Test() {

    String string = "\"uid\":\"4\",\"account\":\"1234567890\",\"passwd\":\"liushiyao\",\"age\":\"\",\"sex\":\"\",\"device_id\":\"\",\"nick\":\"liushiyao\",\"create_time\":\"\",\"update_time\":\"\",";
    string = string.replace("\"","");
    User user = new User ();
    String [] strings = string.split ( "," );
    for (String s :strings){
      String str[] = new String[2];
      str = s.split(":");
      if(str.length == 2){
        switch (str[0]){
          case "uid":user.setUid(Integer.parseInt(str[1]));break;
          case "account":user.setAccount(str[1]);break;
          case "passwd":user.setPasswd(str[1]);break;
          case "age":user.setAge(Integer.parseInt(str[1]));break;
          case "sex":user.setSex(Boolean.parseBoolean(str[1]));break;
          case "device_id":user.setDevice_id(str[1]);break;
          case "nick":user.setNick(str[1]);break;
          default:break;
        }
      }
    }
    System.out.println(user.getAccount());

  }


}
