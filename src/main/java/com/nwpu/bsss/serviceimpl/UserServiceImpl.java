package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.*;
import com.nwpu.bsss.exceptions.NoUserFoundException;
import com.nwpu.bsss.repository.*;
import com.nwpu.bsss.response.UserSubscribeStatusResponse;
import com.nwpu.bsss.service.UserService;

import com.nwpu.bsss.utils.FileComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service    //注入spring容器
public class UserServiceImpl implements UserService {

    @Resource
    private UserRepository userRepository;

    @Resource
    private UserInfoRepository userInfoRepository;

    @Resource
    private FollowRepository followRepository;

    @Resource
    private BlogRepository blogRepository;

    @Resource
    private CommentRepository commentRepository;

    @Resource
    private FavoriteRepository favoriteRepository;

    @Autowired
    private FileComponent fileComponent;

    @Value("${avatarPath}")
    private String avatarPath;

    @Override
    @Transactional
    public long createUser(UserEntity userEntity) {
        return userRepository.save(userEntity).getId();
    }

    @Override
    public void deleteUser(UserEntity userEntity) {
        //TODO:这里临时做了一个删除行的操作，后期需要使用时需要考虑各方面安全问题
        userRepository.delete(userEntity);
    }

    @Override
    public long createUserInfo(UserInfoEntity userInfoEntity) {
        return userInfoRepository.save(userInfoEntity).getId();
    }

    @Override
    @Transactional
    public void updateUserEntity(UserEntity userEntity){
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void updateUserInfoEntity(UserInfoEntity userInfoEntity){
        userInfoRepository.save(userInfoEntity);
    }

    @Override
    public UserEntity findByUserID(long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public UserEntity findByUserEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserInfoEntity findUserInfoByUserId(Long id) {return userInfoRepository.findUserInfoById(id);}

    public UserEntity findByUsername(String username) {
        return userRepository.findUserByUserName(username);
    }

    @Override
    public UserSubscribeStatusResponse findUserSubscribeStatusResponse(Long userId,Long bloggerId){
        FollowEntity follow = new FollowEntity();
        UserSubscribeStatusResponse userSubscribeStatusResponse = new UserSubscribeStatusResponse();
        follow = followRepository.getFollow(userId,bloggerId);
        if(follow==null){
            userSubscribeStatusResponse.setStatus(false);
        }
        else{
            userSubscribeStatusResponse.setStatus(true);
        }
        return userSubscribeStatusResponse;
    }

    @Override
    public String setUserAvatar(MultipartFile file, long userId) throws IOException {
        final String defalut = "/avatar/default";
        String url;

        Optional<UserInfoEntity> user = userInfoRepository.findById(userId);

        if(user.isEmpty()){
            throw new NoUserFoundException("no User");
        }
        UserInfoEntity userEntity = user.get();
        if(defalut.equals(userEntity.getAvatarUrl())){
            //上传用户头像
            url = fileComponent.uploadFile(file,userId,avatarPath);

        }else{
            //更新用户头像
            String oldURL = userEntity.getAvatarUrl();
            url = fileComponent.updateFile(file,userId,oldURL,avatarPath);
        }
        userEntity.setAvatarUrl(url);
        userInfoRepository.save(userEntity);
        return url;
    }

    @Override
    public long getUserBlogNumByUserId(long id) {
        return blogRepository.findAllByAuthorId(id).size();
    }

    @Override
    public long getUserFanNumByUserId(long id) {
        return followRepository.findAllByBlogerId(id).size();
    }

    @Override
    public long getUserCommentNumByUserId(long id) {
        return commentRepository.findAllByUserId(id).size();
    }

    @Override
    public long getFavoriteNumByUserId(long id) {
        Set<BlogEntity> blogs = blogRepository.findAllByAuthorId(id);
        long num = 0L;
        for (BlogEntity entity : blogs){
            num += favoriteRepository.findAllByBlogId(entity.getId()).size();
        }
        return num;
    }

}
