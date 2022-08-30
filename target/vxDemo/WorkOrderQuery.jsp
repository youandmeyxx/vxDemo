<%@ page language="java" import="java.util.*, java.sql.*,com.soecode.wxDemo.utils.DbHelper" pageEncoding="gb2312"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String workOrderNo=request.getParameter("workOrderNo");
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
    <title>工单查询</title>
</head>
<%
    //连接MySQL数据库
    DbHelper db= new DbHelper();

%>
<body>
<table>
<tr>
    <td width="75" id="workorderno">工单编号</td>
    <td width="150" id="iccid">ICCID</td>
    <td width="75" id="isok">是否成功</td>
</tr>
<%
    //把表格第二行的显示放到while循环中，就可以根据查询结果画出表格了。参数则放在<td>内的相应位置。

    String sql="SELECT * from card_ptdj_history where WORK_ORDER_NO =" + workOrderNo;
    System.out.println(sql);
    ResultSet rs= db.executeQuery(sql);

    int recNo=0;
    while(rs.next()){
        recNo++;
%>
        <tr>
            <td width="75" ><%=rs.getString("WORK_ORDER_NO") %></td>
            <td width="150" ><%=rs.getString("ICCID_CODE") %></td>
            <td width="75"><%=rs.getString("IS_OK") %></td>
        </tr>
<%
    }
    if (recNo==0){
        sql="SELECT * from card_ptdj where WORK_ORDER_NO =" + workOrderNo;
        System.out.println(sql);
        rs= db.executeQuery(sql);

        recNo=0;
        while(rs.next()){
            recNo++;
%>
    <tr>
        <td width="75" ><%=rs.getString("WORK_ORDER_NO") %></td>
        <td width="150" ><%=rs.getString("ICCID_CODE") %></td>
        <td width="75"><%=rs.getString("IS_OK") %></td>
    </tr>
    <%
            }
    }
//注意"}"的位置 %>
</table>

<%
    rs.close();
    db.close();
%>
</body>
</html>
