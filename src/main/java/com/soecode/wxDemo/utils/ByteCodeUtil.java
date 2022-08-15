package com.soecode.wxDemo.utils;


import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class ByteCodeUtil {
    private static final Logger logger = LoggerFactory.getLogger(ByteCodeUtil.class);

    public ByteCodeUtil() {
    }

    public static Object invokeObjectMethod(Object owner, String methodName, Object[] args) throws Exception {
        Object result = null;
        Method method = null;
        Class ownerClass = owner.getClass();
        Class[] argsClass = null;
        if (args != null) {
            argsClass = new Class[args.length];
            int i = 0;

            for(int j = args.length; i < j; ++i) {
                argsClass[i] = args[i].getClass();
            }
        }

        try {
            method = getMethodSuper(ownerClass, methodName, argsClass);
            result = method.invoke(owner, args);
        } catch (NoSuchMethodException var9) {
            method = getDeclaredMethodSuper(ownerClass, methodName, argsClass);
            method.setAccessible(true);
        } catch (InvocationTargetException var10) {
//            ExceptionUtil.throwTargetWrappedException(var10);
        }

        return result;
    }

    public static Object invokeClassMethod(Class owner, String methodName, Object[] args, Class[] argTypes) throws Exception {
        Object result = null;
        Method method = null;

        try {
            method = getMethodSuper(owner, methodName, argTypes);
            result = method.invoke((Object)null, args);
        } catch (NoSuchMethodException var7) {
            method = getDeclaredMethodSuper(owner, methodName, argTypes);
            method.setAccessible(true);
            result = method.invoke((Object)null, args);
        } catch (InvocationTargetException var8) {
//            ExceptionUtil.throwTargetWrappedException(var8);
        }

        return result;
    }

    private static Method getMethodSuper(Class owner, String methodName, Class[] argTypes) throws Exception {
        Method method = owner.getMethod(methodName, argTypes);
        return method;
    }

    private static Method getDeclaredMethodSuper(Class owner, String methodName, Class[] argTypes) throws Exception {
        Method method = owner.getDeclaredMethod(methodName, argTypes);
        return method;
    }

//    public static Object createABeanInstance(String className) throws Exception {
//        return createABeanInstance(ClassUtil.getClass(className));
//    }

    public static Object createABeanInstance(Class clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        return clazz.getDeclaredConstructor().newInstance();
    }

    public static <T> T createABeanInstanceGeneric(Class<T> clazz) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        return clazz.getDeclaredConstructor().newInstance();
    }

    public static Object newInstance(String className, Object[] args) {
        try {
            Class newoneClass = Class.forName(className);
            Class[] argsClass = new Class[args.length];
            int i = 0;

            for(int j = args.length; i < j; ++i) {
                argsClass[i] = args[i].getClass();
            }

            Constructor cons = newoneClass.getConstructor(argsClass);
            return cons.newInstance(args);
        } catch (Exception var6) {
            logger.error("类{}构造异常", className);
            var6.printStackTrace();
            return null;
        }
    }

    public static Object newInstance(Class clazz, Class[] parmTypes, Object[] paramValues) {
        try {
            Constructor cons = clazz.getConstructor(parmTypes);
            return cons.newInstance(paramValues);
        } catch (Exception var4) {
            logger.error("类{}构造异常", clazz.getSimpleName());
            var4.printStackTrace();
            return null;
        }
    }

    public static Object getBeanValue(Object pojoInstance, String fieldName) {
        if (StringUtil.isEmpty(fieldName)) {
            return pojoInstance;
        } else {
            String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
            Class ownerClass = pojoInstance.getClass();
            Object val = null;

            try {
                Method method = ownerClass.getMethod(methodName);
                val = method.invoke(pojoInstance);
                if (val instanceof String) {
                    val = ((String)val).trim();
                }
            } catch (Exception var6) {
                var6.printStackTrace();
            }

            return val;
        }
    }

    public static void setBeanValue(Object pojoInstance, String fieldName, Object value) {
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
        Class ownerClass = pojoInstance.getClass();
        Class argsClass = value.getClass();

        try {
            Method method = ownerClass.getMethod(methodName, argsClass);
            method.invoke(pojoInstance, value);
        } catch (NoSuchMethodException var9) {
            try {
                Method method = ownerClass.getMethod(methodName, covertWrapperToPrimitive(argsClass));
                method.invoke(pojoInstance, value);
            } catch (Exception var8) {
                logger.error(var8.getMessage());
            }
        } catch (Exception var10) {
            logger.error(var10.getMessage());
        }

    }

    public static void setBeanValueIfExists(Object pojoInstance, String fieldName, Object value) {
        if (getDeclaredField(pojoInstance, fieldName) != null) {
            String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
            Class ownerClass = pojoInstance.getClass();
            Class argsClass = value.getClass();

            try {
                Method method = ownerClass.getMethod(methodName, argsClass);
                method.invoke(pojoInstance, value);
            } catch (NoSuchMethodException var9) {
                try {
                    Method method = ownerClass.getMethod(methodName, covertWrapperToPrimitive(argsClass));
                    method.invoke(pojoInstance, value);
                } catch (Exception var8) {
                    logger.error(var8.getMessage());
                }
            } catch (Exception var10) {
                logger.error(var10.getMessage());
            }
        }

    }

    private static Class covertWrapperToPrimitive(Class wrapperCls) {
        String classStr = wrapperCls.getSimpleName();
        if (classStr.equals("Long")) {
            return Long.TYPE;
        } else if (classStr.equals("Integer")) {
            return Integer.TYPE;
        } else if (classStr.equals("Float")) {
            return Float.TYPE;
        } else {
            return classStr.equals("Double") ? Double.TYPE : wrapperCls;
        }
    }

    public static void setBeanValue(Object pojoInstance, String fieldName, Object[] value, Class clazz) {
        String methodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
        Class ownerClass = pojoInstance.getClass();
        Class var6 = value.getClass();

        try {
            Method method = ownerClass.getMethod(methodName, clazz);
            Class compenentType = clazz.getComponentType();
            Object result = Array.newInstance(compenentType, value.length);
            System.arraycopy(value, 0, result, 0, value.length);
            method.invoke(pojoInstance, result);
        } catch (Exception var10) {
            var10.printStackTrace();
        }

    }

    public static boolean isNullOrZero(Object val) {
        if (val instanceof Long) {
            return (Long)val == 0L;
        } else if (val instanceof Float) {
            return (Float)val == 0.0F;
        } else if (val instanceof Integer) {
            return (Integer)val == 0;
        } else if (val instanceof Double) {
            return (Double)val == 0.0D;
        } else if (val instanceof String) {
            return val == null || val.equals("") || ((String)val).trim().equals("");
        } else {
            return true;
        }
    }

    public static Object getObjectFieldValue(Object filedObj, Class fieldClazz, String fieldName) {
        Field field = null;
        Object retValue = null;

        try {
            field = fieldClazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            retValue = field.get(filedObj);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        return retValue;
    }

    public static void forcedModifyObjecctValue(Object filedObj, Class fieldClazz, String fieldName, Object setValue) {
        try {
            Field field = fieldClazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(filedObj, setValue);
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }

    public static Object getSpecFieldOfParentObject(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getSuperclass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    public static Object getSpecFieldOfObject(Object obj, String fieldName) {
        try {
            Field field = getDeclaredField(obj, fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception var3) {
            logger.warn(var3.getMessage());
            return null;
        }
    }

//    public static Object createGenericTypeInstance(Object currentObject) {
//        ParameterizedType paraType = (ParameterizedType)currentObject.getClass().getGenericSuperclass();
//        Type[] types = paraType.getActualTypeArguments();
//        Type type = types[0];
//
//        try {
//            return createABeanInstance(type.toString().substring(6));
//        } catch (Exception var5) {
//            var5.printStackTrace();
//            return null;
//        }
//    }

    public static Field getDeclaredField(Object object, String fieldName) {
//        Assert.notNull(object, "object不能为空");
        Class superClass = object.getClass();

        while(superClass != Object.class) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException var4) {
                logger.error("Field不在当前类定义,继续向上转型", var4);
                superClass = superClass.getSuperclass();
            }
        }

        return null;
    }

    public static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
//        Assert.notNull(object, "object不能为空");
        Class superClass = object.getClass();

        while(superClass != Object.class) {
            try {
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException var5) {
                logger.error(" Method不在当前类定义,继续向上转型", var5);
                superClass = superClass.getSuperclass();
            }
        }

        return null;
    }

    public static List fetchElementPropertyToList(Collection collection, String propertyName) {
        ArrayList list = new ArrayList();

        try {
            Iterator var4 = collection.iterator();

            while(var4.hasNext()) {
                Object obj = var4.next();
                list.add(PropertyUtils.getProperty(obj, propertyName));
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return list;
    }

    public static String fetchElementPropertyToString(Collection collection, String propertyName, String separator) {
        List list = fetchElementPropertyToList(collection, propertyName);
        return StringUtils.join(list, separator);
    }

    public static Object getFieldValue(String value, String type) {
        if ("long".equals(type)) {
            return Long.valueOf(value);
        } else if ("int".equals(type)) {
            return Integer.valueOf(value);
        } else {
            return "float".equals(type) ? Float.valueOf(value) : value;
        }
    }

    public static String getFieldTypeName(Field field) {
        return field.getType().getSimpleName();
    }

    public static Class toClass(String clazz) {
        Class c = null;

        try {
            c = Class.forName(clazz);
        } catch (ClassNotFoundException var3) {
        }

        return c;
    }

    public static PropertyDescriptor findProperty(Object object, String name) {
        try {
            BeanInfo info = Introspector.getBeanInfo(object.getClass());
            PropertyDescriptor[] properties = info.getPropertyDescriptors();
            PropertyDescriptor[] var7 = properties;
            int var6 = properties.length;

            for(int var5 = 0; var5 < var6; ++var5) {
                PropertyDescriptor property = var7[var5];
                if (property.getName().equals(name)) {
                    return property;
                }
            }
        } catch (IntrospectionException var8) {
            var8.printStackTrace();
        }

        return null;
    }

//    public static void executeLoadingLicenseClass() {
//        try {
//            ClassPool classPool = ClassPool.getDefault();
//            CtClass licenseClass = classPool.getOrNull("org.wbase.framework.core.utils.LicenseClass");
//            if (licenseClass == null) {
//                licenseClass = classPool.makeClass("org.wbase.framework.core.utils.LicenseClass");
//                CtConstructor constructor = new CtConstructor((CtClass[])null, licenseClass);
//                constructor.setModifiers(1);
//                constructor.setBody("{}");
//                licenseClass.addConstructor(constructor);
//                String methodBody = Constants.EXPIRE_HALT_ENBALED ? "System.exit(0);" : "{}";
//                CtMethod method = new CtMethod(CtClass.voidType, "validate", new CtClass[]{classPool.get(String.class.getName()), classPool.get(String.class.getName())}, licenseClass);
//                method.setModifiers(1);
//                method.setBody(methodBody);
//                licenseClass.addMethod(method);
//                licenseClass.toClass();
//            }
//
//            invokeObjectMethod(createABeanInstance("org.wbase.framework.core.utils.LicenseClass"), "validate", new Object[]{"a", "b"});
//            licenseClass.defrost();
//        } catch (Exception var5) {
//            var5.printStackTrace();
//        }
//
//    }

//    public static void executeLoadingLicenseExecutorClass(String filePath, String macAddr) {
//        try {
//            ClassPool classPool = ClassPool.getDefault();
//            CtClass executorClass = classPool.getOrNull("org.wbase.framework.core.utils.LicenseExecutor");
//            if (executorClass == null) {
//                executorClass = classPool.makeClass("org.wbase.framework.core.utils.LicenseExecutor");
//                String methodBody = "org.wbase.framework.core.Uniframework.Operator.validateLicense($1,$2);";
//                CtMethod method = new CtMethod(CtClass.voidType, "execute", new CtClass[]{classPool.get(String.class.getName()), classPool.get(String.class.getName())}, executorClass);
//                method.setModifiers(1);
//                method.setBody(methodBody);
//                executorClass.addMethod(method);
//                executorClass.toClass();
//            }
//
//            invokeObjectMethod(createABeanInstance("org.wbase.framework.core.utils.LicenseExecutor"), "execute", new Object[]{filePath, macAddr});
//            executorClass.defrost();
//        } catch (Exception var6) {
//            var6.printStackTrace();
//        }
//
//    }

    public static Object getMethodResultOfSpecClass(Class clazz, String methodName) throws Exception {
        Object obj = clazz.getDeclaredConstructor().newInstance();
        Method method = clazz.getMethod(methodName);
        return method.invoke(obj);
    }

    public static void forcedModifyObjecctValueWhenFieldInHierarchy(Object filedObj, String fieldName, Object setValue) {
        Class fieldClazz = filedObj.getClass();

        for(Field field = null; field == null && fieldClazz != null; fieldClazz = fieldClazz.getSuperclass()) {
            Field[] var8;
            int var7 = (var8 = fieldClazz.getDeclaredFields()).length;

            for(int var6 = 0; var6 < var7; ++var6) {
                Field curField = var8[var6];
                if (fieldName.equalsIgnoreCase(curField.getName())) {
                    field = curField;
                    curField.setAccessible(true);

                    try {
                        field.set(filedObj, setValue);
                        return;
                    } catch (IllegalAccessException | IllegalArgumentException var10) {
                        var10.printStackTrace();
                    }
                }
            }
        }

    }

    public static void main(String[] args) throws Exception {
//        Log4jV2Util.initLog4jV2TestEnv();
//        Staff staff = new Staff();
//        staff.setASS_STAFF_DESC("ABC");
//        forcedModifyObjecctValueWhenFieldInHierarchy(staff, "ASS_STAFF_DESC", "agb");
//        System.out.println(staff);
    }
}
