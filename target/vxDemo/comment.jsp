
<%@ page language="java" import="java.util.*, java.sql.*,com.soecode.wxDemo.utils.DbHelper" pageEncoding="gb2312"%>

<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    String openid=request.getParameter("openid");
    String ICCID=request.getParameter("iccid");
    String packageName=request.getParameter("packageName");
    String inValidTime=request.getParameter("inValidTime");
    String usedFlow=request.getParameter("usedFlow");
    String leftFlow=request.getParameter("leftFlow");
    String imei=request.getParameter("imei");
    String msg=request.getParameter("msg");
    System.out.println("test " + msg);
    String cardStatus="";
    String djpt="";
    String accessCode="";
    String usedRate=request.getParameter("usedRate");
    String nickname=request.getParameter("nickname");
    System.out.println("inValidTime:" + inValidTime );
    if(ICCID.equals("null"))
    {
        ICCID="";
    }
    if(usedRate==null){
        usedRate="0";
    }
    System.out.println("CardQueryInfo.jsp nickname:" + nickname + " openid:" + openid);
    //连接MySQL数据库
    DbHelper db= new DbHelper();
%>
<!doctype html>
<html class="no-js" lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title>万物互联云平台</title>
    <meta name="description" content="">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- favicon
		============================================ -->
    <link rel="shortcut icon" type="image/x-icon" href="<%= path %>/img/favicon.ico">
    <!-- Google Fonts
		============================================ -->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,700,700i,800" rel="stylesheet">
    <!-- Bootstrap CSS
		============================================ -->
    <link rel="stylesheet" href="<%= path %>/css/bootstrap.min.css">
    <!-- Bootstrap CSS
		============================================ -->
    <link rel="stylesheet" href="<%= path %>/css/font-awesome.min.css">
    <!-- adminpro icon CSS
		============================================ -->
    <link rel="stylesheet" href="<%= path %>/css/adminpro-custon-icon.css">
    <!-- meanmenu icon CSS
		============================================ -->
    <link rel="stylesheet" href="<%= path %>/css/meanmenu.min.css">
    <!-- mCustomScrollbar CSS
		============================================ -->
    <link rel="stylesheet" href="<%= path %>/css/jquery.mCustomScrollbar.min.css">
    <!-- animate CSS
		============================================ -->
    <link rel="stylesheet" href="<%= path %>/css/animate.css">
    <!-- normalize CSS
		============================================ -->
    <link rel="stylesheet" href="<%= path %>/css/normalize.css">
    <!-- form CSS
		============================================ -->
    <link rel="stylesheet" href="<%= path %>/css/form.css">
    <!-- style CSS
		============================================ -->
    <link rel="stylesheet" href="<%= path %>/css/c3.min.css">

    <link rel="stylesheet" href="<%= path %>/style.css">
    <!-- responsive CSS
		============================================ -->
    <link rel="stylesheet" href="<%= path %>/css/responsive.css">
    <!-- modernizr JS
		============================================ -->
    <script src="<%= path %>/js/vendor/modernizr-2.8.3.min.js"></script>
</head>

<body class="darklayout">

<!-- Header top area start-->
<div class="content-inner-all">
    <div class="header-top-area">
        <div class="fixed-header-top">
            <div class="container-fluid">
                <div class="row">
                    <!-- <button type="button" id="sidebarCollapse" class="btn bar-button-pro header-drl-controller-btn btn-info navbar-btn">
                        <i class="fa fa-bars"></i>
                    </button> -->
                    <div  style="text-align: center; ">
                        <a href="#"><img src="<%= path %>/img/logo/log.png" alt="" />
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Header top area end-->
    <!-- Breadcome start-->
    <div class="breadcome-area">
        <div class="container-fluid">
            <div class="row">
                <div class="login-input-head">
                    <p>输入ICCID号</p>
                </div>
                <form enctype="multipart/form-data" method="get" name="cardinfo" action="/ptdj/CardQueryInfo">
                    <input type="hidden" id="openid" name="openid" value="<%=openid%>">
                    <input type="hidden" id="nickname" name="nickname" value="<%=nickname%>">
                    <div class="login-input-area">
                        <div style="text-align: center;"> <input  style="width:60%;" type="text" name="iccid"   placeholder="请输入卡号或ICCID号" value="<%=ICCID%>"  /><div/>
                        <div class="login-button-pro">
                            <div style="text-align: center;"><button id="query" type="button" class="btn1" onclick="queryCard(form)" >查询</button></div>
                        </div>
                    </div>
                <form/>
            </div>
        </div>
    </div>
    <!-- Breadcome End-->
    <!-- Comment Start-->
    <%
        //把表格第二行的显示放到while循环中，就可以根据查询结果画出表格了。参数则放在<td>内的相应位置。
        String sql="SELECT `card_info_ptdj`.`ICCID_CODE` AS `ICCID_CODE`,`card_info_ptdj`.`ACCESS_CODE` AS `ACCESS_CODE`,ifnull( `card_info_ptdj`.`PTID`, '' ) AS `DJPT`,ifnull(`card_info`.`CARD_STATUS`,'') AS `CARD_STATUS`  FROM `card_info_ptdj` LEFT JOIN `card_info` ON `card_info`.`ICCID_CODE` = `card_info_ptdj`.`ICCID_CODE` where  `card_info_ptdj`.`ICCID_CODE`='" + ICCID + "'";
        System.out.println(sql);
        ResultSet rs= db.executeQuery(sql);
        while(rs.next()){
            accessCode = rs.getString("ACCESS_CODE");
            ICCID= rs.getString("ICCID_CODE");
            switch(rs.getString("CARD_STATUS").toString()){
                case "1":
                    cardStatus = "待初始化";
                    break;
                case "2":
                    cardStatus = "使用中";
                    break;
                case "3":
                    cardStatus = "使用中";
                    break;
                case "4":
                    cardStatus = "使用中";
                    break;
                case "6":
                    cardStatus= "停机";
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
                    djpt="卓智达";
                    break;
                case "4":
                    djpt="宝蓝";
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
                    djpt="天横海王星平台";
                    break;
                case "25":
                    djpt="深圳亿点";
                    break;
                default:
                    djpt="公用平台";
                    break;
            }
            //读取
        }
    %>

    <div class="login-form-area mg-t-30 mg-b-40">
        <div class="container-fluid">
            <div class="row">
                <div class="col-lg-3"></div>
                <form id="adminpro-comment-form" class="adminpro-form">
                    <div class="col-lg-6">
                        <div class="login-bg">
                            <div class="row">
                                <div class="login-title">
                                    <h1>卡片查询信息</h1>
                                </div>
                            </div>
                            <!-- custom chart start-->
                            <div class="skill-content-3">
                                <div class="skill">
                                    <div class="progress">
                                        <div class="lead-content">
                                            <h3>剩余流量:<%=leftFlow%>M</h3>
                                            <p>已用流量:<%=usedFlow%>M</p>
                                        </div>
                                        <div class="progress-bar wow fadeInLeft" data-progress="<%=usedRate%>%" style="width: <%=usedRate%>%;" data-wow-duration="1.5s" data-wow-delay="1.2s"> <span><%=usedRate%>%</span>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <!-- custom chart end-->
                            <div class="welcome-wrapper shadow-reset res-mg-t mg-b-30">
                                <div class="adminpro-message-list">
                                    <ul class="message-list-menu">
                                        <li><span class="message-serial message-cl-one">1</span> <span class="message-info">卡号  </span> <span class="message-info-data"><%=accessCode %></span>
                                        </li>
                                        <li><span class="message-serial message-cl-two">2</span> <span class="message-info">ICCID号</span> <span class="message-info-data"><%=ICCID %></span>
                                        </li>
                                        <li><span class="message-serial message-cl-three">3</span> <span class="message-info">卡状态</span> <span class="message-info-data"><%=cardStatus%></span>
                                        </li>
                                        <li><span class="message-serial message-cl-four">4</span> <span class="message-info">平台名称</span> <span class="message-info-data"><%=djpt%></span>
                                        </li>
                                        <li><span class="message-serial message-cl-five">5</span> <span class="message-info">当前套餐</span> <span class="message-info-data"><%=packageName%></span>
                                        </li>
                                        <li><span class="message-serial message-cl-six">6</span> <span class="message-info">IMEI</span> <span class="message-info-data"><%=imei%></span>
                                        </li>
                                        <li><span class="message-serial message-cl-seven">7</span> <span class="message-info">有效期</span> <span class="message-info-data"><%=inValidTime%></span>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                <div class="col-lg-3"></div>
            </div>
        </div>
    </div>
        <div class="login-input-area">
            <div class="login-button-pro">
                <div style="text-align: center;"><button type="submit" class="btn1" formaction= "http://front.iot-chuanglin.com/#/noBindInput">充值续费</button><div/>
            </div>
        </div>
    <div class="col-lg-3"></div>
    <!-- Comment End-->
</div>
</div>
<!-- Footer Start-->

<!-- Footer End-->
<script src="https://cdn.staticfile.org/jquery/3.2.1/jquery.min.js"></script>
<!-- jquery
    ============================================ -->
<%--<script src="<%= path %>/js/vendor/jquery-1.11.3.min.js"></script>--%>
<!-- bootstrap JS
    ============================================ -->
<script src="<%= path %>/js/bootstrap.min.js"></script>
<!-- meanmenu JS
    ============================================ -->
<script src="<%= path %>/js/jquery.meanmenu.js"></script>
<!-- mCustomScrollbar JS
    ============================================ -->
<script src="<%= path %>/js/jquery.mCustomScrollbar.concat.min.js"></script>
<!-- sticky JS
    ============================================ -->
<script src="<%= path %>/js/jquery.sticky.js"></script>
<!-- scrollUp JS
    ============================================ -->
<script src="<%= path %>/js/jquery.scrollUp.min.js"></script>
<!-- form validate JS
    ============================================ -->
<script src="<%= path %>/js/jquery.form.min.js"></script>
<script src="<%= path %>/js/jquery.validate.min.js"></script>
<script src="<%= path %>/js/form-active.js"></script>
<!-- c3 JS
    ============================================ -->
<script src="<%= path %>/js/c3-charts/d3.min.js"></script>
<script src="<%= path %>/js/c3-charts/c3.min.js"></script>
<script src="<%= path %>/js/c3-charts/c3-active.js"></script>
<!-- main JS
    ============================================ -->
<script src="<%= path %>/js/main.js"></script>
</body>
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
    .clicked {
        width:300px;
        line-height: 30px;
        border:1px solid #ccc;
        border-radius:5px;
        <%--background: url('<%= path %>/img/loading.gif');--%>
        background: #0bb20c;
        background-position: center;
        background-repeat: no-repeat;
    }
</style>

<script>
    $(".btn1").click(function() {
        // Instead of directly editing CSS, toggle a class
        $(this).toggleClass("clicked");
    });


    function queryCard(form)
    {
        form.action ='/ptdj/CardQueryInfo'
        document.getElementById("query").style.backgroundImage="url(<%= path %>/img/loading.gif)";
        document.getElementById("query").style.backgroundRepeat = "no-repeat";
        document.getElementById("query").style.backgroundPosition = "center";
        form.submit();
    }

    var jsmsg="<%=msg%>";
    if(jsmsg.length>0)
    {
        alert(jsmsg);
    }

</script>
</html>