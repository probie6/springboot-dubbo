package com.newbie.common.util.enums;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newbie.dto.ResponseResult;
import com.tyyw.lcba.jcjd.util.ObjectUtil;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Created by 94481 on 2018/12/27.
 * 字段枚举类型
 * 基础类型枚举
 */
public enum ComplexFieldTypeEnum implements OursBaseEnum {

    list_type(1,"list",new String[]{
            List.class.getName()
    }) {
        /***
         * 根据List 获取 json文档
         * @return
         */
        @Override
        public Object getEntityJsonDesc(Type genericType ) throws Exception {
            List<Object> currList = new ArrayList<>();
            //对集合进行处理
            if(genericType!=null){
                if(genericType instanceof ParameterizedType){
                    ParameterizedType parameterizedType = (ParameterizedType) genericType;
                    Class<?> actualTypeArugument = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                    Object currItem = ObjectUtil.getEntityJsonDesc(actualTypeArugument);
                    currList.add(currItem);
                }
            }
            return currList;
        }
        @Override
        public Class findJavaClass() {
            return List.class;
        }

        @Override
        public void adapterObject(Map<String,Object> map,Object entity,Type type) {

        }
    },set_type(2,"set",new String[]{
            Set.class.getName()
    }) {
        /***
         * Set 获取 set
         * @return
         */
        @Override
        public Object getEntityJsonDesc(Type genericType ) throws Exception {
            List<Object> currList = new ArrayList<>();
            //对集合进行处理
            if(genericType!=null){
                if(genericType instanceof ParameterizedType){
                    ParameterizedType parameterizedType = (ParameterizedType) genericType;
                    Class<?> actualTypeArugument = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                    Object currItem = ObjectUtil.getEntityJsonDesc(actualTypeArugument);
                    currList.add(currItem);
                }
            }
            return currList;
        }
        @Override
        public void adapterObject(Map<String,Object> map,Object entity,Type type) {

        }
        @Override
        public Class findJavaClass() {
            return List.class;
        }
    },page_type(3,"page",new String[]{
            Page.class.getName()
    }) {
        /***
         * 根据Page 获取 json文档
         * @return
         */
        @Override
        public Object getEntityJsonDesc(Type genericType ) throws Exception {


            List<Map<String,Object>> currList = new ArrayList<>();

            if(genericType!=null){
                if(genericType instanceof ParameterizedType){
                    ParameterizedType parameterizedType = (ParameterizedType) genericType;
                    Class<?> actualTypeArugument = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                    Object currItem = ObjectUtil.getEntityJsonDesc(actualTypeArugument);
                    currList.add(JSON.parseObject(JSON.toJSONString(currItem),Map.class));
                }
            }
            Page<Map<String, Object>> mapPage = new Page<>(0, 0);
            mapPage.setRecords(currList);
            return mapPage;

        }
        @Override
        public void adapterObject(Map<String,Object> map,Object entity,Type type) {

        }
        @Override
        public Class findJavaClass() {
            return List.class;
        }
    },responseResult_type(3,"responseResult",new String[]{
            ResponseResult.class.getName()
    }) {
        /***
         * 根据responseResult获取 responseResult文档
         * @return
         */
        @Override
        public Object getEntityJsonDesc(Type genericType ) throws Exception {
            ResponseResult<Object> objectResponseResult = new ResponseResult<>();
            List<Map<String,Object>> currList = new ArrayList<>();
            if(genericType!=null){
                ComplexFieldTypeEnum complexFieldTypeEnum= ComplexFieldTypeEnum.getFieldType(genericType.getTypeName());

                if(complexFieldTypeEnum!=null){
                    objectResponseResult.setData(complexFieldTypeEnum.getEntityJsonDesc(genericType));
                }else{
                    objectResponseResult.setData(ObjectUtil.getEntityJsonDesc(genericType));
                }
            }
            return objectResponseResult;

        }
        @Override
        public void adapterObject(Map<String,Object> map,Object entity,Type type) {
            ResponseResult responseResult = (ResponseResult) entity;
            try {
                // 泛型类型切换
                if(type instanceof ParameterizedType){
                    ParameterizedType parameterizedType = (ParameterizedType) type;

                    // 这里进行递归处理泛型
                    //TODO 目前只处理类单泛型参数，未处理多泛型参数 比如 MAP<T,T>
                    ComplexFieldTypeEnum fieldTypeEnum = ComplexFieldTypeEnum.getFieldType(parameterizedType.getActualTypeArguments()[0].getTypeName());

                    Object data =null;
                    if(fieldTypeEnum!=null){
                        data = fieldTypeEnum.getEntityJsonDesc(parameterizedType.getActualTypeArguments()[0]);

                    }else{
                        //转实体类型对象
                        Class<?> currC = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                          data = ObjectUtil.getEntityJsonDesc(currC);

                    }
                    map.put("data",data);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        @Override
        public Class findJavaClass() {
            return List.class;
        }
    };

    private int value;
    private String des;
    private String[] types;
    ComplexFieldTypeEnum(int value, String des, String[]types){
        this.value = value;
        this.des = des;
        this.types = types;
    }

    /***
     * 根据值 和校验值进行校验
     * @return
     */
    @Override
    public int getValue() {
        return this.value;
    }

    @Override
    public String getDes() {
        return this.des;
    }

    /***
     * 判断字符串值是否在数组中
     * @param types
     * @param type
     * @return
     */
    private static int indexOf(String[] types,String type){
        int index = -1;
        if(types !=null){
            for(int i=0,len=types.length;i<len;i++){
                if(types[i].equals(type)){
                    index = i;
                    break;
                }
            }
        }
        return index;
    }

    /***
     * 根据字段类型获取类型枚举
     *
     * @param type
     * @return
     */
    public static ComplexFieldTypeEnum getFieldType(String type){
        ComplexFieldTypeEnum[] enums = ComplexFieldTypeEnum.class.getEnumConstants();
        for(ComplexFieldTypeEnum current: enums) {
            if(current.name().equals(type) || indexOf(current.types,type)>-1) {
                return current;
            }
            //java.util.List
            // java.util.List<xxxx>
            if(type.startsWith(current.name())){
             return current;

            }
            for(String curr:current.types){
                if(type.startsWith(curr)){
                    return current;
                }
            }
        }
        return null;
    }

    public static LinkedHashMap<String,Object> findEnumsJson(){
        ComplexFieldTypeEnum[] enums = ComplexFieldTypeEnum.class.getEnumConstants();
        LinkedHashMap<String,Object> result = new LinkedHashMap<>();
        for(ComplexFieldTypeEnum current: enums) {
            LinkedHashMap<String,Object> currMap = new LinkedHashMap<>();
            currMap.put("name",current.name());
            currMap.put("desc",current.getDes());
            result.put(current.name(),currMap);
        }
        return result;
    }
    public static void main(String[]args){
        LinkedHashMap<String,Object>  map = findEnumsJson();
        String json = JSON.toJSONString(map);
        System.out.println(json);
        Iterator<String> iterator = map.keySet().iterator();
        StringBuffer buffer = new StringBuffer();
        while (iterator.hasNext()){
            String key = iterator.next();
            String name= key.substring(0,1).toUpperCase()+key.substring(1,key.indexOf("_"));


            buffer.append("public static " + ComplexFieldTypeEnum.getFieldType(key).findJavaClass().getName() + " get" + ComplexFieldTypeEnum.getFieldType(key).findJavaClass().getSimpleName() + "(Map<String,Object> map,String key," + ComplexFieldTypeEnum.getFieldType(key).findJavaClass().getName() + " defaultValue){" +
                    "" + ComplexFieldTypeEnum.getFieldType(key).findJavaClass().getName()+" result=null;" +
                    "return result;}");
        }
        System.out.println(buffer.toString());
    }
    public abstract Object getEntityJsonDesc(Type genericType) throws Exception;
    public abstract Class findJavaClass();

    //处理 类的泛型
    public abstract void adapterObject(Map<String,Object> map,Object entity, Type type);
}
