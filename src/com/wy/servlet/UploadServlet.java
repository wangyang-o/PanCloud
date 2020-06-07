package com.wy.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.apache.hadoop.fs.FileStatus;

import com.wy.pojo.HdfsDao;

@WebServlet("/UploadServlet")
@MultipartConfig
public class UploadServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
	    Part part = request.getPart("myfile");
//		文件名
        String disposition = part.getHeader("Content-Disposition");
        String fileName =disposition.substring(disposition.lastIndexOf("=")+2,disposition.length()-1);
//      路径
        String a=(String) request.getParameter("thisPath");
		String upPath=a+"/"+ fileName;
        InputStream in = part.getInputStream();
        System.out.println(disposition.substring(disposition.lastIndexOf("=")+2,disposition.length()-1));
        try {
			HdfsDao.upload(upPath, in);
			System.out.println("上传成功");
			HttpSession session = request.getSession();
			String username=(String)session.getAttribute("username");
			if(a.equals(username)) {
				FileStatus[] documentList = HdfsDao.ShowFiles(a);
				request.setAttribute("documentList", documentList);
				System.out.println("得到list数据"+documentList);
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}else {
				response.sendRedirect(request.getHeader("Referer"));
			}
		} catch (IllegalArgumentException | IOException | InterruptedException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        in.close();
        
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
}
