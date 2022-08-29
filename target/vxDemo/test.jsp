
<%@ page language="java" import="java.util.*, java.sql.*,com.soecode.wxDemo.utils.DbHelper" %>
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<%
    String openid=request.getParameter("openid");
    String nickname=request.getParameter("nickname");
    String msg=request.getParameter("msg");
    System.out.println("test " + msg);
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
    <title>平台变更登记</title>
</head>
<body>
<%
    //连接MySQL数据库
    DbHelper db= new DbHelper();
%>
<main style="background:#fff;text-align:center;">
    <form name="mainForm" method="get" action="/ptdj/submit" target="hidden_frame" >
        <input type="hidden" id="openid" name="openid" value="<%=openid%>">
        <input type="hidden" id="nickname" name="nickname" value="<%=nickname%>">
        <textarea id="iccid" name="iccid" rows="16" style="width:300px" placeholder="请输入卡号或ICCID号(一行一个)。连号的用#连接，比如：8986111920403305000#8986111920403305999，每次最多提交250张" ></textarea><br><br>
        <select id="djpt" name="djpt" style="width:300px" >
            <option value="0">请选择...</option>
            <%
                //把表格第二行的显示放到while循环中，就可以根据查询结果画出表格了。参数则放在<td>内的相应位置。
                String sql="select * from card_ptdj_ptinfo";
                ResultSet rs= db.executeQuery(sql);
                while(rs.next()){%>
            <option value="<%=rs.getString("PTID") %>"><%=rs.getString("PT_NAME") %></option>
            <%}
//注意"}"的位置 %>
        </select><br><br>

        <button type="submit" class="btn">确认提交</button><br><br>
        <button type="submit" class="btn1" formaction="/ptdj/Query">查询记录</button><br>
    </form>

</main>

<script type="text/javascript">
    var jsmsg="<%=msg%>";
    if(jsmsg.length>0)
    {
        alert(jsmsg);
    }
</script>

<style type="text/css" >
    .btn{
        background: burlywood;
        width:300px;
        line-height: 30px;
        border:1px solid #ccc;
        border-radius:5px;
    }
    .btn:hover{
        background: bisque;
    }
    .btn1{
        background: cadetblue;
        width:300px;
        line-height: 30px;
        border:1px solid #ccc;
        border-radius:5px;
    }
    .btn1:hover{
        background: aquamarine;
    }
</style>
</body>
</html>


