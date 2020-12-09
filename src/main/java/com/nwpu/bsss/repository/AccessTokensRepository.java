package com.nwpu.bsss.repository;

import com.nwpu.bsss.domain.AccessTokensEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author JerryChan
 * @desc ...
 * @date 2020-12-08 13:09:12
 */
@Repository
public interface AccessTokensRepository extends JpaRepository<AccessTokensEntity,Long> {

    AccessTokensEntity findByAccessToken(String accessToken);

    AccessTokensEntity findByUserId(long userId);
}
