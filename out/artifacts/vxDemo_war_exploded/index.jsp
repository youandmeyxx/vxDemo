<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=0.5, maximum-scale=2.0, user-scalable=yes" />
    <title>机卡解绑</title>
</head>
<body>
<main style="background:#fff;text-align:center;">
    <form name="mainForm" method="post" action="/api/Telecom/IMEIReRecord">
        <div class="col-xs-12 qyContent">
            <div class="text-center xx-size col-xs-12 mg-Y-x" style="color:#1288ec;">机卡绑定变更</div>
            <div class="col-xs-12 text-center mg-Y-x"><input type="text" placeholder="请输入要办理的ICCID" id="textcard" name="textcard" style="border:none;outline:none;background-color:#f1f1f1;padding:10px;font-size: 1.2em;width:300px;border-radius:20px;" /></div>
            <!--<div class="col-xs-12 text-center mg-Y-x"><input type="text" placeholder="请输入新的IMEI号前8位或14位" id="ptac" name="ptac" style="border:none;outline:none;background-color:#f1f1f1;padding:10px;font-size: 1.2em;width:300px;border-radius:20px;" /></div>-->
            <div class="col-xs-12 text-center mg-Y-x"><button class="searchBtn" id="searchBtn">提交变更</button></div>
            <!--<div class="col-xs-12 text-center mg-Y-x"><input class="piBtn" id="pibang" onclick="window.location.href='/pibang.html';" value="批量变更" /></div>-->
            <div class="col-xs-12 text-center mg-Y-x" style="color:#f00;font-size:18px;">
                <div>温馨提示：</div>
                <div style="text-align:left;">电信系统机卡绑定解绑的次数每卡每月仅限2次，超过次数需等次月，一个卡号一小时内只能提交一次，请确认好是否换过设备锁卡再解，以免浪费解绑次数！</div>
            </div>
        </div>
    </form>
</main>

<script>
    $(function () {
        //$("#pibang").click(function () {
        //window.location.href='/pibang.html';
        //});

        $(".searchBtn").click(function () {
            var terminalNum = $("#textcard").val();
            if (terminalNum == undefined || terminalNum == '' || terminalNum == ' ' || terminalNum == null) {
                alert('ICCID不能为空');
                return false;
            }

            //var ptac = $("#ptac").val();
            //if (ptac == undefined || ptac == '' || ptac == ' ' || ptac == null) {
            //alert('IMEI段不能为空');
            //return false;
            //}

        });
    });
</script>

<script type="text/javascript" language="javascript">
    var purl=getQueryStringByName("iccid");
    if (purl!="")
    {
        $("#textcard").val(purl);
    }
    function getQueryStringByName(name){
        var result=location.search.match(new RegExp("[\?\&]" + name+ "=([^\&]+)","i"));
        if(result == null || result.length < 1){
            return "";
        }
        else
        {
            return result[1];
        }
    }
</script>
</body>
</html>
