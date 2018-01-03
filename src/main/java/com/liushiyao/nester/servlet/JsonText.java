package com.liushiyao.nester.servlet;

import com.liushiyao.nester.utils.JdbcUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class JsonText extends HttpServlet
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

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		// 创建数据库连接
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		// FileUtil fileUtil = new FileUtil();
		// String str =
		// fileUtil.loadFile("E:/Code/JavaWeb/myEclipse/WebSocket_1/WebRoot/myfile/json.json","utf-8");
		try {
			String sql2 = "select * from EmptyUser";
			String list = jdbcUtils.findModeResultToString(sql2,
					null);
			
			list = ("[{"+list+"}]").replace(",}]","}]");
			System.out.println(list);
//			JsonUtils.parseUserFromJson(list);
			out.println(list);
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
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
		this.doGet(request, response);
	}

}
