package com.newbie.application.impl;

import com.newbie.pojo.entity.CityPopulation;
import com.newbie.mapper.CityPopulationMapper;
import com.newbie.application.ICityPopulationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author com.probie6.probie6
 * @since 2020-01-16
 */
@Service
@Slf4j
public class CityPopulationServiceImpl extends ServiceImpl<CityPopulationMapper, CityPopulation> implements ICityPopulationService {

}
