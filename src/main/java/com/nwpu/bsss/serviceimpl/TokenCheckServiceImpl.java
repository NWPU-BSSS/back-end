package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.AccessTokensEntity;
import com.nwpu.bsss.repository.AccessTokensRepository;
import com.nwpu.bsss.service.TokenCheckService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author JerryChan
 * @desc ...
 * @date 2020-12-08 16:38:34
 */
@Service
public class TokenCheckServiceImpl implements TokenCheckService {

    @Resource
    private AccessTokensRepository accessTokensRepository;

    @Override
    public AccessTokensEntity findIdByToken(String accessToken) {
        return this.accessTokensRepository.findByAccessToken(accessToken);
    }

    @Override
    public AccessTokensEntity findIdByUserId(long id) {
        return this.accessTokensRepository.findByUserId(id);
    }

    @Override
    public void insertToken(AccessTokensEntity entity) {
        this.accessTokensRepository.save(entity);
    }
}
