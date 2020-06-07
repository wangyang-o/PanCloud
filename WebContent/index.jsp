<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="org.apache.hadoop.fs.FileStatus"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>欢迎使用-bobo网盘</title>
<!-- 新 Bootstrap4 核心 CSS 文件 -->
<link rel="stylesheet"
	href="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/css/bootstrap.min.css">
<link rel="icon"
	href="https://cdn.jsdelivr.net/gh/wangyang-o/imgcdn/img/20200512002714.png"
	type="image/x-icon">
<link
	href="https://cdn.bootcdn.net/ajax/libs/font-awesome/4.7.0/css/font-awesome.css"
	rel="stylesheet">
</head>
<body class="container">
	<%
		FileStatus[] list = (FileStatus[]) request.getAttribute("documentList");
	String name = (String) session.getAttribute("username");
	String thisPath;
	if (request.getAttribute("thisPath") == null) {
		thisPath = name;
	} else {
		thisPath = (String) request.getAttribute("thisPath");
	}
	String result;//用户当前目录
	if (list.length != 0) {
		/*不知道怎么获取当前目录的路径，所以找到他一个子目录的父目录，前面一串路径都是一样的
		比如hdfs://localhost:9000/user/wangyang/user/wy/dir1/
		你点击了dir2，你就只需要得到hdfs://localhost:9000/user/wangyang/user/wy/+你点击的路径dir2
		*/
		result = list[0].getPath().getParent().toString().substring(40);
	} else {
		result = "";
	}
	%>
	<nav class="navbar navbar-expand-lg navbar-light bg-light mt-5">
		<a class="navbar-brand" href="#">bobo<i
			class="fa fa-modx fa-spin m-1"></i>网盘
		</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarSupportedContent"
			aria-controls="navbarSupportedContent" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<a class="nav-link col-3">当前路径：<%=thisPath%></a>
		<form id="frm" class="form-inline my-2 my-lg-0"
			action="MkdirServlet?thisPath=<%=thisPath%>" method="post">
			<input class="form-control mr-sm-2 col-5" type="text"
				placeholder="输入文件夹名称" name="mkdir">
			<button class="btn btn-outline-success my-2 my-sm-0" type="submit">创建</button>
		</form>
		<form action="UploadServlet?thisPath=<%=thisPath%>" method="post"
			enctype="multipart/form-data">
			<div class="row">

				<div class="col-9">
					<input type="file" class="filestyle" data-badgeName="badge-danger"
						data-badge="true" data-placeholder="未选择" data-text="上传文件"
						name="myfile">
				</div>
				<div class="col-3 ml-0 pl-0">
					<button class="btn btn-secondary my-2 my-sm-0" type="submit">
						<i class="fa fa-upload" aria-hidden="true"></i>
					</button>
				</div>
			</div>
		</form>



	</nav>
	<table class="table table-hover " style="text-align: center">
		<thead class="thead-dark">
			<tr>
				<th scope="col">序号</th>
				<th scope="col">文件名</th>
				<th scope="col">属性</th>
				<th scope="col">大小</th>
				<th scope="col">可执行操作</th>
			</tr>
		</thead>
		<tbody>
			<%
				if (list != null) {

				for (int i = 0; i < list.length; i++) {
			%>
			<tr>
				<%
					out.print("<th scope='row'>" + (i + 1) + "</th>");
				if (list[i].isDir())//DocumentServlet
				{
					out.print("<td><strong>" + list[i].getPath().getName() + "</strong></td>");
				} else {
					out.print("<td>" + list[i].getPath().getName() + "</td>");
				}
				%>
				<td><%=(list[i].isDir() ? "<i class='fa fa-folder-open mr-1' aria-hidden='true'></i>目录"
		: "<i class='fa fa-file-text-o mr-1' aria-hidden='true'></i>文件")%></td>
				<td><%=(list[i].getLen())%></td>
				<td>
					<%
					String cut=(String)request.getAttribute("cut");
					if (list[i].isDir())//DocumentServlet
					{
						out.print("<a role='button' class='btn btn-outline-success' href='ShowChildDirServlet?filePath=" + result + "/"
						+ list[i].getPath().getName() + "&cut=" + cut + "'><i class='fa fa-sign-in mr-1'></i>进入</a>");
					} else {
						out.print("<a role='button' class='btn btn-outline-primary' href='DownloadServlet?result=" + result + "&fileName="
						+ list[i].getPath().getName() + "'><i class='fa fa-download' aria-hidden='true'></i>下载</a>");
					}
					%> <a role="button" class="btn btn-outline-danger"
					href="DeleteServlet?thisPath=<%=thisPath%>&fileName=<%=list[i].getPath().getName()%>"><i
						class="fa fa-trash-o" aria-hidden="true"></i></a> <%
		 	 System.out.println("html:" + request.getAttribute("cut"));
			 if (cut == null||cut.equals("null")) {
			 	out.print("<a role='button' class='btn btn-outline-secondary' href='MoveServlet?thisPath=" + thisPath + "&fileName="
			 	+ list[i].getPath().getName() + "'><i class='fa fa-scissors'></i> </a>");
			 } else if (!cut.equals("null")&&cut != null && list[i].isDir()) {
			 	out.print("<a role='button' class='btn btn-outline-secondary' href='MoveServlet?pastePath=" + thisPath
			 	+ "&pasteName=" + list[i].getPath().getName() + "&cut=" + request.getAttribute("cut") + "'>粘贴</a>");
			
		 }
 %>
				</td>
				<%
					}
				}
				if (list.length == 0) {
					out.print("<tr><td colspan='5' style='text-align:center'>没有任何文件哦</td><tr>");
				}
				%>
			
		</tbody>
	</table>
	<div class="row">
		<div class="col-3 offset-9">
			<div class="row">
				<a role="button" class="btn btn-outline-secondary col-6 mr-1"
					href="javascript:history.go(-1);location.reload()"><i
					class="fa fa-arrow-left" aria-hidden="true"></i>返回上一级</a> <a
					class="btn btn-danger col-4" href="LogoutServlet" role="button">注销登录</a>
			</div>

		</div>
	</div>
</body>
<footer>
	<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
	<script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
	<script
		src="https://cdn.bootcdn.net/ajax/libs/bootstrap-filestyle/2.1.0/bootstrap-filestyle.js"></script>
	<!-- bootstrap.bundle.min.js 用于弹窗、提示、下拉菜单，包含了 popper.min.js -->
	<script
		src="https://cdn.staticfile.org/popper.js/1.15.0/umd/popper.min.js"></script>

	<script
		src="https://cdn.staticfile.org/twitter-bootstrap/4.3.1/js/bootstrap.min.js"></script>


	<script type="text/javascript" color="100,50,100" opacity='1'
		zIndex="-2" count="99"
		src="//cdn.bootcss.com/canvas-nest.js/1.0.1/canvas-nest.min.js"></script>
</footer>
</html>




