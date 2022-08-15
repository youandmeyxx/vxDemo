
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%
    String IMEIReRecordstrback=request.getParameter("IMEIReRecordstrback");
%>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Title</title>
</head>
<body>
<div>操作反馈：</div>
<div style="text-align:center;"><%=IMEIReRecordstrback%></div>
</body>
</html>