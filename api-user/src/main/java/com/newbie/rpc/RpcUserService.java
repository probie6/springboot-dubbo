package com.newbie.rpc;

import com.newbie.inputdto.GetUserInfoInputDTO;
import com.newbie.outputdto.GetUserInfoOutputDTO;

public interface RpcUserService {
    GetUserInfoOutputDTO getUserInfo(GetUserInfoInputDTO infoInputDTO);
}
