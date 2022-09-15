//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package  com.soecode.wxDemo.telecom.chinatelecom;

import com.soecode.wxDemo.pojo.CardInfo;
import  com.soecode.wxDemo.telecom.chinatelecom.TelecomApi;
import com.soecode.wxDemo.telecom.chinatelecom.util.DesUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.soecode.wxDemo.telecom.chinatelecom.util.JSONUtils;
import com.soecode.wxDemo.utils.HttpGetUtil;
import com.soecode.wxDemo.utils.StringUtil;
import com.soecode.wxDemo.utils.XmlParseUtil;

public abstract class ChinaTelApi {
    protected static String api_queryaware_prefix = "http://api.ct10649.com:9001/m2m_ec/query.do?method=";
    protected static String api_serviceactaware_prefix = "http://api.ct10649.com:9001/m2m_ec/app/serviceAccept.do?method=";

    public ChinaTelApi() {
    }

    abstract String userId();

    abstract String password();

    abstract String key1();

    abstract String key2();

    abstract String key3();


    /**
     *
     */
    public String getCardIpPomainName(CardInfo cardInfo) throws Exception
    {
        String cadprod = apiQuery(cardInfo.getAccessCode(), "prodInstQuery", null);
        String IpPomainName = XmlParseUtil.getNodeAttrValue (cadprod, "/SvcCont/result/prodInfos/funProdInfos","IP/域名");
        return IpPomainName;
    }

    public String apiOffNetQuery(String access_number, String queryMethodName, Map<String, String> extraParameterMap, String action, String quota, String type) {
        try {
            return HttpGetUtil.visitUrlByGet((String)null, this.getOffNetActionQueryRequestUrl(access_number, queryMethodName, extraParameterMap, action, quota, type), false);
        } catch (Exception var8) {
            var8.printStackTrace();
            return null;
        }
    }

    public String apiQuery(String access_number, String queryMethodName, Map<String, String> extraParameterMap) {
        try {
            return HttpGetUtil.visitUrlByGet((String)null, this.getQueryRequestUrl(access_number, queryMethodName, extraParameterMap), false);
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public String apiExecute(String access_number, String queryMethodName, Map<String, String> extraParameterMap, String acctCd, String orderTypeId) {
        try {
            return HttpGetUtil.visitUrlByGet((String)null, this.getExecuteRequestUrl(access_number, queryMethodName, extraParameterMap, acctCd, orderTypeId), false);
        } catch (Exception var7) {
            var7.printStackTrace();
            return null;
        }
    }

    public  String apiServiceAct(String access_number, String queryMethodName, String flowValue, Map<String, String> extraParameterMap) {
        try {
            //return this.getServiceActRequestUrl(access_number, queryMethodName, flowValue, extraParameterMap);
            return HttpGetUtil.visitUrlByGet((String)null, this.getServiceActRequestUrl(access_number, queryMethodName, flowValue, extraParameterMap), false);
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public String[] getQueryPasswordAndSign(String access_number, String methodName) {
        String[] result = new String[2];
        String[] arr = new String[]{access_number, this.userId(), this.password(), methodName};
        new DesUtils();
        String passWord = DesUtils.strEnc(this.password(), this.key1(), this.key2(), this.key3());
        String sign = DesUtils.strEnc(DesUtils.naturalOrdering(arr), this.key1(), this.key2(), this.key3());
        result[0] = passWord;
        result[1] = sign;
        return result;
    }

    public String[] getOffNetActionQueryPasswordAndSign(String access_number, String methodName, String action, String quota, String type) {
        String[] result = new String[2];
        String[] arr = new String[]{access_number, this.userId(), this.password(), action, quota, type, methodName};
        new DesUtils();
        String passWord = DesUtils.strEnc(this.password(), this.key1(), this.key2(), this.key3());
        String sign = DesUtils.strEnc(DesUtils.naturalOrdering(arr), this.key1(), this.key2(), this.key3());
        result[0] = passWord;
        result[1] = sign;
        return result;
    }

    public String[] getExecutePasswordAndSign(String access_number, String methodName, String acctCd, String orderTypeId) {
        String[] result = new String[2];
        String[] arr = new String[]{access_number, this.userId(), this.password(), methodName, acctCd, orderTypeId};
        new DesUtils();
        String passWord = DesUtils.strEnc(this.password(), this.key1(), this.key2(), this.key3());
        String sign = DesUtils.strEnc(DesUtils.naturalOrdering(arr), this.key1(), this.key2(), this.key3());
        result[0] = passWord;
        result[1] = sign;
        return result;
    }

    public String getQueryRequestUrl(String access_number, String methodName, Map<String, String> extraParameterMap) {
        String[] result = this.getQueryPasswordAndSign(access_number, methodName);
        StringBuffer url = new StringBuffer(api_queryaware_prefix);
        url.append(methodName);
        url.append("&access_number=").append(access_number);
        url.append("&userId=").append(this.userId());
        url.append("&passWord=").append(result[0]);
        url.append("&sign=").append(result[1]);
        if (extraParameterMap != null) {
            Iterator extraparamiterator = extraParameterMap.entrySet().iterator();

            while(extraparamiterator.hasNext()) {
                Entry entity = (Entry)extraparamiterator.next();
                url.append("&").append(entity.getKey()).append("=").append(entity.getValue());
            }
        }
        System.out.println(url.toString());
        return url.toString();
    }

    public String getOffNetActionQueryRequestUrl(String access_number, String methodName, Map<String, String> extraParameterMap, String action, String quota, String type) {
        String[] result = this.getOffNetActionQueryPasswordAndSign(access_number, methodName, action, quota, type);
        StringBuffer url = new StringBuffer(api_queryaware_prefix);
        url.append(methodName);
        url.append("&access_number=").append(access_number);
        url.append("&userId=").append(this.userId());
        url.append("&passWord=").append(result[0]);
        url.append("&sign=").append(result[1]);
        if (extraParameterMap != null) {
            Iterator extraparamiterator = extraParameterMap.entrySet().iterator();

            while(extraparamiterator.hasNext()) {
                Entry entity = (Entry)extraparamiterator.next();
                url.append("&").append(entity.getKey()).append("=").append(entity.getValue());
            }
        }

        return url.toString();
    }

    public String getExecuteRequestUrl(String access_number, String methodName, Map<String, String> extraParameterMap, String acctCd, String orderTypeId) {
        String[] result = this.getExecutePasswordAndSign(access_number, methodName, acctCd, orderTypeId);
        StringBuffer url = new StringBuffer(api_serviceactaware_prefix);
        url.append(methodName);
        url.append("&access_number=").append(access_number);
        url.append("&userId=").append(this.userId());
        url.append("&passWord=").append(result[0]);
        url.append("&sign=").append(result[1]);
        if (extraParameterMap != null) {
            Iterator extraparamiterator = extraParameterMap.entrySet().iterator();

            while(extraparamiterator.hasNext()) {
                Entry entity = (Entry)extraparamiterator.next();
                url.append("&").append(entity.getKey()).append("=").append(entity.getValue());
            }
        }

        return url.toString();
    }


    public  String[] getServiceActPasswordAndSign(String iccid_number, String methodName, String flowValue) {
        String[] result = new String[2];
        String[] arr = new String[]{iccid_number, this.userId(),flowValue, this.password(), methodName};
        new DesUtils();
        String passWord = DesUtils.strEnc(this.password(), this.key1(), this.key2(), this.key3());
        String sign = DesUtils.strEnc(DesUtils.naturalOrdering(arr), key1(), key2(), key3());
        result[0] = passWord;
        result[1] = sign;
        return result;
    }



    public  String getServiceActRequestUrl(String iccid_number, String methodName, String flowValue, Map<String, String> extraParameterMap) {
        String[] result = getServiceActPasswordAndSign(iccid_number, methodName, flowValue);
        StringBuffer url = new StringBuffer(api_serviceactaware_prefix);
        url.append(methodName);
        url.append("&iccid=").append(iccid_number);
        url.append("&user_id=").append(this.userId());
        url.append("&passWord=").append(result[0]);
        url.append("&sign=").append(result[1]);
        if (extraParameterMap != null) {
            Iterator extraparamiterator = extraParameterMap.entrySet().iterator();

            while(extraparamiterator.hasNext()) {
                Entry entity = (Entry)extraparamiterator.next();
                url.append("&").append(entity.getKey()).append("=").append(entity.getValue());
            }
        }
        System.out.println(url.toString());
        return url.toString();
    }


    public String IMEIReRecord(String access_number){
        Map<String, String> extraParameterMap = new HashMap();
        extraParameterMap.put("action", "ADD");
        String ReRecord = this.apiServiceAct(access_number,Thread.currentThread().getStackTrace()[1].getMethodName(),extraParameterMap.get("action"),extraParameterMap);
        //Element bytes = ((Element)DocumentHelper.parseText(ReRecord).getRootElement().elements().get(0)).element("RspDesc");
        //String used = bytes.getTextTrim();
        return ReRecord;
    }


    public String getOnlineStatus(String access_number){
        Map<String, String> extraParameterMap = new HashMap();
        Map<String, Object> onlineStatusMap=null;
        String onlineStatus = this.apiQuery(access_number, Thread.currentThread().getStackTrace()[1].getMethodName(), extraParameterMap);
        if (onlineStatus != null ) {
            onlineStatusMap = (HashMap<String, Object>) JSONUtils.jsonParse(onlineStatus);
            if(onlineStatusMap.get("imei")!=null) {
                onlineStatus = onlineStatusMap.get("imei").toString();
                return onlineStatus;
            }
        }
        onlineStatus="";
        return onlineStatus;
    }


//    public String queryTraffic(String access_number, boolean withDetail) throws DocumentException {
//        Map<String, String> extraParameterMap = new HashMap();
//        extraParameterMap.put("needDtl", withDetail ? "1" : "0");
//        String traffic = this.apiQuery(access_number, Thread.currentThread().getStackTrace()[1].getMethodName(), extraParameterMap);
//        if (traffic != null && traffic.startsWith("100008")) {
//            extraParameterMap.put("needDtl", "0");
//            traffic = this.apiQuery(access_number, Thread.currentThread().getStackTrace()[1].getMethodName(), extraParameterMap);
//        }
//
//        Element bytes = ((Element)DocumentHelper.parseText(traffic).getRootElement().elements().get(0)).element("TOTAL_BYTES_CNT");
//        String used = bytes.getTextTrim();
//        return used.replace("MB", "");
//    }

    public String prodInstQuery(String access_number) {
        return this.apiQuery(access_number, Thread.currentThread().getStackTrace()[1].getMethodName(), (Map)null);
    }

    public String requestServActive(String access_number) {
        return this.apiQuery(access_number, Thread.currentThread().getStackTrace()[1].getMethodName(), (Map)null);
    }


//    @Override
//    public String offNetAction(String access_number, long quota) {
//        Map<String, String> map = new HashMap();
//        map.put("type", "1");
//        map.put("action", "2");
//        map.put("quota", String.valueOf(quota));
//        return this.apiOffNetQuery(access_number, Thread.currentThread().getStackTrace()[1].getMethodName(), map, (String)map.get("quota"), (String)map.get("action"), (String)map.get("type"));
//    }
//
//    public String getCardStatusName(String access_number) throws DocumentException {
//        Document doc = DocumentHelper.parseText(this.prodInstQuery(access_number));
//        String prodStatusName ="";
//        return prodStatusName;
//    }
//
//    public boolean isThisCardRun(String access_number) {
//        try {
//            return this.getCardStatusName(access_number).equalsIgnoreCase("在用");
//        } catch (Exception var3) {
//            return false;
//        }
//    }

//    public boolean isOffNet(String access_number) {
//        boolean isOffNet = false;
//
//        try {
//            new ArrayList();
//            String cadprod = this.prodInstQuery(access_number);
//            Document doc = DocumentHelper.parseText(cadprod);
//            List<Node> nodelist = doc.selectNodes("/SvcCont/result/prodInfos/funProdInfos/attrInfos");
//            Iterator var8 = nodelist.iterator();
//
//            while(var8.hasNext()) {
//                Node attrnode = (Node)var8.next();
//                String name = attrnode.selectSingleNode("attrName").getText();
//                if (name.startsWith("是否已达量断网")) {
//                    String value = attrnode.selectSingleNode("attrValue").getText();
//                    isOffNet = value.equals("是");
//                    break;
//                }
//            }
//        } catch (Exception var11) {
//            var11.printStackTrace();
//        }
//
//        return isOffNet;
//    }

//    @Override
//    public long getOffNetQuota(String access_number) throws Exception {
//        long quota = 0L;
//        new ArrayList();
//        String cadprod = this.prodInstQuery(access_number);
//        Document doc = DocumentHelper.parseText(cadprod);
//        List<Node> attrnodelist = doc.selectNodes("/SvcCont/result/prodInfos/funProdInfos/attrInfos");
//        Iterator var9 = attrnodelist.iterator();
//
//        while(var9.hasNext()) {
//            Node attrnode = (Node)var9.next();
//            String name = attrnode.selectSingleNode("attrName").getText();
//            if (name.startsWith("断网阀值")) {
//                String value = attrnode.selectSingleNode("attrValue").getText();
//                quota = Long.parseLong(value);
//                break;
//            }
//        }
//
//        return quota;
//    }

//    public boolean isThisPackageFlowOrdered(String access_number, String flowValue) {
//        boolean isOrdered = false;
//
//        try {
//            List<Node> nodelist = DocumentHelper.parseText(this.prodInstQuery(access_number)).selectNodes("/SvcCont/result/prodInfos/prodOfferInfos");
//            Iterator var6 = nodelist.iterator();
//
//            while(var6.hasNext()) {
//                Node node = (Node)var6.next();
//                if (!StringUtil.isEmpty(node.selectSingleNode("prodOfferNbr")) && !StringUtil.isEmpty(node.selectSingleNode("prodOfferNbr").getText()) && flowValue.equals(node.selectSingleNode("prodOfferNbr").getText())) {
//                    isOrdered = true;
//                }
//            }
//        } catch (Exception var7) {
//            var7.printStackTrace();
//        }
//
//        return isOrdered;
//    }

//    public boolean isThisPackageFlowUnScribed(String access_number, String flowValue) {
//        boolean isUnscribed = false;
//
//        try {
//            boolean existNbr = false;
//            List<Node> nodelist = DocumentHelper.parseText(this.prodInstQuery(access_number)).selectNodes("/SvcCont/result/prodInfos/prodOfferInfos");
//            Iterator var7 = nodelist.iterator();
//
//            while(var7.hasNext()) {
//                Node node = (Node)var7.next();
//                if (!StringUtil.isEmpty(node.selectSingleNode("prodOfferNbr")) && !StringUtil.isEmpty(node.selectSingleNode("prodOfferNbr").getText()) && flowValue.equals(node.selectSingleNode("prodOfferNbr").getText()) && "将失效".equalsIgnoreCase(node.selectSingleNode("statusName").getText())) {
//                    return true;
//                }
//
//                if (!StringUtil.isEmpty(node.selectSingleNode("prodOfferNbr")) && !StringUtil.isEmpty(node.selectSingleNode("prodOfferNbr").getText()) && flowValue.equals(node.selectSingleNode("prodOfferNbr").getText()) && "有效".equalsIgnoreCase(node.selectSingleNode("statusName").getText())) {
//                    existNbr = true;
//                }
//            }
//
//            if (!existNbr) {
//                isUnscribed = true;
//            }
//        } catch (Exception var8) {
//            var8.printStackTrace();
//        }
//
//        return isUnscribed;
//    }

    public String queryCardMainStatus(String access_number) {
        return this.apiQuery(access_number, Thread.currentThread().getStackTrace()[1].getMethodName(), (Map)null);
    }

//    public boolean activateCard(CardInfo cardinfo) throws Exception {
//        this.requestServActive(cardinfo.getACCESS_CODE());
//        return true;
//    }

//    public boolean controlNetworkSpeed(CardDao arg0, CardInfo arg1, long arg2) {
//        return false;
//    }

//    public boolean disabledNumber(CardInfo cardinfo) throws Exception {
//        return true;
//    }
//
//    public boolean enableNumber(CardInfo cardinfo) throws Exception {
//        return true;
//    }
//
//    public long getStatus(CardInfo arg0) {
//        return 0L;
//    }
//
//    public boolean initOperation(CardInfo arg0) throws Exception {
//        return true;
//    }
//
//    public String locationLastTime(CardInfo arg0) throws Exception {
//        return null;
//    }
//
//    public String locationRealTime(CardInfo arg0) throws Exception {
//        return null;
//    }
//
//    public boolean suspendAndKeepNumber(CardInfo arg0) throws Exception {
//        return false;
//    }
}
