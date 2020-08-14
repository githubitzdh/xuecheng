package com.czxy.xuecheng.domain.ucenter.request;

import com.czxy.xuecheng.common.model.request.RequestData;
import lombok.Data;
import lombok.ToString;

/**
 *
 */
@Data
@ToString
public class LoginRequest extends RequestData {

    String username;
    String password;
    String verifycode;

}
