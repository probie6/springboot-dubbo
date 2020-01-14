package com.newbie.common.util;

import com.tyyw.lcba.jcjd.util.enums.ComplexFieldTypeEnum;
import com.tyyw.lcba.jcjd.util.enums.FieldTypeEnum;
import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.*;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 动态设置获取类的属性值
 * @author 张元平
 * @version 1.0
 * @date 2019-11-11
 */
public class ObjectUtil {

    /**
     * 根据类型 获取类的文档
     * @param clz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Map<String,Object> getEntityJsonDesc(Class<?> clz, Type type) throws Exception {
        return  getEntityJsonDesc(clz.newInstance(),type);
    }
    /**
     * 根据类型 获取类的文档
     * @param clz
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object getEntityJsonDesc(Class<?> clz) throws Exception {
         return  getEntityJsonDesc(clz.newInstance());
    }
    /***
     * 获取对象的json结构并用 {key:"${说明ixnx}"}的格式输出为字符串，用于输入参数的描述信息
     * 对list或者数组类型，没有指定参数泛型类型的不做处理
     * 无法处理递归死循环的场景,父类与子类相互嵌套的场景(暂时不做处理)
     * 无法对map类型的字段进行处理
     * @param object
     * @return
     */
    public static Map<String,Object> getEntityJsonDesc(Object object,Type type) throws Exception {

        Field[] fields = ObjectUtil.getFields(object);
        Map<String,Object> map = new LinkedHashMap<>();
        for(Field curr:fields){

            curr.setAccessible(true);
            FieldTypeEnum fieldTypeEnum = FieldTypeEnum.getFieldType(curr.getType().getName());
            if(fieldTypeEnum==null){ //如果为null 则说明是 对象类型
                ComplexFieldTypeEnum complexFieldTypeEnum = ComplexFieldTypeEnum.getFieldType(curr.getType().getName());
                if(complexFieldTypeEnum!=null){
                    map.put(curr.getName(),complexFieldTypeEnum.getEntityJsonDesc(curr.getGenericType()));
                }else{
                    map.put(curr.getName(),getEntityJsonDesc(curr.getType().newInstance()));
                }
            }else{
                //获取swagger上的属性说明
                ApiModelProperty apiModelProperty = curr.getAnnotation(ApiModelProperty.class);
                if(apiModelProperty ==null){
                    map.put(curr.getName(),curr.getName());
                }else{
                    map.put(curr.getName(),apiModelProperty.value());
                }
            }
        }
        ComplexFieldTypeEnum complexFieldTypeEnum = ComplexFieldTypeEnum.getFieldType(object.getClass().getName());
        complexFieldTypeEnum.adapterObject(map, object,type);

        return map;
    }
    /***
     * 获取对象的json结构并用 {key:"${说明ixnx}"}的格式输出为字符串，用于输入参数的描述信息
     * 对list或者数组类型，没有指定参数泛型类型的不做处理
     * 无法处理递归死循环的场景,父类与子类相互嵌套的场景(暂时不做处理)
     * 无法对map类型的字段进行处理
     * @param type
     * @return
     */
    public static Object getEntityJsonDesc4Type(ParameterizedType type) throws Exception {
        // 泛型类型切换
        ComplexFieldTypeEnum complexFieldTypeEnum = ComplexFieldTypeEnum.getFieldType(type.getRawType().getTypeName());
        if(complexFieldTypeEnum!=null){

            return complexFieldTypeEnum.getEntityJsonDesc(type);
        }
        throw new Exception("复杂泛型 类型未定义");
    }
    /***
     * 获取对象的json结构并用 {key:"${说明ixnx}"}的格式输出为字符串，用于输入参数的描述信息
     * 对list或者数组类型，没有指定参数泛型类型的不做处理
     * 无法处理递归死循环的场景,父类与子类相互嵌套的场景(暂时不做处理)
     * 无法对map类型的字段进行处理
     * @param object
     * @return
     */
    public static Object getEntityJsonDesc(Object object) throws Exception {

        if(object instanceof String){
            return null ;
        }
        if(object instanceof ParameterizedType){
            return getEntityJsonDesc4Type((ParameterizedType)object);
        }
        Field[] fields = ObjectUtil.getFields(object);
        Map<String,Object> map = new LinkedHashMap<>();

        for(Field curr:fields){

            curr.setAccessible(true);
            FieldTypeEnum fieldTypeEnum = FieldTypeEnum.getFieldType(curr.getType().getName());
            if(fieldTypeEnum==null){ //如果为null 则说明是 对象类型
                ComplexFieldTypeEnum complexFieldTypeEnum = ComplexFieldTypeEnum.getFieldType(curr.getType().getName());
                if(complexFieldTypeEnum!=null){
                    map.put(curr.getName(),complexFieldTypeEnum.getEntityJsonDesc(curr.getGenericType()));
                }else{
                    map.put(curr.getName(),getEntityJsonDesc(curr.getType().newInstance()));
                }
            }else{
                //获取swagger上的属性说明
                ApiModelProperty apiModelProperty = curr.getAnnotation(ApiModelProperty.class);
                if(apiModelProperty ==null){
                    map.put(curr.getName(),curr.getName());
                }else{
                    map.put(curr.getName(),apiModelProperty.value());
                }
            }
        }

        return map;
    }
    /**
     * 过滤不需要属性
     * @param field
     * @return
     */
    private static Boolean needFilterField(Field field){
        // 过滤静态属性
        if(Modifier.isStatic(field.getModifiers())){
            return true;
        }
        // 过滤transient 关键字修饰的属性
        if(Modifier.isTransient(field.getModifiers())){
            return true;
        }
        return false;
    }
    /**
     * 利用Java反射根据类的名称获取属性信息和父类的属性信息
     * @param obj
     * @return
     */
    public static Map<String, Field> getFieldMap(Object obj) {
        Map<String, Field> fieldMap = new LinkedHashMap<>();
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            if (needFilterField(field)) {
                continue;
            }
            fieldMap.put(field.getName(), field);
        }
        getParentField(clazz, fieldMap);
        return fieldMap;
    }

    /***
     * 获取类的所有字段含父类字段
     * @param obj
     * @return
     */
    public static Field[]  getFields(Object obj){
        Map<String,Field> map = getFieldMap(obj);
        Field[] fields = new Field[map.size()];
        fields = map.values().toArray(fields);
        return fields;
    }
    /**
     * 递归所有父类属性
     * @param clazz
     * @param fieldMap
     */
    private static void getParentField(Class<?> clazz, Map<String, Field> fieldMap){
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            Field[] superFields = superClazz.getDeclaredFields();
            for(Field field : superFields){
                if (needFilterField(field)) {
                    continue;
                }
                fieldMap.put(field.getName(), field);
            }
            getParentField(superClazz, fieldMap);
        }
    }

    /**
     * 根据类获取属性信息和父类的属性信息
     * @param obj
     * @return
     */
    public static Map<String, Method> getMethodMap(Object obj) {
        Map<String, Method> methodMap = new LinkedHashMap<>();
        Class<?> clazz = obj.getClass();
        Method[] methods = clazz.getMethods();
        for(Method method : methods){
            methodMap.put(method.getName(), method);
        }
        getParentMethod(clazz, methodMap);
        return methodMap;
    }

    /**
     * 递归所有父类方法
     * @param clazz
     * @param methodMap
     */
    private static void getParentMethod(Class<?> clazz, Map<String, Method> methodMap){
        Class<?> superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            Method[] superMethods = superClazz.getMethods();
            for(Method field : superMethods){
                methodMap.put(field.getName(), field);
            }
            getParentMethod(superClazz, methodMap);
        }
    }
    /**
     * 根据属性名获取属性值
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldValue(Object obj, String fieldName) {
        try {
            Map<String, Method> methodMap = getMethodMap(obj);
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = methodMap.get(getter);
            Object value = null;
            if (method != null){
                value = method.invoke(obj, new Object[] {});
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
//            log.error(e.getMessage());
            return null;
        }
    }
    /**
     * 设置属性值
     * @param obj
     * @param fieldName
     * @param value
     */
    public static void setFieldValue(Object obj, String fieldName, Object value) {
        try {
            Map<String, Field> fields = getFieldMap(obj);
            Field f = fields.get(fieldName);
            if (f != null) {
                f.setAccessible(true);
                f.set(obj, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            log.error(e.getMessage());
        }
    }
}