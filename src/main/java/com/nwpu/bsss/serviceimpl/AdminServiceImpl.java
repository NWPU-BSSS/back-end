package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.*;
import com.nwpu.bsss.repository.*;
import com.nwpu.bsss.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author alecHe
 * @desc 管理员模块
 * @date 2020-12-07 10:49:29
 */
@Slf4j
@Service
public class AdminServiceImpl implements AdminService {
	
	@Resource
	AnnounRepository announRepository;
	@Resource
	BlogRepository blogRepository;
	@Resource
	UserRepository userRepository;
	@Resource
	AdminRepository adminRepository;
	@Resource
	UserInfoRepository userInfoRepository;
	
	
	@Override
	public long makeAnnounce(AnnouncementsEntity announcementEntity) {
		return this.announRepository.save(announcementEntity).getId();
	}
	
	@Transactional(rollbackFor = {Exception.class})
	@Override
	public boolean deleteBlog(long blogId) {
		//删除一个博客并删除所有依赖于这个博客的记录，如博客的likes
		try {
			BlogEntity blogEntity = this.blogRepository.findByBlogId(blogId);
            this.blogRepository.delete(blogEntity);
		} catch (InvalidDataAccessApiUsageException e){
			log.warn("博客:" + blogId +"不存在");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		} catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}
	
	@Transactional(rollbackFor = {Exception.class})
	@Override
	public boolean deleteUser(long userId) {
		//删除一个用户即删除所有依赖于这个用户的记录，如他的博客
		try {
			UserEntity userEntity = this.userRepository.findUserById(userId);
            this.userRepository.delete(userEntity);
		} catch (InvalidDataAccessApiUsageException e){
			log.warn("用户:" + userId +"不存在");
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return false;
		}
		return true;
	}
	
	@Override
	public long check(String admin, String password) {
		Optional<AdminsEntity> ad = this.adminRepository.findByUsername(admin);
		if (ad.isPresent() && ad.get().getPassword().equals(password)) {
			return ad.get().getId();
		}
		return -1;
	}
	
	@Override
	public List<UserInfoEntity> findAllUsers() {
		return this.userInfoRepository.findAll();
	}
}
