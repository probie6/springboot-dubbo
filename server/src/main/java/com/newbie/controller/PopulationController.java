package com.newbie.controller;

import com.newbie.mapper.PopulationMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.util.Map;


/**
 * create by wangfei on 2019-12-25
 */
@Api("城市人口接口")
@RestController
@RequestMapping("/api/population")
@Slf4j
public class PopulationController {

    @Autowired
    private PopulationMapper populationMapper;

    @Autowired
    private DataSource druidDataSource;

    @PostMapping(value = "/list",name = "post测试")
    @ApiOperation("获取列表")
    public String list() {

        log.info(druidDataSource.getClass().toString());
        log.info(populationMapper.list().toString());
        return "post success";
    }
}
