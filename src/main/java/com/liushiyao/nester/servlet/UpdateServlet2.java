package com.liushiyao.nester.servlet;

import com.liushiyao.nester.utils.JdbcUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class UpdateServlet2 extends HttpServlet
{


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		request.setCharacterEncoding("utf-8");

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		JdbcUtils jdbcUtils = new JdbcUtils();
		jdbcUtils.getConnection();
		String account = request.getParameter("account");
		String name = URLDecoder.decode(request.getParameter("name"),"utf-8");
		String age = request.getParameter("age");
		String sex = request.getParameter("sex");
		String medical_history = request.getParameter("medical_history");
		String attention = request.getParameter("attention");
		System.out.println(account + name + age + sex + medical_history
				+ attention);
		String[] dataList = { name, age, sex, medical_history, attention };
		String[] dataNameList = { "name", "age", "sex", "medical_history",
				"attention" };
		int count = 0;
		try {
			for (int i = 0; i < dataList.length; i++) {
				if (dataList[i] != null) {
					System.out.println(dataList[i]);
					String sql = "update EmptyUser set " + dataNameList[i]
							+ "= ? where account = ? ";
					List<Object> params = new ArrayList<Object>();
					params.add(dataList[i]);
					params.add(account);
					boolean flag = jdbcUtils.updateByPreparedStatement(sql,
							params);
					System.out.println(flag);
					if (flag) {
						count++;
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jdbcUtils.releaseConn();
		}
		if (count == 5) {
			out.println("用户设置已保存");
		} else {
			out.println("服务器异常");
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
		doGet(request, response);
	}

}
