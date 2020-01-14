package com.newbie.common.util.enums;

import com.alibaba.fastjson.JSON;
import dm.jdbc.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 枚举值接口
 *
 */
public interface OursBaseEnum {
	
	/**
	 * 获取枚举的int值
	 * @return 枚举值
	 */
	int getValue();

	/**
	 * 获取枚举的显示值
	 * @return 显示值
	 */
	default String getDes() {
		return "";
	}

	/**通过value查找枚举对象
	 * @param c 具体枚举的class
	 * @param value 枚举的value
	 */
	static <T extends OursBaseEnum> T findEnumByValue(Class<T> c, int value){
		if(c!=null&&c.isEnum()){
			T[] enums = c.getEnumConstants();
			for(T current: enums) {
				if(current.getValue() == value) {
					return current;
				}
			}
		}
		return null;
	}


	/**
	 * 根据value 字符串来解析枚举列表
	 * @param c 枚举class
	 * @param valueStr 如 1,2,3
	 * @param <T> 泛型
	 * @return 枚举列表
	 */
	static <T extends OursBaseEnum> List<T> findEnumsByValueStr(Class<T> c, String valueStr) {
		if(StringUtil.isEmpty(valueStr)) {
			return null;
		}
		List<T> enums = new ArrayList<>();
		String[] states = valueStr.split("[,]");
		for (String state : states) {
			T enumByValue = OursBaseEnum.findEnumByValue(c, Integer.parseInt(state));
			if(enumByValue != null) {
				enums.add(enumByValue);
			}
		}
		return enums;
	}

	/**
	 * 解析成前端ours-form data格式
	 * @param c c
	 * @param <T> T
	 * @return json
	 */
	static <T extends OursBaseEnum> String data4OursForm(Class<T> c) {
		List<Map<String, Object>> mapList = data4OursFormList(c);
		return JSON.toJSONString(mapList);
	}

	/**
	 * 解析成前端ours-form data格式
	 * @param c c
	 * @param <T> T
	 * @return List
	 */
	static <T extends OursBaseEnum> List<Map<String, Object>> data4OursFormList(Class<T> c) {
		List<Map<String, Object>> dataList = new ArrayList<>();
		if(c!=null&&c.isEnum()) {
			T[] enums = c.getEnumConstants();
			for (T current : enums) {
				Map<String, Object> dataMap = new HashMap<>();
				dataMap.put("display", current.getDes());
				dataMap.put("value", current.getValue());
				Enum currentEnum = (Enum) current;
				dataMap.put("id", currentEnum.name());
				dataList.add(dataMap);
			}
		}
		return dataList;
	}
}
