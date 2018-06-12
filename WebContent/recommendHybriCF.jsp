<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"
import ="java.util.Map" import="util.UserItemPair" import="util.Item" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Movie Recommender System</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="style/style.css" />

</head>
<body>
  <div id="main">
    <div id="header">
      <div id="logo">
        <div id="logo_text">
        
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
        <div class="sidebar">
         
         
          
        </div>
      </div>
        <% Map<UserItemPair,Double > recMap =(Map<UserItemPair,Double >)request.getAttribute("recommendation"); 
           Map<Integer,Item> items= (Map<Integer,Item>)request.getAttribute("items");    		 
        %>
			<div id="content">

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
					<tr>
						<%for(UserItemPair userItemPair: recMap.keySet()){
        
        %>
						<td><%=items.get(userItemPair.getItemId()).getName() %></td>
						<td><%=items.get(userItemPair.getItemId()).getYear() %></td>
						<td><%=recMap.get(userItemPair) %></td>
						<td><%=items.get(userItemPair.getItemId()).getGenre()%></td>
					</tr>
					<%} %>
				</table>

			</div></body>
</html>