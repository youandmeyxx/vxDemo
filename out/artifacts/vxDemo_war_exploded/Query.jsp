<%@ page language="java" import="java.util.*, java.sql.*,com.soecode.wxDemo.utils.DbHelper" pageEncoding="gb2312"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String openid=request.getParameter("openid");
    String nickname=request.getParameter("nickname");
%>
<%request.setCharacterEncoding("UTF-8");%>
<%response.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<style type="text/css">
    table{ width:300px; margin:auto; padding: 5px; border:0px; background:#00CCFF;}
    tr{ background:#fff;}
    td{ padding: 5px;}
    #title{ text-align:center;}
</style>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
    <title>查询结果</title>
</head>

<body>
<%
    //连接MySQL数据库
    DbHelper db= new DbHelper();
%>
<table >
    <tr>
        <td width="75" id="djpt">登记平台</td>
        <td width="150" id="iccid">ICCID</td>
        <td width="75" id="nickname">登记用户名</td>
    </tr>

    <%
        //把表格第二行的显示放到while循环中，就可以根据查询结果画出表格了。参数则放在<td>内的相应位置。
        String sql="SELECT card_info_ptdj.*, card_ptdj_ptinfo.PT_NAME FROM card_info_ptdj INNER JOIN card_ptdj_ptinfo ON  card_info_ptdj.PTID = card_ptdj_ptinfo.PTID where OPEN_ID='" + openid + "'";
        ResultSet rs= db.executeQuery(sql);
        while(rs.next()){%>

    <tr>
        <td width="75" ><%=rs.getString("PT_NAME") %></td>
        <td width="150" ><%=rs.getString("ICCID_CODE") %></td>
        <td width="75"><%=rs.getString("NICK_NAME") %></td>
    </tr>

    <%}
//注意"}"的位置 %>
</table>

<%
    rs.close();
    db.close();
%>
</body>
</html>
