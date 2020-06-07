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

@WebServlet("/ShowChildDirServlet")
public class ShowChildDirServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String filePath = request.getParameter("filePath");
		request.setAttribute("thisPath",filePath);
//		//用于移动，传递原始文件路径给子目录
		String cut = request.getParameter("cut");
		System.out.println("看看cut:"+cut);
		request.setAttribute("cut", cut);
		FileStatus[] documentList = HdfsDao.ShowDirFiles(filePath);
		request.setAttribute("documentList", documentList);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
}