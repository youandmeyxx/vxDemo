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
    <title>卡片查询</title>
</head>
<body>
<%
    //连接MySQL数据库
    DbHelper db= new DbHelper();
%>
<main style="background:#fff;text-align:center;">
    <form enctype="multipart/form-data" method="get" name="cardinfo" action="/ptdj/CardQueryInfo">
        <input type="hidden" id="openid" name="openid" value="<%=openid%>">
        <input type="hidden" id="nickname" name="nickname" value="<%=nickname%>">
        &nbsp;<input name="ICCID" class="btn1" placeholder="请输入卡号或ICCID号" ><input type="submit" class="btn" value="查询" name="bntQuery">
    </form>
    <%
        //把表格第二行的显示放到while循环中，就可以根据查询结果画出表格了。参数则放在<td>内的相应位置。
        String sql="SELECT `card_info_ptdj`.`ICCID_CODE` AS `ICCID_CODE`,`card_info_ptdj`.`ACCESS_CODE` AS `ACCESS_CODE`,ifnull( `card_info_ptdj`.`PTID`, '' ) AS `DJPT`,ifnull(`card_info`.`CARD_STATUS`,'') AS `CARD_STATUS`  FROM `card_info_ptdj` LEFT JOIN `card_info` ON `card_info`.`ICCID_CODE` = `card_info_ptdj`.`ICCID_CODE` where  `card_info_ptdj`.`ICCID_CODE`='" + ICCID + "'";
        System.out.println(sql);
        ResultSet rs= db.executeQuery(sql);
        while(rs.next()){
            switch(rs.getString("CARD_STATUS").toString()){
                case "1":
                    cardStatus = "待初始化";
                    break;
                case "2":
                    cardStatus = "待启用";
                    break;
                case "3":
                    cardStatus = "待实名审核";
                    break;
                case "4":
                    cardStatus = "使用中";
                    break;
                default:
                    break;
            }

            switch(rs.getString("DJPT").toString()){
                case "1":
                    djpt="公网时代";
                    break;
                case "2":
                    djpt="博纳德";
                    break;
                case "3":
                    djpt="卓志达";
                    break;
                case "4":
                    djpt="河南宝蓝";
                    break;
                case "5":
                    djpt="善理";
                    break;
                case "6":
                    djpt="易锐通";
                    break;
                case "7":
                    djpt="天翼达(云洽)";
                    break;
                case "8":
                    djpt="中兴高达";
                    break;
                case "9":
                    djpt="茶余";
                    break;
                case "10":
                    djpt="心立通";
                    break;
                case "11":
                    djpt="特易通TYT";
                    break;
                case "12":
                    djpt="天龙世纪";
                    break;
                case "13":
                    djpt="iwalkie平台";
                    break;
                case "14":
                    djpt="联想平台";
                    break;
                case "15":
                    djpt="三通平台";
                    break;
                case "16":
                    djpt="芯平台";
                    break;
                case "17":
                    djpt="瑞特平台";
                    break;
                case "18":
                    djpt="南极星";
                    break;
                case "19":
                    djpt="力同";
                    break;
                case "20":
                    djpt="易锐通";
                    break;
                case "21":
                    djpt="天横";
                    break;
                case "22":
                    djpt="中瑞科";
                    break;
                case "23":
                    djpt="云鑫城";
                    break;
                case "24":
                    djpt="海王星";
                    break;
                case "25":
                    djpt="深圳亿点";
                    break;
                default:
                    djpt="公用平台";
                    break;


            }
    %>
    <table>
        <tr >
            <td colspan="2">卡信息</td>
        </tr>
        <tr>
            <td style="width:100px" >卡号</td>
            <td style="width:200px"><%=rs.getString("ACCESS_CODE") %></td>
        </tr>
        <tr>
            <td style="width:100px">ICCID</td>
            <td style="width:200px"><%=rs.getString("ICCID_CODE") %></td>
        </tr>
        <tr>
            <td style="width:100px">卡状态</td>
            <td style="width:200px"><%=cardStatus%></td>
        </tr>
        <tr>
            <td style="width:100px">平台名称</td>
            <td style="width:200px"><%=djpt%></td>
<%--            <td style="width:200px"><%=djpt%></td>--%>
        </tr>
    </table>
    <%}
//注意"}"的位置 %>

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
