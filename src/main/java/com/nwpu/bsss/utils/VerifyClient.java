package com.nwpu.bsss.utils;

import com.dtflys.forest.annotation.Get;

/**
 * 用于向验证服务器发送http验证请求
 */
public interface VerifyClient {

    @Get(url = "http://www.rain7yukee.xyz:9277/api/sendverifycode?email=${0}")
    String sentVerifyCode(String email);

    @Get(url = "http://www.rain7yukee.xyz:9277/api/verifyemail?email=${0}&code=${1}")
    String verifyEmail(String email, String code);

}
