package com.newbie.mapper;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * create by wangfei on 2020-01-07
 */
@Repository
public interface PopulationMapper {
    List<Map<String, String>> list();
}
