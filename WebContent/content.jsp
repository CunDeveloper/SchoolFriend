<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'user_register.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>

<body>
   <h1>查询所有的用户发表的内容</h1>
	<form method="POST" action="UserContentContrller">

		user_id:<input type="text" name="user_id"><br>
		 <input type="hidden" name="lable" value="query_all"><br /> 
		<input type="submit" value="Press">
	</form>
	<br>
	<br>
	<h1>查询自己的所发表的内容</h1>
	<form method="POST" action="UserContentContrller">
		user_id:<input type="text" name="user_id"><br>
		 <input type="hidden" name="lable" value="query_own"><br /> 
		<input type="submit" value="Press">
	</form>
	<br>
	<br>
	<h1>查询另一个用户所发表的内容</h1>
	<form method="POST" action="UserContentContrller">
		  自己的id:<input type="text" name="user_id"><br>
		  拜访者的user_id:<input type="text" name="visit_id">
		 <input type="hidden" name="lable" value="query_another"><br /> 
		<input type="submit" value="Press">
	</form>
</body>
</html>
