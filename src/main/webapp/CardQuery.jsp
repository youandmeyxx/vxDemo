<%--
  Created by IntelliJ IDEA.
  User: zhangcao
  Date: 2022-01-11
  Time: 20:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*, java.sql.*,com.soecode.wxDemo.utils.DbHelper" pageEncoding="gb2312"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String openid=request.getParameter("openid");
    String ICCID=request.getParameter("ICCID");
    String cardStatus="";
    String djpt="";
    String nickname=request.getParameter("nickname");
    System.out.println("CardQueryInfo.jsp nickname:" + nickname + " openid:" + openid);
%>

<%request.setCharacterEncoding("UTF-8");%>
<%response.setCharacterEncoding("UTF-8");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
    <title>��Ƭ��ѯ</title>
</head>
<body>
<%
    //����MySQL���ݿ�
    DbHelper db= new DbHelper();
%>
<main style="background:#fff;text-align:center;">
    <form enctype="multipart/form-data" method="get" name="cardinfo" action="/ptdj/CardQueryInfo">
        <input type="hidden" id="openid" name="openid" value="<%=openid%>">
        <input type="hidden" id="nickname" name="nickname" value="<%=nickname%>">
        &nbsp;<input name="ICCID" class="btn1" placeholder="�����뿨�Ż�ICCID��" ><input type="submit" class="btn" value="��ѯ" name="bntQuery">
    </form>
    <%
        //�ѱ��ڶ��е���ʾ�ŵ�whileѭ���У��Ϳ��Ը��ݲ�ѯ�����������ˡ����������<td>�ڵ���Ӧλ�á�
        String sql="SELECT `card_info_ptdj`.`ICCID_CODE` AS `ICCID_CODE`,`card_info_ptdj`.`ACCESS_CODE` AS `ACCESS_CODE`,ifnull( `card_info_ptdj`.`PTID`, '' ) AS `DJPT`,ifnull(`card_info`.`CARD_STATUS`,'') AS `CARD_STATUS`  FROM `card_info_ptdj` LEFT JOIN `card_info` ON `card_info`.`ICCID_CODE` = `card_info_ptdj`.`ICCID_CODE` where  `card_info_ptdj`.`ICCID_CODE`='" + ICCID + "'";
        System.out.println(sql);
        ResultSet rs= db.executeQuery(sql);
        while(rs.next()){
            switch(rs.getString("CARD_STATUS").toString()){
                case "1":
                    cardStatus = "����ʼ��";
                    break;
                case "2":
                    cardStatus = "������";
                    break;
                case "3":
                    cardStatus = "��ʵ�����";
                    break;
                case "4":
                    cardStatus = "ʹ����";
                    break;
                default:
                    break;
            }

            switch(rs.getString("DJPT").toString()){
                case "1":
                    djpt="����ʱ��";
                    break;
                case "2":
                    djpt="���ɵ�";
                    break;
                case "3":
                    djpt="׿־��";
                    break;
                case "4":
                    djpt="���ϱ���";
                    break;
                case "5":
                    djpt="����";
                    break;
                case "6":
                    djpt="����ͨ";
                    break;
                case "7":
                    djpt="�����(��Ǣ)";
                    break;
                case "8":
                    djpt="���˸ߴ�";
                    break;
                case "9":
                    djpt="����";
                    break;
                case "10":
                    djpt="����ͨ";
                    break;
                case "11":
                    djpt="����ͨTYT";
                    break;
                case "12":
                    djpt="��������";
                    break;
                case "13":
                    djpt="iwalkieƽ̨";
                    break;
                case "14":
                    djpt="����ƽ̨";
                    break;
                case "15":
                    djpt="��ͨƽ̨";
                    break;
                case "16":
                    djpt="оƽ̨";
                    break;
                case "17":
                    djpt="����ƽ̨";
                    break;
                case "18":
                    djpt="�ϼ���";
                    break;
                case "19":
                    djpt="��ͬ";
                    break;
                case "20":
                    djpt="����ͨ";
                    break;
                case "21":
                    djpt="���";
                    break;
                case "22":
                    djpt="�����";
                    break;
                case "23":
                    djpt="���γ�";
                    break;
                case "24":
                    djpt="������";
                    break;
                case "25":
                    djpt="�����ڵ�";
                    break;
                default:
                    djpt="����ƽ̨";
                    break;


            }
    %>
    <table>
        <tr >
            <td colspan="2">����Ϣ</td>
        </tr>
        <tr>
            <td style="width:100px" >����</td>
            <td style="width:200px"><%=rs.getString("ACCESS_CODE") %></td>
        </tr>
        <tr>
            <td style="width:100px">ICCID</td>
            <td style="width:200px"><%=rs.getString("ICCID_CODE") %></td>
        </tr>
        <tr>
            <td style="width:100px">��״̬</td>
            <td style="width:200px"><%=cardStatus%></td>
        </tr>
        <tr>
            <td style="width:100px">ƽ̨����</td>
            <td style="width:200px"><%=djpt%></td>
<%--            <td style="width:200px"><%=djpt%></td>--%>
        </tr>
    </table>
    <%}
//ע��"}"��λ�� %>

</main>

<style type="text/css" >
    table{ width:300px; margin: auto; padding: 5px; border:0px; background: #f1f1f1;}
    tr{ background:#fff;}
    td{  padding: 5px;}
    #title{ text-align:center;}

    .btn{
        background: #1288ec;
        width:80px;
        line-height: 30px;
        border:1px solid #ccc;
        border-radius:5px;
    }
    .btn:hover{
        background: #00CCFF;
    }
    .btn1{
        background: bisque;
        width:200px;
        line-height: 30px;
        border:1px solid #ccc;
        border-radius:5px;
    }
    .btn1:hover{
        background: antiquewhite;
    }
</style>
</body>
</html>
