package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.repository.UserInfoRepository;
import com.nwpu.bsss.repository.UserRepository;
import com.nwpu.bsss.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service    //注入spring容器
public class UserServiceImpl implements UserService {
	
	@Resource
	private UserRepository userRepository;
	
	@Resource
	private UserInfoRepository userInfoRepository;
	
	@Override
	@Transactional
	public long createUser(UserEntity userEntity) {
		return this.userRepository.save(userEntity).getId();
	}
	
	@Override
	public void deleteUser(UserEntity userEntity) {
		//TODO:这里临时做了一个删除行的操作，后期需要使用时需要考虑各方面安全问题
		this.userRepository.delete(userEntity);
	}
	
	@Override
	public void createUserInfo(UserInfoEntity userInfoEntity) {
		this.userInfoRepository.save(userInfoEntity);
	}
	
	@Override
	public UserEntity findByUserID(long id) {
		return this.userRepository.findUserById(id);
	}
	
	@Override
	public UserEntity findByUserEmail(String email) {
		return this.userRepository.findByEmail(email);
	}
	
	@Override
	public UserInfoEntity findUserInfoByUserId(Long id) {
		return this.userInfoRepository.findUserInfoById(id);
	}
	
	@Override
	public UserEntity findByUsername(String username) {
		return this.userRepository.findUserByUserName(username);
	}
	
	@Override
	public List<UserInfoEntity> findAll() {
		return this.userInfoRepository.findAll();
	}
}
