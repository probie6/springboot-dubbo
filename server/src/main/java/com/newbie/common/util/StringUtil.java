package com.newbie.common.util;

import java.util.Collection;
import java.util.Iterator;

public class StringUtil {
    private StringUtil(){}
    public static boolean isNotEmpty(Object obj){
        return !isNullOrEmpty(obj);
    }
	public static boolean isNullOrEmpty(Object obj) {	
		return obj == null || "".equals(obj.toString());
	}
	public static String toString(Object obj){
		if(obj == null) return "null";
		return obj.toString();
	}
	public static String join(Collection s, String delimiter) {
        StringBuilder buffer = new StringBuilder();
        Iterator iter = s.iterator();
        while (iter.hasNext()) {
            buffer.append(iter.next());
            if (iter.hasNext()) {
                buffer.append(delimiter);
            }
        }
        return buffer.toString();
    }

    public static boolean isEmpty(Object obj) {
        return  isNullOrEmpty(obj);
    }
}


