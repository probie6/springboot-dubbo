package com.newbie.common.util;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

/**
 * @Describe
 * @Author: islibaodong
 * @Date: 2019/12/25 4:11 下午
 */
@Slf4j
@Data
public class JavaClassPareseUtil {

    public static void main(String[] args) throws Exception {

        Class<?> aClass = Class.forName("com.tyyw.lcba.rw.rwfw.portal.RwYxZbajCrl");


        String baseurl = JavaClassPareseUtil.getBaseUrl("http://ysj-service/", aClass);
        String ctrName = JavaClassPareseUtil.getCtrApiInfo(aClass);

        List<Method> methods = Arrays.asList(aClass.getMethods());

        if (!methods.isEmpty()) {
            for (Method method : methods) {
                if (StringUtils.equalsAny(method.getName(),
                        "wait", "equals", "toString", "hashCode", "getClass", "notify",
                        "notifyAll")) continue;

                if ("getQx".equals(method.getName())) {
                    System.out.println("===========getQx method=====================");
                }
                JavaClassPareseUtil.getWebApiInfo(aClass, baseurl, method);
                System.out.println("========================================");
            }
        }


    }

    /**
     * 获取控制层根url
     *
     * @param baseurl
     * @param aClass
     * @return
     * @throws Exception
     */
    public static String getBaseUrl(String baseurl, Class<?> aClass) throws Exception {
        String[] ctrRequstInfo = JavaClassPareseUtil.getCtrRequestMappingtInfo(aClass);
        if (StringUtils.isNotBlank(ctrRequstInfo[0])) {
            baseurl = baseurl + ctrRequstInfo[0];
        } else {
            throw new Exception("控制层@RequestMapping 值为空！");
        }
        if (StringUtils.isNotBlank(baseurl) && !baseurl.endsWith("/")) {
            baseurl = baseurl + "/";
        }
        return baseurl;
    }

    /**
     * 获取控制层@RequestMapping 值
     *
     * @param aClass
     * @return
     */
    public static String[] getCtrRequestMappingtInfo(Class<?> aClass) {
        RequestMapping annotation = aClass.getAnnotation(RequestMapping.class);
        if (annotation != null) {
            return annotation.value();
        }
        return null;
    }

    /**
     * 获取控制层@Api tags值
     *
     * @param aClass
     * @return
     */
    public static String getCtrApiInfo(Class<?> aClass) {
        Api api = aClass.getAnnotation(Api.class);
        if (api != null && api.tags() != null) {
            return api.tags()[0];
        }
        return null;
    }

    /**
     * 获取web api 接口 信息
     * @param aClass
     * @param baseUrl
     * @param method
     * @throws Exception
     */
    public static void getWebApiInfo(Class<?> aClass, String baseUrl, Method method) throws Exception {

        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        ApiOperation apiOperation = method.getAnnotation(ApiOperation.class);
        String jkfw = baseUrl;
        String jkmc = "";
        String qqfs = "POST";
        String jkcs = "";
        String sc = "";

        if (postMapping != null && postMapping.value().length > 0) {
            String ur = postMapping.value()[0];
            if (StringUtils.isNotBlank(ur) && ur.startsWith("/")) {
                ur = ur.substring(1);
            }
            jkfw = jkfw + ur;
        }
        if (getMapping != null && getMapping.value().length > 0) {
            String urg = getMapping.value()[0];
            if (StringUtils.isNotBlank(urg) && urg.startsWith("/")) {
                urg = urg.substring(1);
            }
            qqfs = "GET";
            jkfw = jkfw + urg;

        }
        if (apiOperation != null) {
            jkmc = apiOperation.value();
        }

        System.out.println("接口形态 Http（RESTFul）");
        System.out.println("接口名称 " + jkmc);
        System.out.println("接口访问 " + jkfw);
        System.out.println("请求方式 " + qqfs);

        Type parameterType = method.getParameterTypes()[0];
        System.out.println("接口参数 " + JsonFormatTool.formatJson(JSON.toJSONString(
                ObjectUtil.getEntityJsonDesc(((Class<?>)parameterType).newInstance()))
                .replaceAll("null", "\"\"")));


        //Type genericType = method.getGenericReturnType();

        System.out.println("输出 " + JsonFormatTool.formatJson(JSON.toJSONString(
                ObjectUtil.getEntityJsonDesc(method.getReturnType(),method.getGenericReturnType()))
                .replaceAll("null", "\"\"")));


        return;
    }




















    public static String getJsonFormartObj(Class<?> clazz, Method method, boolean isRetrun) throws Exception {

        StringBuilder jsonStr = new StringBuilder().append("{");
        Field[] declaredFields = clazz.getDeclaredFields();
        List<Field> fields = Arrays.asList(declaredFields);
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            jsonStr.append("\"").append(field.getName()).append("\":");
            Class<?> fieldType = field.getType();
            String fxObjName = JavaClassPareseUtil.getListfx(field.getGenericType().getTypeName());
            if ("java.lang.String".equalsIgnoreCase(fieldType.getTypeName())) {
                if (StringUtils.equals(field.getName(), "code")) {
                    jsonStr.append("\"200\"");
                } else if (StringUtils.equals(field.getName(), "message")) {
                    jsonStr.append("\"提示信息\"");
                } else {
                    ApiModelProperty apiModelProperty = field.getAnnotation(ApiModelProperty.class);
                    if (apiModelProperty != null) {
                        apiModelProperty.value();
                        apiModelProperty.name();
                        jsonStr.append("\"").append(apiModelProperty.value()).append("\"");

                    } else {
                        jsonStr.append("\"\"");
                    }
                }
            } else if (StringUtils.equalsAny(fieldType.getTypeName(),
                    "java.lang.Long",
                    "java.lang.Integer", "int", "long")) {
                jsonStr.append(0);
            } else if (StringUtils.equals(fieldType.getTypeName(), "boolean")) {
                jsonStr.append(true);
            } else if (StringUtils.equals(fieldType.getTypeName(), "java.util.List")) {

                if (StringUtils.equalsAny(fxObjName,
                        "java.lang.Long",
                        "java.lang.Integer", "int", "long", "java.lang.String")) {
                    jsonStr.append("[ ]");
                } else {
                    String s = JSON.toJSONString(ObjectUtil.getEntityJsonDesc(Class.forName(fxObjName).newInstance()));
                    jsonStr.append("[").append(s).append("]");
                }
            } else {
                if (isRetrun) {
                    String typeName = JavaClassPareseUtil.getListfx(method.getGenericReturnType().getTypeName());
                    if ("T".equals(typeName)) {
                        continue;
                    }
                    String s = JSON.toJSONString(ObjectUtil.getEntityJsonDesc(Class.forName(typeName).newInstance()));
                    jsonStr.append(s);

                } else {
                    jsonStr.append("{ }");
                }
            }
            if (i != fields.size() - 1) {
                jsonStr.append(",");
            }


            jsonStr.append("");
        }
        return jsonStr.append("}").toString();

    }

    /**
     * 获取泛型类
     *
     * @return
     */
    public static String getListfx(String classname) throws Exception {

        //java.util.List<java.lang.String>

        if (classname.contains("java.util.List<") || classname.contains("<")) {
            return classname.substring(classname.lastIndexOf("<") + 1, classname.indexOf(">"));
        } else if (classname.contains("java.util.Map<")) {
            throw new Exception("bu la bu la bu la ,shen me gui wan yi er !!!");
        }
        return classname;
    }

}
