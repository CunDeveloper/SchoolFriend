<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
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
	<form method="POST" action="UserContentContrller">
        school:<input type="text" name="lanzhouuniversity">
		conID:<input type="text" name="con_id"><br> containImage:<input
			type="text" name="is_contain_image"><br /> ImageName1:<input
			type="text" name="img_name1"><br> ImageName2:<input
			type="text" name="img_name2"><br> ImageName3:<input
			type="text" name="img_name3"><br> ImageName4:<input
			type="text" name="img_name4"><br> ImageName5:<input
			type="text" name="img_name5"><br> ImageName6:<input
			type="text" name="img_name6"><br> ImageName7:<input
			type="text" name="img_name7"><br> ImageName8:<input
			type="text" name="img_name8"><br> ImageName9:<input
			type="text" name="img_name9"><br> <br /> <input
			type="submit" value="Press"> to upload the file!
	</form>
</body>
</html>
