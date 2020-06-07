package com.wy.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.wy.pojo.HdfsDao;

@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String result = request.getParameter("result");
		String fileName = request.getParameter("fileName");

		String filePath=result+"/"+fileName;
		try {
			InputStream in=HdfsDao.down(filePath);
			 byte[] b = new byte[in.available()];  
	            in.read(b);  
	            response.setCharacterEncoding("utf-8");  
	            response.setHeader("Content-Disposition","attachment; filename="+fileName+"");  
	            //获取响应报文输出流对象  
	            ServletOutputStream  out =response.getOutputStream();  
	            //输出  
	            out.write(b);  
	            out.flush();  
	            out.close();  
		} catch (IOException | InterruptedException | URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	} 

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
}
