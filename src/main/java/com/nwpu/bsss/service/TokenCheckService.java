package com.nwpu.bsss.service;

import com.nwpu.bsss.domain.AccessTokensEntity;

/**
 * @author JerryChan
 * @desc ... 用于查验AccessToken相关信息
 * @date 2020-12-08 16:25:09
 */
public interface TokenCheckService {

    AccessTokensEntity findIdByToken(String accessToken);

    AccessTokensEntity findIdByUserId(long id);

    void insertToken(AccessTokensEntity entity);

}
