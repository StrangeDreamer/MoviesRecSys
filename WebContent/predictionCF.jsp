<%@page import="java.util.Iterator"%>
<%@page import="java.util.LinkedHashMap"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" 
import ="java.util.Map" import ="java.util.List" import="java.util.Comparator" import="java.util.Map.Entry" 
import="java.util.Collections" import="java.util.ArrayList" import="util.UserItemPair" import="util.MapValueComparator" import="util.Item" %>
    
<!DOCTYPE HTML>
<html>

<head>
  <title>电影推荐系统</title>
  <meta http-equiv="content-type" content="text/html; charset=utf-8" />
  <link rel="stylesheet" type="text/css" href="style/style.css" />
</head>

<body>
  <div id="main">
    <div id="header">
      <div id="logo">
        <div id="logo_text">
        
          <h1>电影推荐系统</h1>
        
        </div>
      </div>
      <div id="menubar">
        <ul id="menu">
          <!-- put class="selected" in the li tag for the selected page - to highlight which page you're on -->
          <li class="selected"><a href="index.html">主页</a></li>
          <li><a href="login.html">登录</a></li>
          
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
           Map<UserItemPair,Double> sortedreccMap=new LinkedHashMap<UserItemPair,Double>();
           List<Map.Entry<UserItemPair,Double>> entryList=new ArrayList<Map.Entry<UserItemPair,Double>>(
           recMap.entrySet());
           Collections.sort(entryList,new MapValueComparator());
           
           Iterator<Map.Entry<UserItemPair,Double>> iter=entryList.iterator();
           Map.Entry<UserItemPair,Double> tmpEntry=null;
           while(iter.hasNext()){
        	   tmpEntry=iter.next();
        	   sortedreccMap.put(tmpEntry.getKey(), tmpEntry.getValue());
           }
        		 /*  
        		  List<Map.Entry<UserItemPair,Double>> recMap = new ArrayList<Map.Entry<UserItemPair,Double>>(map.entrySet());
        	        //然后通过比较器来实现排序
        	        Collections.sort(recMap,new Comparator<Map.Entry<UserItemPair,Double>>() {
        	            //升序排序
        	            public int compare(Entry<UserItemPair, Double> o1,
        	                    Entry<UserItemPair, Double> o2) {
        	                return o1.getValue().compareTo(o2.getValue());
        	            }
        	            
        	        }); */
        %>
			<div id="content">

				<h1>
					用户：<%= request.getAttribute("username") %>您好！
					向你推荐如下电影：
				</h1>
				<table>
					<tr>
						<th>影名</th>
						<th>年份</th>
						<th>评分</th>
						<th>类型</th>
					</tr>
					<tr>
						 <%for(UserItemPair userItemPair: sortedreccMap.keySet()){ 
        
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
