package com.soecode.wxDemo.utils;


import org.apache.commons.beanutils.BeanUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

public final class BeanUtil extends BeanUtils {
    public BeanUtil() {
    }

    public static void copyProperties(Object destination, Object source) {
        try {
            copyProperties(destination, source, false);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    private static void copyProperties(Object dest, Object src, boolean keepOnNull) throws Exception {
        if (src != null && dest != null) {
            BeanInfo info = Introspector.getBeanInfo(src.getClass());
            PropertyDescriptor[] props = info.getPropertyDescriptors();

            for(int i = 0; i < props.length; ++i) {
                PropertyDescriptor srcProp = props[i];
                PropertyDescriptor destProp = findProperty(dest, srcProp.getName());
                if (destProp != null && destProp.getWriteMethod() != null && srcProp.getReadMethod() != null) {
                    try {
                        Object srcVal = srcProp.getReadMethod().invoke(src);
                        Object destVal = destProp.getReadMethod().invoke(dest);
                        if (keepOnNull && dest != null && srcVal == null) {
                            srcVal = destVal;
                        }

                        destProp.getWriteMethod().invoke(dest, srcVal);
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }
                }
            }

        }
    }

    private static boolean isIn(String[] excludeFields, String field) {
        String[] var5 = excludeFields;
        int var4 = excludeFields.length;

        for(int var3 = 0; var3 < var4; ++var3) {
            String f = var5[var3];
            if (f.equalsIgnoreCase(field)) {
                return true;
            }
        }

        return false;
    }

    public static void copyPropertiesWithoutSpecFields(Object dest, Object src, boolean keepOnNull, String[] excludeFields) throws Exception {
        if (src != null && dest != null) {
            BeanInfo info = Introspector.getBeanInfo(src.getClass());
            PropertyDescriptor[] props = info.getPropertyDescriptors();

            for(int i = 0; i < props.length; ++i) {
                PropertyDescriptor srcProp = props[i];
                PropertyDescriptor destProp = findProperty(dest, srcProp.getName());
                if (destProp != null && destProp.getWriteMethod() != null && srcProp.getReadMethod() != null && !isIn(excludeFields, srcProp.getName())) {
                    try {
                        Object srcVal = srcProp.getReadMethod().invoke(src);
                        Object destVal = destProp.getReadMethod().invoke(dest);
                        if (keepOnNull && dest != null && srcVal == null) {
                            srcVal = destVal;
                        }

                        destProp.getWriteMethod().invoke(dest, srcVal);
                    } catch (Exception var11) {
                        var11.printStackTrace();
                    }
                }
            }

        }
    }

    private static PropertyDescriptor findProperty(Object object, String name) throws IntrospectionException {
        BeanInfo info = Introspector.getBeanInfo(object.getClass());
        PropertyDescriptor[] properties = info.getPropertyDescriptors();

        for(int i = 0; i < properties.length; ++i) {
            PropertyDescriptor property = properties[i];
            if (property.getName().equals(name)) {
                return property;
            }
        }

        return null;
    }

    public static void copyFieldsFromSrcToDestWhenSrcFieldValueNotEmpty(Object srcObject, Object destObject) {
        try {
            Field[] fields = srcObject.getClass().getDeclaredFields();
            Field[] var6 = fields;
            int var5 = fields.length;

            for(int var4 = 0; var4 < var5; ++var4) {
                Field field = var6[var4];
                Object srcfieldValue = getProperty(srcObject, field.getName());
                getProperty(destObject, field.getName());
                if (!StringUtil.isEmpty(srcfieldValue)) {
                    setProperty(destObject, field.getName(), srcfieldValue);
                }
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }

    }

    public static void copyFieldsFromSrcToDestWhenDestFieldValueEmpty(Object srcObject, Object destObject) {
        try {
            Field[] fields = srcObject.getClass().getDeclaredFields();
            Field[] var6 = fields;
            int var5 = fields.length;

            for(int var4 = 0; var4 < var5; ++var4) {
                Field field = var6[var4];
                Object srcfieldValue = getProperty(srcObject, field.getName());
                Object destfieldValue = getProperty(destObject, field.getName());
                if (!StringUtil.isEmpty(srcfieldValue) && StringUtil.isEmpty(destfieldValue)) {
                    setProperty(destObject, field.getName(), srcfieldValue);
                }
            }
        } catch (Exception var9) {
            var9.printStackTrace();
        }

    }
}
