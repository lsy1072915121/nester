package com.liushiyao.nester.servlet;

import com.liushiyao.nester.common.BusiResult;
import com.liushiyao.nester.common.BusiStatus;
import com.liushiyao.nester.common.MD5;
import com.liushiyao.nester.utils.JdbcUtils;
import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

@WebServlet(name = "register",urlPatterns = "/register")
public class RegisterServlet extends HttpServlet
{


	private static final Logger logger = Logger.getLogger(RegisterServlet.class);

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.println("Your Request Method error!!!");
	}


	/**
	 * 注册用户，接收账号和密码
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String account = request.getParameter("account");
		String passwd = request.getParameter("passwd");
		List<Object> parms = new ArrayList<Object>();

		if(!StringUtils.isNullOrEmpty(account) && !StringUtils.isNullOrEmpty(passwd)){
			logger.info("account="+account+"passwd="+passwd);
			String md5Passwd = MD5.getMD5(passwd);
			parms.add(account);
			parms.add(md5Passwd);
			parms.add(new Date(System.currentTimeMillis()).toString());
			String sql = "insert into user (account,passwd,create_time) values(?,?,?)";
			JdbcUtils jdbcUtils = new JdbcUtils();
			try {
				jdbcUtils.getConnection();
				jdbcUtils.updateByPreparedStatement(sql,parms);
				out.println(new BusiResult(BusiStatus.SUCCESS).toJson());
			} catch (SQLException e) {
				e.printStackTrace();
				out.println(new BusiResult(BusiStatus.BUSIERROR).toJson());
			}finally {
				jdbcUtils.releaseConn();
			}
		}else {
			out.println(new BusiResult(BusiStatus.PARAMETERILLEGAL).toJson());
		}
	}

}
