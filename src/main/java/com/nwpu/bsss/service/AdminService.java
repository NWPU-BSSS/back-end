package com.nwpu.bsss.service;

import com.nwpu.bsss.domain.AnnouncementsEntity;

/**
 * @author alecHe
 * @desc 管理员服务接口
 * @date 2020-12-07 10:49:17
 */
public interface AdminService {
    /**
     * 发布博客
     * @param announcementEntity 在controller处检验参数结束后传入
     * @return 博客id
     */
    long makeAnnounce(AnnouncementsEntity announcementEntity);

    /**
     * 强制删除博客
     * @param blogId 博客id
     * @return 删除成功与否
     */
    boolean deleteBlog(long blogId);

    /**
     * 强制删除用户
     * @param userId 用户id
     * @return 删除成功与否
     */
    boolean deleteUser(long userId);

    long check(String admin, String password);
}
