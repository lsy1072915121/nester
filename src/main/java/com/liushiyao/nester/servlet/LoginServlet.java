package com.liushiyao.nester.servlet;

import com.liushiyao.nester.common.BusiResult;
import com.liushiyao.nester.common.BusiStatus;
import com.liushiyao.nester.common.MD5;
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

@WebServlet(name = "loginServlet",urlPatterns = "/login")
public class LoginServlet extends HttpServlet
{


	private static final Logger logger = Logger.getLogger(Servlet.class);


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		PrintWriter printWriter = response.getWriter();
		printWriter.println("Request Method Error!!!'");
	}


	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter printWriter =response.getWriter();
		String account = request.getParameter("account");
		String passwd = request.getParameter("passwd");
		if (!StringUtils.isNullOrEmpty(account) && !StringUtils.isNullOrEmpty(passwd)){
			String md5Passwd = MD5.getMD5(passwd);
			List parms = new ArrayList();
			parms.add(account);
			parms.add(md5Passwd);
			JdbcUtils jdbcUtils = new JdbcUtils();
			jdbcUtils.getConnection();
			String sql = "SELECT *FROM user WHERE account=? AND passwd=?";
			try {
				String result = jdbcUtils.findModeResultToString(sql,parms);
				if(!StringUtils.isNullOrEmpty(result))
				{
					logger.info("用户："+account+"登录成功");
					printWriter.println(new BusiResult(BusiStatus.SUCCESS).toJson());
				}else{
					logger.info("用户："+account+"登录失败");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally {
				jdbcUtils.releaseConn();
			}
		}else
		{
			printWriter.println(new BusiResult(BusiStatus.PARAMETERILLEGAL).toJson());
		}


	}

}
