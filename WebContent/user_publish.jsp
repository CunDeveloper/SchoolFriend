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
		action="UserPublishContentController?school=lanzhouuniversity">
		<input type="file" name="upfile1"><br /> <input type="file"
			name="upfile2"><br /> <input type="file" name="upfile3"><br />
		<input type="file" name="upfile4"><br /> <input type="file"
			name="upfile5"><br /> <input type="file" name="upfile6"><br />
		<input type="file" name="upfile7"><br /> <input type="file"
			name="upfile8"><br /> <input type="file" name="upfile9"><br />
		content: <input type="text" name="content"><br /> 
		 id:<input type="text"
			name="user_id"><br /> <br /> <input type="submit"
			value="Press"> to upload the file!
	</form>
</body>
</html>
