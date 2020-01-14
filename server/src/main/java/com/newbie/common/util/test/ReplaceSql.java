package com.newbie.common.util.test;

import com.alibaba.fastjson.JSONObject;
import com.tyyw.lcba.jcjd.util.FileOperate;
import com.tyyw.lcba.jcjd.util.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/***
 * sql脚本 替换
 */
public class ReplaceSql {
    /**
     * 达蒙数据库建表语句转 oracle 建表脚本
     * @param sqlTpl
     * @return
     */
    public static String dm2oracleSql(String sqlTpl){
        return sqlTpl.replace("\"TYYW2_LCBA\".","")
                .replace("\"","")
                .replace("STORAGE(ON LCBA_DATA, CLUSTERBTR)","")
                .replace("NOT CLUSTER","");
    }

    /**
     * 根据sql获取所有的表名
     * @param createSql
     * @return
     */
    public static List<String> getAllTableNames(String createSql){
        String[] arr = createSql.split("CREATE TABLE");
        List<String> tbList = new ArrayList<>();
        for(String curr :arr){
            String temp = curr.trim();
            if(StringUtil.isNotEmpty(temp)){
                String tbName = temp.substring(0,temp.indexOf("("));
                tbName =tbName.substring(tbName.indexOf(".")+1).trim();
                tbName=tbName.replace("\"","");
                tbList.add(tbName);
            }
        }
        return tbList;
    }
    public static void main(String[] args) throws IOException {
        FileOperate fo = new FileOperate();
        String sqlTpl = fo.readTxt("D:\\projects\\server\\jcjd-service\\src\\main\\resources\\sql\\jcjd.sql","utf-8");
        List<String>tbList = getAllTableNames(sqlTpl);
        String json = JSONObject.toJSONString(tbList);
        System.out.println(json);
        //System.out.println(sql);

    }
}
