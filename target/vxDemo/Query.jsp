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
    <title>��ѯ���</title>
</head>

<body>
<%
    //����MySQL���ݿ�
    DbHelper db= new DbHelper();
%>
<table >
    <tr>
        <td width="75" id="djpt">�Ǽ�ƽ̨</td>
        <td width="150" id="iccid">ICCID</td>
        <td width="75" id="nickname">�Ǽ��û���</td>
    </tr>

    <%
        //�ѱ��ڶ��е���ʾ�ŵ�whileѭ���У��Ϳ��Ը��ݲ�ѯ�����������ˡ����������<td>�ڵ���Ӧλ�á�
        String sql="SELECT card_info_ptdj.*, card_ptdj_ptinfo.PT_NAME FROM card_info_ptdj INNER JOIN card_ptdj_ptinfo ON  card_info_ptdj.PTID = card_ptdj_ptinfo.PTID where OPEN_ID='" + openid + "'";
        ResultSet rs= db.executeQuery(sql);
        while(rs.next()){%>

    <tr>
        <td width="75" ><%=rs.getString("PT_NAME") %></td>
        <td width="150" ><%=rs.getString("ICCID_CODE") %></td>
        <td width="75"><%=rs.getString("NICK_NAME") %></td>
    </tr>

    <%}
//ע��"}"��λ�� %>
</table>

<%
    rs.close();
    db.close();
%>
</body>
</html>
