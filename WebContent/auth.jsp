<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%
String path = request.getContextPath();
String imgpath = request.getParameter("img");
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
	<form method="POST" action="XueXinAuthController">

		username:<input type="text" name="username"><br>
		password:<input type="password" name="password"><br />
		label_id:<input type="text" name="label_id"><br /> captcha:<input
			type="text" name="captcha"><br /> <img src=<%=imgpath %>>

		<br /> <input type="submit" value="Press"> to upload the file!
	</form>
</body>
</html>
