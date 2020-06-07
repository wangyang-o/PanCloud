package com.wy.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wy.pojo.HdfsDao;
import com.wy.pojo.UserDao;
 
@WebServlet("/RegistServlet")
public class RegistServlet extends HttpServlet {
 
	private static final long serialVersionUID = 1L;
 
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String username = request.getParameter("reguser");
		String password = request.getParameter("password1");
		String rpsw = request.getParameter("password2");
		if(username==null||username.trim().isEmpty()){
//			request.setAttribute("registError", "用户名不能为空");
//			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			response.getWriter().write("<script>alert('用户名不能为空！！'); window.location='login.jsp'; window.close();</script>");
			response.getWriter().flush();
			return;
		}
		if(password==null||password.trim().isEmpty()){
//			request.setAttribute("registError", "密码不能为空");
//			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			response.getWriter().write("<script>alert('密码不能为空！！'); window.location='login.jsp'; window.close();</script>");
			response.getWriter().flush();
			return;
		}
		if(!password.equals(rpsw)){
//			request.setAttribute("registError", "密码不一致");
//			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			response.getWriter().write("<script>alert('密码不一致！！'); window.location='login.jsp'; window.close();</script>");
			response.getWriter().flush();
			return;
		}
		UserDao u = new UserDao();
		boolean res = u.addUser(username,password);
		if(res){
//			response.getWriter().write("<script>alert('注册成功！！'); window.location='login.jsp'; window.close();</script>");
//			response.getWriter().flush();
			HdfsDao.mkPersonalDir(username);
			response.sendRedirect("login.jsp");
		}else {
//			request.setAttribute("registError", "注册失败，该用户名已存在");
//			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			response.getWriter().write("<script>alert('注册失败，该用户名已存在！！'); window.location='login.jsp'; window.close();</script>");
			response.getWriter().flush();
		}
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
}