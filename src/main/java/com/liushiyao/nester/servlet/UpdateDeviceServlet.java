package com.liushiyao.nester.servlet;

import com.liushiyao.nester.utils.JdbcUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 用于设备的绑定信息的修改 UpdateDeviceServlet
 * 
 * @author 北岭山下
 * 
 *         2016年8月15日 下午1:10:25
 */
public class UpdateDeviceServlet extends HttpServlet
{

	/**
	 * The doGet method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String account = request.getParameter("account");
		String device = request.getParameter("device");
		System.out.println("UpdateDeviceServlet==>" + "账号：" + account + " 设备："
				+ device);
		// 数据库修改曹操作
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();

		boolean flag = false;
		try {

			String sql = "update EmptyUser set " + "device"
					+ "= ? where account = ? ";
			List<Object> params = new ArrayList<Object>();
			params.add(device);
			params.add(account);
			flag = jdbcUtils.updateByPreparedStatement(sql, params);
			System.out.println("改 操作是否成功？ ：" + flag);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		//返回操作信息
		if (flag) {
			out.println("device succeed");
		}
		else {
			out.println("device unsucceed");
		}
	}

	/**
	 * The doPost method of the servlet. <br>
	 * 
	 * This method is called when a form has its tag value method equals to
	 * post.
	 * 
	 * @param request
	 *            the request send by the client to the server
	 * @param response
	 *            the response send by the server to the client
	 * @throws ServletException
	 *             if an error occurred
	 * @throws IOException
	 *             if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
	}

}
