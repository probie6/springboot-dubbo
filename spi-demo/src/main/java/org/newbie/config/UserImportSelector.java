package org.newbie.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * create by wangfei on 2020-02-03
 */
@Slf4j
public class UserImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        // 此处可写判断条件
        log.info("load selector," + importingClassMetadata.toString());
        return new String[]{UserConfiguration.class.getName()};
    }
}
