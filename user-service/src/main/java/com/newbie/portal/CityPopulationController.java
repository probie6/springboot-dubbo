package com.newbie.portal;


import com.newbie.application.ICityPopulationService;
import com.newbie.basic.ResponseResult;
import com.newbie.basic.enums.ResponseTypes;
import com.newbie.pojo.entity.CityPopulation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author com.probie6.probie6
 * @since 2020-01-16
 */

@Api("人口接口")
@RestController
@RequestMapping("/city-population")
public class CityPopulationController {
    @Autowired
    private ICityPopulationService iCityPopulationService;

    @ApiOperation(value="获取人口信息", notes="获取人口信息")
    @GetMapping("/list")
    public ResponseResult list() {
        List<CityPopulation> list = iCityPopulationService.list();
        return new ResponseResult<>(ResponseTypes.SUCCESS, "获取成功", list);
    }

}

