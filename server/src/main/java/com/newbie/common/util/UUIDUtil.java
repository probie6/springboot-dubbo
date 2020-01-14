package com.newbie.common.util;

import java.util.UUID;

public class UUIDUtil {
    private UUIDUtil(){}
    /****
     * 获取随机id
     * @return
     */
    public static String getUUID(){
        String id= UUID.randomUUID().toString();
        id = id.replace("-","");
        return id;
    }
}

