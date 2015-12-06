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
	<form method="POST" enctype="multipart/form-data"
		action="FileUploadUserController?school=lanzhouuniversity&label_id=fdgdsfgdsgdf122334">
		File to :<input type="file" name="upfile"><br /> username:<input
			type="text" name="name"><br /> password:<input
			type="password" name="password"><br>
			 sign :<input
			type="text" name="sign"> <br /> <input type="submit"
			value="Press"> to upload the file!
	</form>
</body>
</html>
