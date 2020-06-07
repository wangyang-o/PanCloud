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

@WebServlet("/MoveServlet")

public class MoveServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String thisPath = request.getParameter("thisPath");
		String fileName = request.getParameter("fileName");
		String filePath1 = thisPath + "/" + fileName;
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");

		String pastePath = request.getParameter("pastePath");
		String pasteName = request.getParameter("pasteName");
		String filePath2 = pastePath + "/" + pasteName;
		if (pastePath != null) {
			String cut = request.getParameter("cut");
			HdfsDao.ReName(cut, filePath2);
			request.setAttribute("cut", "null");
			if (pastePath.equals(username)) {
				FileStatus[] documentList = HdfsDao.ShowFiles(pastePath);
				request.setAttribute("documentList", documentList);
				System.out.println("得到list数据" + documentList);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			} else {
				System.out.println("从定向了1");
				response.sendRedirect(request.getHeader("Referer"));
			}
		} else {
			System.out.println("准备剪切文件:" + filePath1);
			request.setAttribute("cut", filePath1);

			System.out.println("我是username：" + username + "=====" + "我是thisPath：" + thisPath);
			System.out.println("===============================================================");


				FileStatus[] documentList = HdfsDao.ShowFiles(username);
				request.setAttribute("documentList", documentList);
				System.out.println("得到list数据" + documentList);
				request.getRequestDispatcher("index.jsp").forward(request, response);

		}
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
