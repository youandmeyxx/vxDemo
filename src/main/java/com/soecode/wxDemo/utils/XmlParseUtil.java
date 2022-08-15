package com.soecode.wxDemo.utils;

import org.dom4j.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.List;

public class XmlParseUtil {
    public XmlParseUtil() {
    }

    public static String singleNodeString(Document doc, String attributePath) {
        return doc.selectSingleNode(attributePath).getText();
    }

    public static String getNodeAttrValue(String cadprod,String attributePath,String attrName ) throws DocumentException {
        Document doc = DocumentHelper.parseText(cadprod);
        String attrValu = "";
        List<Node> attrnodelist = doc.selectNodes(attributePath);
        for (Node attrnode : attrnodelist)
        {
            List<Node> attrInfosList = attrnode.selectNodes("attrInfos");
            for(Node attrInfos : attrInfosList) {
                String name = attrInfos.selectSingleNode("attrName").getText();
                if (name.startsWith(attrName)) {
                    attrValu = attrInfos.selectSingleNode("attrValue").getText();
                    return attrValu;
                }
            }
        }
        return null;
    }

    public static <T> T fillSimpleBeanWithNodeOnlyLeafChild(Node nodeHasOnlyLeafChild, T bean) {
        try {
            Document subdoc = DocumentHelper.parseText(nodeHasOnlyLeafChild.asXML());
            List<Element> elements = subdoc.getRootElement().elements();
            Iterator var5 = elements.iterator();

            while(var5.hasNext()) {
                Element element = (Element)var5.next();
                BeanUtil.setProperty(bean, element.getName(), element.getText());
            }
        } catch (InvocationTargetException | DocumentException | IllegalAccessException var6) {
            var6.printStackTrace();
        }

        return bean;
    }
}