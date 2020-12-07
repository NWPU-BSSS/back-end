package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.AdminsEntity;
import com.nwpu.bsss.domain.AnnouncementsEntity;
import com.nwpu.bsss.domain.BlogEntity;
import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.repository.AdminRepository;
import com.nwpu.bsss.repository.AnnounRepository;
import com.nwpu.bsss.repository.BlogRepository;
import com.nwpu.bsss.repository.UserRepository;
import com.nwpu.bsss.service.AdminService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author alecHe
 * @desc 管理员模块
 * @date 2020-12-07 10:49:29
 */
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


    @Override
    public long makeAnnounce(AnnouncementsEntity announcementEntity) {
        return this.announRepository.save(announcementEntity).getId();
    }

    @Transactional(rollbackFor = { Exception.class })
    @Override
    public boolean deleteBlog(long blogId) {
        //删除一个博客并删除所有依赖于这个博客的记录，如博客的likes
        try {
            BlogEntity blogEntity = blogRepository.findByBlogId(blogId);
            blogRepository.delete(blogEntity);
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Transactional(rollbackFor = { Exception.class })
    @Override
    public boolean deleteUser(long userId) {
        //删除一个用户即删除所有依赖于这个用户的记录，如他的博客
        try {
            UserEntity userEntity = userRepository.findUserById(userId);
            userRepository.delete(userEntity);
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
        return true;
    }

    @Override
    public long check(String admin, String password) {
        Optional<AdminsEntity> ad = adminRepository.findByUsername(admin);
        if(ad.isPresent() && ad.get().getPassword().equals(password)){
            return ad.get().getId();
        }
        return -1;
    }
}
