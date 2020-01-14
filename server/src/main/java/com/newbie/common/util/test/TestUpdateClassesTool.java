package com.newbie.common.util.test;
import com.tyyw.lcba.jcjd.util.FileOperate;
import java.util.HashMap;
import java.util.Map;

public class TestUpdateClassesTool {
    public static void main(String[]args){
        FileOperate fo = new FileOperate();
        //修改实体类
        Map<String,String> replaceMap =new HashMap<>();
        replaceMap.put("implements Serializable","extends BaseEntity");
        replaceMap.put("import java.io.Serializable;","import com.newbie.core.audit.BaseEntity;");
        replaceMap.put("private static final long serialVersionUID = 1L;","");

        replaceMap.put("\n" +
                "    /**\n" +
                "     * 是否删除\n" +
                "     */\n" +
                "    @TableField(\"SFSC\")\n" +
                "    private String sfsc;\n","");
        replaceMap.put("\n" +
                "    /**\n" +
                "     * 创建时间\n" +
                "     */\n" +
                "    @TableField(\"CJSJ\")\n" +
                "    private Date cjsj;","");
        replaceMap.put("\n" +
                "    /**\n" +
                "     * 最后修改时间\n" +
                "     */\n" +
                "    @TableField(\"ZHXGSJ\")\n" +
                "    private Date zhxgsj;\n","");
        replaceMap.put("\n" +
                "    /**\n" +
                "     * 数据标识编号\n" +
                "     */\n" +
                "    @TableField(\"SJBSBH\")\n" +
                "    private String sjbsbh;\n","");
        replaceMap.put("\n" +
                "    /**\n" +
                "     * 数据来源网络(1.是1.0的工作网 2.是1.0的专网 3.是2.0的工作网,4.是2.0的专网)\n" +
                "     */\n" +
                "    @TableField(\"SJLY\")\n" +
                "    private String sjly;\n","");
        // 变更服务类增加日志
        replaceMap.put("import org.springframework.stereotype.Service;","import org.springframework.stereotype.Service;\nimport lombok.extern.slf4j.Slf4j;");
        replaceMap.put("@Service","@Service\n@Slf4j\n");

        //更新控制器路径
        replaceMap.put("@RequestMapping(\"/jcjd/","@RequestMapping(\"/api/");

        //修改控制器 Api Tag
        replaceMap.put("@RestController","@RestController\n" +
                "@Api(tags = \"TODO 控制器说明\")");
        replaceMap.put("import org.springframework.web.bind.annotation.RestController;",
                "import org.springframework.web.bind.annotation.RestController;\n" +
                "import io.swagger.annotations.Api;");
        //更新 实体类
        fo.renameBatch("D:\\projects\\server\\jcjd-service\\src\\main\\java\\com\\tyyw\\lcba\\jcjd\\domain",replaceMap);
        //更新控制器 根路径
        fo.renameBatch("D:\\projects\\server\\jcjd-service\\src\\main\\java\\com\\tyyw\\lcba\\jcjd\\portal",replaceMap);
        //更新service 增加日志
        fo.renameBatch("D:\\projects\\server\\jcjd-service\\src\\main\\java\\com\\tyyw\\lcba\\jcjd\\application",replaceMap);

        //更新 mapper
        Map<String,String> replace4MyBatisMapper = new HashMap<>();
        replace4MyBatisMapper.put("import com.baomidou.mybatisplus.core.mapper.BaseMapper;",
                "import com.baomidou.mybatisplus.core.mapper.BaseMapper;\n" +
                "import org.apache.ibatis.annotations.Param;\n" +
                "import org.springframework.stereotype.Repository;");
        replace4MyBatisMapper.put("public interface","@Repository\npublic interface");
        //更新 mapper 用于在service中可注入
        fo.renameBatch("D:\\projects\\server\\jcjd-service\\src\\main\\java\\com\\tyyw\\lcba\\jcjd\\infrastructure",replace4MyBatisMapper);



    }
}
