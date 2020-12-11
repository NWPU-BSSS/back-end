package com.nwpu.bsss.service;

import com.nwpu.bsss.domain.AnnouncementsEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.response.AdminBlogElement;

import java.util.List;

/**
 * @author alecHe
 * @desc 管理员服务接口
 * @date 2020-12-07 10:49:17
 */
public interface AdminService {
	/**
	 * 发布博客
	 *
	 * @param announcementEntity 在controller处检验参数结束后传入
	 * @return 博客id
	 */
	long makeAnnounce(AnnouncementsEntity announcementEntity);
	
	List<AdminBlogElement> getAllBlogs();
	
	/**
	 * 强制删除博客
	 *
	 * @param blogId 博客id
	 * @return 删除成功与否
	 */
	boolean deleteBlog(long blogId);
	
	/**
	 * 强制删除用户
	 *
	 * @param userId 用户id
	 * @return 删除成功与否
	 */
	boolean deleteUser(long userId);
	
	/**
	 * Get all users
	 *
	 * @return list of users
	 */
	List<UserInfoEntity> findAllUsers();
	
	/**
	 * @return ID of the admin, -1 if invalid
	 */
	long check(String admin, String password);
}
