package com.wy.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.hadoop.fs.FileStatus;

import com.wy.pojo.HdfsDao;
import com.wy.pojo.User;
import com.wy.pojo.UserDao;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
 
	private static final long serialVersionUID = 1L;
 
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		// 接收表单信息
		String username = request.getParameter("user");
		String password = request.getParameter("password");
		// 设置回显
		request.setAttribute("user", username);
		request.setAttribute("password", password);
		// 根据用户名查询用户
		User user = new UserDao().findUser(username);
		if (user != null) {
			if (user.getPassword().equals(password)) {

//				request.getSession().setAttribute("user", user);
				HttpSession session = request.getSession();
				session.setAttribute("username", username);
//				HdfsDao hdfs = new HdfsDao();
				FileStatus[] documentList = HdfsDao.ShowFiles(username);
				request.setAttribute("documentList", documentList);
				request.getRequestDispatcher("index.jsp").forward(request, response);
//				response.sendRedirect("index.jsp");
			} else {
				// （1）只弹出弹窗
				response.getWriter()
						.write("<script>alert('密码错误！！'); window.location='login.jsp'; window.close();</script>");
				response.getWriter().flush();
//				response.getWriter().write("<script>alert('弹窗要显示的内容！');</script>");
//				request.setAttribute("loginError", "* 密码错误");
//				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}
		} else {
			response.getWriter()
					.write("<script>alert('用户不存在！！'); window.location='login.jsp'; window.close();</script>");
			response.getWriter().flush();
//			request.setAttribute("loginError", "* 用户不存在");
//			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}

	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
 
}
