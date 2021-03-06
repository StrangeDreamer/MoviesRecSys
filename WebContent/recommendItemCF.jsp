<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8" import="util.Item" import="util.UserItemPair"
	import="java.io.BufferedReader" import="java.io.InputStreamReader"
	import="java.util.HashMap" import="java.util.Map"%>

<!DOCTYPE HTML>
<html>

<head>
<title>Movie Recommender System</title>
<meta name="description" content="website description" />
<meta name="keywords" content="website keywords, website keywords" />
<meta http-equiv="content-type"
	content="text/html; charset=windows-1252" />
<link rel="stylesheet" type="text/css" href="style/style.css" />
</head>

<body>
	<div id="main">
		<div id="header">
			<div id="logo">
				<div id="logo_text">
					<!-- class="logo_colour", allows you to change the colour of the text -->
					<h1>Movie Recommender System</h1>

				</div>
			</div>
			<div id="menubar">
				<ul id="menu">
					<!-- put class="selected" in the li tag for the selected page - to highlight which page you're on -->
					<li class="selected"><a href="index.html">Home</a></li>
					<li><a href="login.html">Login</a></li>

				</ul>
			</div>
		</div>
		<div id="content_header"></div>
		<div id="site_content">
			<div id="sidebar_container">
				<div class="sidebar"></div>
			</div>
			<div id="content">
				<!-- insert the page content here -->
				<h1>
					Hello User!!<%= request.getAttribute("username") %>
					Movies that might interest you
				</h1>
				<table>
					<tr>

						<th>影名</th>
						<th>年份</th>
						<th>评分</th>
						<th>类型</th>
					</tr>

					<%
						Map<UserItemPair, Double> recMap = (Map<UserItemPair, Double>) request.getAttribute("recommendation");
						Map<Integer, Item> items = (Map<Integer, Item>) request.getAttribute("items");
					%>

					<tr>
						<%
							for (UserItemPair userItemPair : recMap.keySet()) {
						%>

						<td><%=items.get(userItemPair.getItemId()).getName()%></td>
						<td><%=items.get(userItemPair.getItemId()).getYear()%></td>
						<td><%=recMap.get(userItemPair)%></td>
						<td><%=items.get(userItemPair.getItemId()).getGenre()%></td>
					</tr>
					<%
						}
					%>
				</table>
				<!--  <p>This standards compliant, simple, fixed width website template is released as an 'open source' design (under a <a href="http://creativecommons.org/licenses/by/3.0">Creative Commons Attribution 3.0 Licence</a>), which means that you are free to download and use it for anything you want (including modifying and amending it). All I ask is that you leave the 'design from HTML5webtemplates.co.uk' link in the footer of the template, but other than that...</p>
        <p>This template is written entirely in <strong>HTML5</strong> and <strong>CSS</strong>, and can be validated using the links in the footer.</p>
        <p>You can view more free HTML5 web templates <a href="http://www.html5webtemplates.co.uk">here</a>.</p>
        <p>This template is a fully functional 5 page website, with an <a href="examples.html">examples</a> page that gives examples of all the styles available with this design.</p>
        <h2>Browser Compatibility</h2>
        <p>This template has been tested in the following browsers:</p>
        <ul>
          <li>Internet Explorer 8</li>
          <li>Internet Explorer 7</li>
          <li>FireFox 3.5</li>
          <li>Google Chrome 6</li>
          <li>Safari 4</li>
        </ul>
      </div>
      -->
			</div>
</body>
</html>
