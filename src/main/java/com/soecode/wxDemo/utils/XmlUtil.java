package com.soecode.wxDemo.utils;

import org.apache.commons.lang3.ClassUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XmlUtil {
    public XmlUtil() {
    }

    public static void writeDocument(Document document, String outFile) throws Exception {
        String charsetName = "UTF-8";
        writeDocument(document, outFile, charsetName);
    }

    public static void writeDocument(Document document, String outFile, String charsetName) throws Exception {
        OutputStreamWriter fileWriter = new OutputStreamWriter(new FileOutputStream(outFile), charsetName);
        OutputFormat xmlFormat = new OutputFormat();
        xmlFormat.setNewlines(true);
        xmlFormat.setIndent(true);
        xmlFormat.setIndent("\t");
        xmlFormat.setEncoding(charsetName);
        XMLWriter xmlWriter = new XMLWriter(fileWriter, xmlFormat);
        xmlWriter.setEscapeText(false);
        xmlWriter.write(document);
        xmlWriter.close();
    }

    public static Document readDocument(String xmlfile, String encoding) throws Exception {
        Document document = null;
        File file = new File(xmlfile);
        if (file.isFile()) {
            SAXReader reader = new SAXReader();
            reader.setEncoding(encoding);
            document = reader.read(file);
        }

        return document;
    }

//    public static Document readDocumentFromClasspath(String xmlfile) {
//        InputStream isr = ResultConfigBuilder.class.getResourceAsStream(xmlfile);
//        SAXReader readerr = new SAXReader();
//        Document document = null;
//
//        try {
//            document = readerr.read(isr);
//        } catch (DocumentException var5) {
//            var5.printStackTrace();
//        }
//
//        return document;
//    }

    public static Document readDocument(String xmlfile) throws Exception {
        String encoding = "UTF-8";
        return readDocument(xmlfile, encoding);
    }

    public static String toString(Document document) {
        try {
            document.setXMLEncoding("UTF-8");
            StringWriter writer = new StringWriter(1024);
            document.write(writer);
            String result = writer.toString();
            return result;
        } catch (IOException var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static String toFormatString(Document document) {
        try {
            document.setXMLEncoding("UTF-8");
            OutputFormat xmlFormat = new OutputFormat();
            xmlFormat.setNewlines(false);
            xmlFormat.setIndent(true);
            xmlFormat.setIndent("\t");
            StringWriter writer = new StringWriter(4096);
            XMLWriter xmlWriter = new XMLWriter(writer, xmlFormat);
            xmlWriter.setEscapeText(false);
            xmlWriter.write(document);
            xmlWriter.close();
            String result = writer.toString();
            return result;
        } catch (IOException var5) {
            var5.printStackTrace();
            return null;
        }
    }

    public static String parseTagValueByXmlString(String xmlString, String tagName) {
        StringBuffer qualifiedXmlString = new StringBuffer("<root>");
        qualifiedXmlString.append(xmlString).append("</root>");
        Document doc = null;

        try {
            doc = DocumentHelper.parseText(qualifiedXmlString.toString());
        } catch (DocumentException var6) {
            var6.printStackTrace();
        }

        Element root = doc.getRootElement();
        Element tagElement = root.element(tagName);
        return tagElement.getText();
    }

    public static Element parseVirtualRootElementByXmlString(String xmlString) {
        StringBuffer qualifiedXmlString = new StringBuffer("<root>");
        qualifiedXmlString.append(xmlString).append("</root>");
        Document doc = null;

        try {
            doc = DocumentHelper.parseText(qualifiedXmlString.toString());
        } catch (DocumentException var4) {
            var4.printStackTrace();
        }

        return doc.getRootElement();
    }

//    public static void generateDefaultXmlObjectForEntityArrayListAsList(Element root, PagableList pagableList, String entityElementName) {
//        List<BaseEntity[]> entityList = pagableList.getList();
//        root.addElement("totalSize").setText(String.valueOf(pagableList.getFullListSize()));
//        root.addElement("pageSize").setText(String.valueOf(pagableList.getObjectsPerPage()));
//        root.addElement("curPageNum").setText(String.valueOf(pagableList.getPageNumber()));
//        root.addElement("totalPageNum").setText(String.valueOf(pagableList.getTotalPageNum()));
//        List<Integer> indexList = pagableList.getAvailablePageIndexes();
//        Iterator var6 = indexList.iterator();
//
//        while(var6.hasNext()) {
//            Integer pageIndex = (Integer)var6.next();
//            root.addElement("availPageIndexes").setText(String.valueOf(pageIndex));
//        }
//
//        generateDefaultXmlObjectForEntityArrayListAsList(root, entityList, entityElementName);
//    }

//    private static void generateDefaultXmlObjectForEntityArrayListAsList(Element root, List<BaseEntity[]> entityList, String entityElementName) {
//        int listSize = entityList.size();
//        root.addElement("curSize").setText(String.valueOf(listSize));
//        if (listSize > 0) {
//            int index = 1;
//            Iterator var6 = entityList.iterator();
//
//            while(var6.hasNext()) {
//                Object[] entityarr = (Object[])var6.next();
//                Element entityElement = root.addElement(!StringUtil.isEmpty(entityElementName) ? entityElementName : "entityList");
//                entityElement.addElement("seq").setText(String.valueOf(index++));
//                Object[] var11 = entityarr;
//                int var10 = entityarr.length;
//
//                for(int var9 = 0; var9 < var10; ++var9) {
//                    Object entity = var11[var9];
//                    Field[] fields = entity.getClass().getDeclaredFields();
//                    Field[] var16 = fields;
//                    int var15 = fields.length;
//
//                    for(int var14 = 0; var14 < var15; ++var14) {
//                        Field field = var16[var14];
//                        Object fieldValue = ByteCodeUtil.getSpecFieldOfObject(entity, field.getName());
//                        recurseGenerate(entityElement, entity.getClass().getSimpleName() + "_" + field.getName(), fieldValue);
//                    }
//                }
//            }
//        }
//
//    }

//    public static void generateDefaultXmlObjectForEntityListAsList(Element root, PagableList pagableList, String entityElementName) {
//        List<Object> entityList = pagableList.getList();
//        root.addElement("totalSize").setText(String.valueOf(pagableList.getFullListSize()));
//        root.addElement("pageSize").setText(String.valueOf(pagableList.getObjectsPerPage()));
//        root.addElement("curPageNum").setText(String.valueOf(pagableList.getPageNumber()));
//        root.addElement("totalPageNum").setText(String.valueOf(pagableList.getTotalPageNum()));
//        root.addElement("curSize").setText(String.valueOf(entityList.size()));
//        List<Integer> indexList = pagableList.getAvailablePageIndexes();
//        Iterator var6 = indexList.iterator();
//
//        while(var6.hasNext()) {
//            Integer pageIndex = (Integer)var6.next();
//            root.addElement("availPageIndexes").setText(String.valueOf(pageIndex));
//        }
//
//        generateDefaultXmlObjectForEntityListAsList(root, entityList, entityElementName);
//    }

    public static void generateDefaultXmlObjectForEntityListAsList(Element root, List<Object> entityList, String entityElementName) {
        if (entityList.size() > 0) {
            int index = 1;
            Iterator var5 = entityList.iterator();

            while(var5.hasNext()) {
                Object entity = var5.next();
                Element entityElement = root.addElement(!StringUtil.isEmpty(entityElementName) ? entityElementName : "entityList");
                entityElement.addElement("seq").setText(String.valueOf(index++));
                Field[] fields = entity.getClass().getDeclaredFields();
                Field[] var11 = fields;
                int var10 = fields.length;

                for(int var9 = 0; var9 < var10; ++var9) {
                    Field field = var11[var9];
                    Object fieldValue = ByteCodeUtil.getSpecFieldOfObject(entity, field.getName());
                    recurseGenerate(entityElement, field.getName(), fieldValue);
                }
            }
        }

    }

    private static void recurseGenerate(Element joinPoint, String fieldName, Object fieldValue) {
        if (!StringUtil.isEmpty(fieldValue) && !ClassUtils.isPrimitiveOrWrapper(fieldValue.getClass()) && fieldValue.getClass() != String.class) {
            if (fieldValue instanceof Blob) {
                joinPoint.addElement(fieldName).setText("blob");
            } else if (ClassUtils.isAssignable(fieldValue.getClass(), List.class)) {
                generateDefaultXmlObjectForEntityListAsList(joinPoint, (List)fieldValue, fieldName);
            } else {
                Field[] subfields = fieldValue.getClass().getDeclaredFields();
                Field[] var7 = subfields;
                int var6 = subfields.length;

                for(int var5 = 0; var5 < var6; ++var5) {
                    Field field = var7[var5];
                    Object subfieldValue = ByteCodeUtil.getSpecFieldOfObject(fieldValue, field.getName());
                    recurseGenerate(joinPoint.addElement(fieldName), field.getName(), subfieldValue);
                }
            }
        } else {
            joinPoint.addElement(fieldName).setText(String.valueOf(fieldValue == null ? "" : fieldValue));
        }

    }

//    public static void generateDefaultXmlObjectForEntityAsObject(Element root, BaseEntity entity, String entityElementName) {
//        int index = 0;
//        Element entityElement = root.addElement(!StringUtil.isEmpty(entityElementName) ? entityElementName : "entity");
//        Field[] fields = entity.getClass().getDeclaredFields();
//        Field[] var9 = fields;
//        int var8 = fields.length;
//
//        for(int var7 = 0; var7 < var8; ++var7) {
//            Field field = var9[var7];
//            Object fieldValue = ByteCodeUtil.getSpecFieldOfObject(entity, field.getName());
//            if (fieldValue instanceof Blob) {
//                entityElement.addElement(field.getName()).setText("blob");
//            } else {
//                entityElement.addElement(field.getName()).setText(String.valueOf(fieldValue == null ? "" : fieldValue));
//            }
//        }
//
//    }

//    public static void generateDefaultXmlObjectForEntityListAsObject(Element root, List<BaseEntity> entityList, String entityElementName) {
//        int index = 0;
//        Element entityElement = root.addElement(!StringUtil.isEmpty(entityElementName) ? entityElementName : "entity");
//        Iterator var6 = entityList.iterator();
//
//        while(var6.hasNext()) {
//            BaseEntity entity = (BaseEntity)var6.next();
//            Field[] fields = entity.getClass().getDeclaredFields();
//            Field[] var11 = fields;
//            int var10 = fields.length;
//
//            for(int var9 = 0; var9 < var10; ++var9) {
//                Field field = var11[var9];
//                Object fieldValue = ByteCodeUtil.getSpecFieldOfObject(entity, field.getName());
//                if (fieldValue instanceof Blob) {
//                    entityElement.addElement(field.getName()).setText("blob");
//                } else {
//                    entityElement.addElement(field.getName()).setText(String.valueOf(fieldValue == null ? "" : fieldValue));
//                }
//            }
//        }
//
//    }

    public static String mapToXml(Map<String, String> data) {
        String output = null;
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            org.w3c.dom.Document document = documentBuilder.newDocument();
            org.w3c.dom.Element root = document.createElement("xml");
            document.appendChild(root);
            Iterator var7 = data.keySet().iterator();

            while(var7.hasNext()) {
                String key = (String)var7.next();
                String value = (String)data.get(key);
                if (value == null) {
                    value = "";
                }

                value = value.trim();
                org.w3c.dom.Element filed = document.createElement(key);
                filed.appendChild(document.createTextNode(value));
                root.appendChild(filed);
            }

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer transformer = tf.newTransformer();
            DOMSource source = new DOMSource(document);
            transformer.setOutputProperty("encoding", "UTF-8");
            transformer.setOutputProperty("indent", "yes");
            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            output = writer.getBuffer().toString();
            writer.close();
        } catch (TransformerException | IOException | ParserConfigurationException var11) {
            var11.printStackTrace();
        }

        return output;
    }
}
