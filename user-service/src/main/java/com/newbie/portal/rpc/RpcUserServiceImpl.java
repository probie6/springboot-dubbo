package com.newbie.portal.rpc;

import com.newbie.application.ICityPopulationService;
import com.newbie.inputdto.GetUserInfoInputDTO;
import com.newbie.outputdto.GetUserInfoOutputDTO;
import com.newbie.pojo.entity.CityPopulation;
import com.newbie.rpc.RpcUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * create by 王飞 on 2020-01-19
 */
@Service(version = "1.0.0", loadbalance = "random", weight = 20)
@org.springframework.stereotype.Service
@Slf4j
public class RpcUserServiceImpl implements RpcUserService {
    @Value("${server.port}")
    private String port;
    @Autowired
    private ICityPopulationService iCityPopulationService;

    @Override
    public GetUserInfoOutputDTO getUserInfo(GetUserInfoInputDTO infoInputDTO) {
        log.info("getUserInfo被调用，port：" + port);
        CityPopulation cityPopulationInfo = iCityPopulationService.getById(infoInputDTO.getId());

        return new ModelMapper().map(cityPopulationInfo, GetUserInfoOutputDTO.class);
    }
}
