package com.newbie.common.util;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author 黄茂全
 * @Description
 * @Date 2019/5/11
 */
@Slf4j
public class Tools {

    public static final ModelMapper model = new ModelMapper();

    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat DATE_FORMAT_ZH = new SimpleDateFormat("yyyy年MM月dd日");

    public static String creatUUID(){

        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    /**
     * 取两个Map集合的交集(将map1的值赋给resultMap)
     *
     * @param map1 (有值)
     * @param map2 (没值)
     * @return 两个集合的交集
     */
    public static Map<String, Object> getMapJiaoJi(Map<String, Object> map1, Map<String, Object> map2) {
        Set<String> bigMapKey = map1.keySet();
        Set<String> smallMapKey = map2.keySet();
        Set<String> differenceSet = Sets.intersection(bigMapKey, smallMapKey);
        Map<String, Object> resultMap = Maps.newHashMap();
        for (String key : differenceSet) {
            resultMap.put(key, map1.get(key));
        }
        return resultMap;
    }

    /**
     * 使用reflect进行转换,javabean对象转map集合
     *
     * @param obj
     * @return map集合
     * @throws IllegalAccessException java.lang.IllegalAccessException
     * @throws InstantiationException java.lang.InstantiationException
     */
    public static Map<String, Object> objectToMap(Object obj){
        if (obj == null) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                map.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                log.info(e.getMessage());
            }
        }
        return map;
    }
    /**
     * 使用reflect进行转换,javabean对象转map集合,key为小写
     *
     * @param obj
     * @return map集合
     * @throws IllegalAccessException java.lang.IllegalAccessException
     * @throws InstantiationException java.lang.InstantiationException
     */
    public static Map<String, String> objectToLowerKeyMap(Object obj){
        if (obj == null) {
            return null;
        }
        Map<String, String> map = new HashMap<>();

        Field[] declaredFields = obj.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            try {
                Object value = field.get(obj);
                String val = null;
                if(value !=null){
                    if(value instanceof String){
                        val = value.toString();
                    }else{
                        val = JSON.toJSONString(value);
                    }
                }
                map.put(field.getName().toLowerCase(), val );
            } catch (IllegalAccessException e) {
                log.info(e.getMessage());
            }
        }
        return map;
    }
    /**
     * 将字段map和主键map转为字段和主键list与字段和主键值list
     *
     * @param zdmap
     * @param idMap
     * @return
     */
    public static Map<String, List<Object>> mapTOzdAndzdValueList(Map<String, Object> zdmap, Map<String, Object> idMap) {

        List<Object> zdList = new ArrayList<>();
        List<Object> zdValueList = new ArrayList<>();
        Map<String, List<Object>> ressultMap = new HashMap<>();

        //设置主键列和值
        idMap.forEach((id, idValue) -> {
            zdList.add(id);
            zdValueList.add(idValue);
        });

        //设置其它列和值
        zdmap.remove("CJSJ");
        zdmap.remove("ZHXGSJ");
        zdmap.put("SFSC", "N");
        zdmap.forEach((zd, value) -> {
            zdList.add(zd);
            zdValueList.add(value);
        });
        ressultMap.put("zdList", zdList);
        ressultMap.put("zdValueList", zdValueList);
        return ressultMap;
    }

    /**
     * 英文单双引号转义
     * @param str
     * @return
     */
    public static String strTransform(String str) {
        if (StringUtils.isNotBlank(str)) {
            // 去掉前后空格
            str = str.trim();
            // 英文单引号转义
            str = str.replace("'", "&#39;");
            // 英文双引号转义
            str = str.replace("\"", "\\\"");
        }
        return str;
    }

    /**
     * 当前时分秒替换指定时间的时分秒
     * @param dateStr 指定时间
     * @return
     */
    public static Date addHHmmss(String dateStr){

        String dateTimeStr1 = DATE_TIME_FORMAT.format(new Date());

        String hhmmssStr = dateTimeStr1.substring(10);
        Date date = null;
        try {
            date = DATE_TIME_FORMAT.parse(dateStr + hhmmssStr);
        } catch (ParseException e) {
            log.info(e.getMessage());
        }
        return date;
    }

}
