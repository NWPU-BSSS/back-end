package com.nwpu.bsss.repository;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserInfoRepositoryTest {
	
	@Resource
	private UserInfoRepository userInfoRepository;
	
	@Test
	void findUserInfoById() {
		System.out.println(this.userInfoRepository.findUserInfoById(1L));
	}
}