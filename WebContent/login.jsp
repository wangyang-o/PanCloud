<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN">

<head>
<meta charset="utf-8">
<title>你的最爱--bobo网盘</title>

<link href="https://cdn.bootcdn.net/ajax/libs/font-awesome/4.7.0/css/font-awesome.css" rel="stylesheet">

<link rel="icon"
	href="https://cdn.jsdelivr.net/gh/wangyang-o/imgcdn/img/20200512002714.png"
	type="image/x-icon">
<!--  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css"> -->
<link rel="stylesheet" href="css/style.css">

</head>

<body>
	<div class="dowebok" id="dowebok">
		<div class="form-container sign-up-container">
			<form action="RegistServlet" method="post">
				<h1>注册</h1>
				<div class="social-container">
					<a href="#" class="social"><i class="fa fa-qq"></i></a> 
					<a href="#"
						class="social"><i class="fa fa-link"></i></a> 
					<a href="#"
						class="social"><i class="fa fa-twitter"></i></a>
				</div>
				<input type="text" placeholder="用户名" name="reguser"> <input
					type="password" placeholder="密码" name="password1"> <input
					type="password" placeholder="确认密码" name="password2">
				<button type="submit">注册</button>
			</form>
		</div>
		<div class="form-container sign-in-container">
			<form action="LoginServlet" method="post">
				<h1>登录</h1>
				<div class="social-container">
					<a href="#" class="social"><i class="fa fa-qq"></i></a> <a href="#"
						class="social"><i class="fa fa-link"></i></a> <a href="#"
						class="social"><i class="fa fa-twitter"></i></a>
				</div>
				<span>使用您的帐号</span> <input type="text" placeholder="用户名" name="user">
				<input type="password" placeholder="密码" name="password"> <a
					href="#">忘记密码？</a>
				<button type="submit">登录</button>
			</form>
		</div>
		<div class="overlay-container">
			<div class="overlay">
				<div class="overlay-panel overlay-left">
					<h1>已有帐号？</h1>
					<p>请使用您的帐号进行登录</p>
					<button class="ghost" id="signIn">登录</button>
				</div>
				<div class="overlay-panel overlay-right">
					<h1>没有帐号？</h1>
					<p>立即注册加入我们，和我们一起开始旅程吧</p>
					<button class="ghost" id="signUp">注册</button>
				</div>
			</div>
		</div>
	</div>
	<script src="js/index.js"></script>
		<script type="text/javascript" color="100,50,100" opacity='1' zIndex="-2" count="99" src="//cdn.bootcss.com/canvas-nest.js/1.0.1/canvas-nest.min.js"></script>
	
</body>

</html>