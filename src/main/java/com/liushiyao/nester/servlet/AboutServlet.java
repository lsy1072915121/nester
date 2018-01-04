package com.liushiyao.nester.servlet;

import com.liushiyao.nester.common.BusiResult;
import com.liushiyao.nester.common.BusiStatus;
import com.liushiyao.nester.utils.JdbcUtils;
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


/**
 * 查询用户信息
 */

@WebServlet(name = "AboutServlet", urlPatterns = {"/AboutServlet"})
public class AboutServlet extends HttpServlet {

  private final static Logger LOGGER = Logger.getLogger(AboutServlet.class);


  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    this.doGet(request, response);
    PrintWriter out = response.getWriter();
  }

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {
    response.setContentType("text/html;charset=utf-8");
    PrintWriter out = response.getWriter();
    BusiResult busiResult = new BusiResult(BusiStatus.SUCCESS);
    String uid = request.getParameter("uid");
    LOGGER.info("uid="+uid);
    String sql =
        "select user.uid,account,age,sex,device_id,nick,medical_history,accention "
            + "from user join user_info on user.uid=user_info.uid where user.uid=?";
    List list = new ArrayList();
    list.add(uid);
    JdbcUtils jdbcUtils = new JdbcUtils();
    jdbcUtils.getConnection();

    try {
      String result = jdbcUtils.findSimpleResultToString(sql, list);
      LOGGER.info("result="+result);
      if(!StringUtils.isNullOrEmpty(result)){
        busiResult.setData(result);
        out.println(busiResult.toJson());
      }else{

        out.println(new BusiResult(BusiStatus.BUSIERROR).toJson());

      }
    } catch (SQLException e) {
      e.printStackTrace();
    } finally {

      jdbcUtils.releaseConn();

    }


  }
}
