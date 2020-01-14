package com.newbie.common.util;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/***
 * 实体转换工具
 * @author 张元平
 */
@Slf4j
public class TransEntityUtil {
    private TransEntityUtil(){}

    /***
     * 根据实体属性模型映射，将实体转为map
     * @param entity
     * @param attrModels
     * @return
     */
    public static Map<String,Object> trans2MapByAttrModels(Object entity, List<AttrModelOutputDTO> attrModels){
        if(entity ==null){
            return  null;
        }
        String str = JSON.toJSONString(entity);
        Map<String,Object> sourceObject = JSON.parseObject(str);
        Map<String,Object> map = new HashMap<>();

        for(AttrModelOutputDTO attrModelOutputDTO: attrModels){
            String sourceKey = attrModelOutputDTO.getSourceProp();
            String key = attrModelOutputDTO.getKey();
            if(sourceObject.get(sourceKey)!=null){
                map.put(key,sourceObject.get(sourceKey));
            }
        }
        return map;
    }

    /**
     * 根据模板和 属性模型 替换内容
     * @param tpl
     * @param model
     * @return
     */
    public static String replaceTpl(String tpl,AttrModelOutputDTO model,String mapName){
        String attrName = model.getKey();
        String setterName ="set"+ attrName.substring(0,1).toUpperCase()+attrName.substring(1);
        String getterName ="find"+ attrName.substring(0,1).toUpperCase()+attrName.substring(1);
        String curr = ""+tpl;
        curr = curr.replace("${display}",model.getDisplay());
        curr = curr.replace("${sourceProp}",model.getSourceProp());
        curr = curr.replace("${upperKey}",model.getKey().toUpperCase());
        curr = curr.replace("${key}",model.getKey());
        curr = curr.replace("${mapName}",mapName);
        curr = curr.replace("${setterName}",setterName);
        curr = curr.replace("${getterName}",getterName);
        return curr;
    }
    /***
     * 获取map setter gettter code
     * @param attrModels
     * @return
     */
    public static String getSetterGetterCode(List<AttrModelOutputDTO> attrModels,String mapName){
        String attrTpl="/** ${display}[${sourceProp}]   **/\n private static final String ${upperKey}=\"${key}\";\n";
        String setterTpl = " /**设置 ${display} @param ${key}*/\n public void ${setterName}(String ${key}) {  ${mapName}.put(${upperKey},${key}!=null?${key}:\"\");  } \n/** 获取${display} @return */\n public String ${getterName}() { return ${mapName}.getOrDefault(${upperKey},\"\");  }\n";
        StringBuilder builder = new StringBuilder();
        builder.append("/*******setter getter 由 TransEntityUtil.getSetterGetterCode 自动生成 *****************************/\n");
        // 输出map的属性key
        for(AttrModelOutputDTO attrModelOutputDTO :attrModels){
            builder.append(replaceTpl(attrTpl,attrModelOutputDTO,mapName));
        }
        //输出 map的setter getter
        for(AttrModelOutputDTO attrModelOutputDTO :attrModels){
            builder.append(replaceTpl(setterTpl,attrModelOutputDTO,mapName));
        }
        return builder.toString();
    }

    /**
     *  给指定某个类的某个map属性 生成setter 和find方法
     * @param c
     * @param mapName
     * @return
     */
    public static String getSetterGetterCode(Class c,String mapName){
        ApiModelProperty apiModelProperty = null;
        try{
            apiModelProperty = c.getDeclaredField(mapName).getAnnotation(ApiModelProperty.class);

        }catch (Exception e){
            log.error("map属性配置属性和说明不满足规则[key:desc,key2:desc,....] ",e);
            return "";
        }

        List<AttrModelOutputDTO> attrModelOutputDTOS = new ArrayList<>();
        String str =apiModelProperty.value();
        String[] arr = str.split(",");
        List<AttrModelOutputDTO> attrs = new ArrayList<>();
        for(String curr :arr){
            if(StringUtil.isNullOrEmpty(curr)){
                continue;
            }
            String key = curr.trim();
            String desc = key.substring(key.indexOf(":")+1);
            key = key.substring(0,key.indexOf(":"));

            AttrModelOutputDTO attrModelOutputDTO = new AttrModelOutputDTO();
            attrModelOutputDTO.setKey(key);
            attrModelOutputDTO.setDisplay(desc);
            attrModelOutputDTO.setSourceProp(key);
            attrs.add(attrModelOutputDTO);
        }

        String code = TransEntityUtil.getSetterGetterCode(attrs,mapName);
        return code;
    }
}

