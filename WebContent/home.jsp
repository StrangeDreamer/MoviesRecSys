<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="description" content="website description" />
<meta name="keywords" content="website keywords, website keywords" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="style/style.css" />
<title>电影推荐系统 - 推荐方式</title>
</head>
<body>
	<div id="main">
		<div id="header">
			<div id="logo">
				<div id="logo_text">
					<!-- class="logo_colour", allows you to change the colour of the text -->
					<h1>电影推荐系统</h1>

				</div>
			</div>
			<div id="menubar">
				<ul id="menu">
					<!-- put class="selected" in the li tag for the selected page - to highlight which page you're on -->
					<li><a href="index.html">主页</a></li>

				</ul>
			</div>
		</div>
		<div id="content_header"></div>
		<div id="site_content">
			<div id="sidebar_container"></div>
			<div class="sidebar">

				<div class="sidebar_item"></div>

			</div>
			<div class="sidebar"></div>
		</div>
		<div id="content">
			<!-- insert the page content here -->
			<h1>
				欢迎！<%=request.getAttribute("username")%></h1>
			<form action="/CF/recommend" method="post">
				<div class="form_settings">
					选择推荐方式: <br> <br> <br> 
					<input type="radio" name="recommend" value="UserbasedCF">基于用户推荐
					 <br>
					<input type="radio" name="recommend" value="ItembasedCF">基于项目推荐
					<br> 
					<input type="radio" name="recommend" value="HybridCF">混合推荐
					<input type="hidden" name="username"
						value="<%=request.getAttribute("username")%>">
					<p style="padding-top: 15px">
						<span>&nbsp;</span><input class="submit" type="submit"
							name="contact_submitted" value="确定" />
					</p>
				</div>
			</form>

		</div>
	</div>

	</div>
</body>
</html>