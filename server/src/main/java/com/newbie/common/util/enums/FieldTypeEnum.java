package com.newbie.common.util.enums;

import com.alibaba.fastjson.JSON;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by 94481 on 2018/12/27.
 * 字段枚举类型
 * 基础类型枚举
 */
public enum FieldTypeEnum implements OursBaseEnum {
    int_type(1,"int",new String[]{
            "int",
            "java.lang.Integer"
    }) {

        @Override
        public String findFieldDefine4DB(int minLength, int maxLength, int dotNum, boolean isUnique) {
            if(maxLength<0){
                maxLength =4;
            }
            return "int("+maxLength+")";
        }

        @Override
        public Class findJavaClass() {
            return Integer.class;
        }
    },
    double_type(2,"double",new String[]{
            "double",
            "java.lang.Double"
    }) {

        @Override
        public String findFieldDefine4DB(int minLength, int maxLength, int dotNum, boolean isUnique) {
            return "double";
        }

        @Override
        public Class findJavaClass() {
            return Double.class;
        }
    },
    float_type(3,"float",new String[]{
            "float",
            "java.lang.Float"
    }) {

        @Override
        public String findFieldDefine4DB(int minLength, int maxLength, int dotNum, boolean isUnique) {
            if(maxLength<0){
                maxLength =10;
            }
            return "float("+maxLength+","+dotNum+")";
        }

        @Override
        public Class findJavaClass() {
            return Float.class;
        }
    },
    long_type(4,"long",new String[]{
            "long",
            "java.lang.Long"
    }) {

        @Override
        public String findFieldDefine4DB(int minLength, int maxLength, int dotNum, boolean isUnique) {
            if(maxLength<0){
                maxLength =8;
            }
            return "bigint("+maxLength+")";
        }

        @Override
        public Class findJavaClass() {
            return Long.class;
        }
    },
    short_type(5,"short",new String[]{
            "short",
            "java.lang.Short"
    }) {

        @Override
        public String findFieldDefine4DB(int minLength, int maxLength, int dotNum, boolean isUnique) {
            if(maxLength<0){
                maxLength = 4;
            }
            return "smallint("+maxLength+")";
        }

        @Override
        public Class findJavaClass() {
            return Short.class;
        }
    },
    byte_type(6,"byte",new String[]{
            "byte",
            "java.lang.Byte"
    }) {

        @Override
        public String findFieldDefine4DB(int minLength, int maxLength, int dotNum, boolean isUnique) {
            if(maxLength<0){
                maxLength = 4;
            }
            return "tinyint("+maxLength+")";
        }

        @Override
        public Class findJavaClass() {
            return Byte.class;
        }
    },
    boolean_type(7,"boolean",new String[]{
            "boolean",
            "java.lang.Boolean"
    }) {

        @Override
        public String findFieldDefine4DB(int minLength, int maxLength, int dotNum, boolean isUnique) {
            if(maxLength<0){
                maxLength = 2;
            }
            return "bit("+maxLength+")";
        }

        @Override
        public Class findJavaClass() {
            return Boolean.class;
        }
    },
    char_type(8,"char",new String[]{
            "char",
            "java.lang.Character"
    }) {

        @Override
        public String findFieldDefine4DB(int minLength, int maxLength, int dotNum, boolean isUnique) {
            if(maxLength<0){
                maxLength=50;
            }
            return "varchar("+maxLength+") default 0" ;
        }

        @Override
        public Class findJavaClass() {
            return Character.class;
        }
    },
    string_type(9,"string",new String[]{
            "string",
            "String",
            "java.lang.String"
    }) {

        @Override
        public String findFieldDefine4DB(int minLength, int maxLength, int dotNum, boolean isUnique) {
            int max = maxLength;
            if(max<0){
                max =500;
            }
            if(max >10000){
                return "longtext";
            }else if (max>500){
                return "text("+max+")";
            }else{
                return "varchar("+max+")";
            }
        }

        @Override
        public Class findJavaClass() {
            return String.class;
        }
    },
    decimal_type(10,"decimal",new String[]{
            "decimal",
            "BigDecimal",
            "big_decimal",
            "java.math.BigDecimal"
    }) {

        @Override
        public String findFieldDefine4DB(int minLength, int maxLength, int dotNum, boolean isUnique) {
            return "decimal";
        }

        @Override
        public Class findJavaClass() {
            return BigDecimal.class;
        }
    },

    date_type(11,"date",new String[]{
            "date",
            "java.util.Date",
            "java.sql.Date"
    }) {

        @Override
        public String findFieldDefine4DB(int minLength, int maxLength, int dotNum, boolean isUnique) {
            if(maxLength<0){
                maxLength =6;
            }
            return "timestamp("+maxLength+")";
        }

        @Override
        public Class findJavaClass() {
            return Date.class;
        }
    },
    datetime_type(12,"datetime",new String[]{
            "datetime",
            "java.sql.Time",
            "java.sql.Timestamp"
    }) {

        @Override
        public String findFieldDefine4DB(int minLength, int maxLength, int dotNum, boolean isUnique) {
            if(maxLength<0){
                maxLength =6;
            }
            return "timestamp("+maxLength+")";
        }

        @Override
        public Class findJavaClass() {
            return Date.class;
        }
    },
    time_type(13,"time",new String[]{
            "time"
    }) {

        @Override
        public String findFieldDefine4DB(int minLength, int maxLength, int dotNum, boolean isUnique) {
            return "int(5)";
        }

        @Override
        public Class findJavaClass() {
            return Integer.class;
        }
    };

    private int value;
    private String des;
    private String[] types;
    FieldTypeEnum(int value,String des,String[]types){
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
     * 是否是int型
     * @param type
     * @return
     */
    public static boolean isInt(String type){
        return FieldTypeEnum.int_type.name().equals(type) || (indexOf(FieldTypeEnum.int_type.types, type) > -1);
    }
    /***
     * 是否是double型
     * @param type
     * @return
     */
    public static boolean isDouble(String type){
        return FieldTypeEnum.double_type.name().equals(type) || (indexOf(FieldTypeEnum.double_type.types, type) > -1);
    }

    /***
     * 是否是浮点类型
     * @param type
     * @return
     */
    public static boolean isFloat(String type){
        return FieldTypeEnum.float_type.name().equals(type) || (indexOf(FieldTypeEnum.float_type.types, type) > -1);
    }

    /**
     * 是否是长整形
     * @param type
     * @return
     */
    public static boolean isLong(String type){
        return FieldTypeEnum.long_type.name().equals(type) || (indexOf(FieldTypeEnum.long_type.types, type) > -1);
    }

    /***
     * 是否是短整形
     * @param type
     * @return
     */
    public static boolean isShort(String type){
        return FieldTypeEnum.short_type.name().equals(type) || (indexOf(FieldTypeEnum.short_type.types, type) > -1);
    }
    /***
     * 是否是byte
     * @param type
     * @return
     */
    public static boolean isByte(String type){
        return FieldTypeEnum.byte_type.name().equals(type) || (indexOf(FieldTypeEnum.byte_type.types, type) > -1);
    }

    /**
     * 是否是boolean类型
     * @param type
     * @return
     */
    public static boolean isBoolean(String type){
        return FieldTypeEnum.boolean_type.name().equals(type) || (indexOf(FieldTypeEnum.boolean_type.types, type) > -1);
    }

    /***
     * 是否是char类型
     * @param type
     * @return
     */
    public static boolean isChar(String type){
        return FieldTypeEnum.char_type.name().equals(type) || (indexOf(FieldTypeEnum.char_type.types, type) > -1);
    }

    /***
     * 是否是字符串
     * @param type
     * @return
     */
    public static boolean isString(String type){
        return FieldTypeEnum.string_type.name().equals(type) || (indexOf(FieldTypeEnum.string_type.types, type) > -1);
    }

    /****
     * 是否是高精度数字类型
     * @param type
     * @return
     */
    public static boolean isDecimal(String type){
        return FieldTypeEnum.decimal_type.name().equals(type) || (indexOf(FieldTypeEnum.decimal_type.types, type) > -1);
    }


    /***
     * 是否是日期类型
     * @param type
     * @return
     */
    public static boolean isDate(String type){
        return FieldTypeEnum.date_type.name().equals(type) || (indexOf(FieldTypeEnum.date_type.types, type) > -1);
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
    public static FieldTypeEnum getFieldType(String type){
        FieldTypeEnum[] enums = FieldTypeEnum.class.getEnumConstants();
        for(FieldTypeEnum current: enums) {
            if(current.name().equals(type) || indexOf(current.types,type)>-1) {
                return current;
            }
        }
        return null;
    }

    public static LinkedHashMap<String,Object> findEnumsJson(){
        FieldTypeEnum[] enums = FieldTypeEnum.class.getEnumConstants();
        LinkedHashMap<String,Object> result = new LinkedHashMap<>();
        for(FieldTypeEnum current: enums) {
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


            buffer.append("public static " + FieldTypeEnum.getFieldType(key).findJavaClass().getName() + " get" + FieldTypeEnum.getFieldType(key).findJavaClass().getSimpleName() + "(Map<String,Object> map,String key," + FieldTypeEnum.getFieldType(key).findJavaClass().getName() + " defaultValue){" +
                    "" + FieldTypeEnum.getFieldType(key).findJavaClass().getName()+" result=null;" +
                    "return result;}");
        }
        System.out.println(buffer.toString());
    }

    public abstract String findFieldDefine4DB(int minLength, int maxLength, int dotNum, boolean isUnique);
    public abstract Class findJavaClass();

}
