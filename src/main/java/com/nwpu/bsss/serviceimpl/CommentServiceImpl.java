package com.nwpu.bsss.serviceimpl;

import com.nwpu.bsss.domain.CommentEntity;
import com.nwpu.bsss.domain.UserInfoEntity;
import com.nwpu.bsss.repository.CommentRepository;
import com.nwpu.bsss.repository.UserInfoRepository;
import com.nwpu.bsss.response.blog.CommentElement;
import com.nwpu.bsss.service.CommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {

	@Resource
	private CommentRepository commentRepository;

	@Resource
	private UserInfoRepository userInfoRepository;

	@Override
	@Transactional
	public long createComment(long userId, long blogId, long parentId, String content) {
		CommentEntity entity = new CommentEntity(userId, blogId, parentId, content);
		return this.commentRepository.save(entity).getId();
	}

	@Override
	public List<CommentElement> getCommentList(long blogId) {
		Set<CommentEntity> commentEntitySet = this.commentRepository.findRootCommentsByBlogId(blogId);
		List<CommentElement> ret = new ArrayList<>(commentEntitySet.size());
		CommentEntity dummyHead = new CommentEntity();
		dummyHead.setChildren(commentEntitySet);
		this.dfsChildren(1, ret, dummyHead, 1, null);
		return ret;
	}

	@Override
	public long getCommentsNum(long blogId) {
		return this.commentRepository.getBlogCommentsNum(blogId);
	}

	/**
	 * DFS将children展平
	 *
	 * @param ret {@code getCommentList()}中的返回值
	 * @implNote 如果用户ID不存在，则显示“已注销”
	 */
	private void dfsChildren(int level, List<CommentElement> ret, CommentEntity curNode, long parentUserId, String parentNickname) {
		for (CommentEntity entity : curNode.getChildren()) {
			UserInfoEntity parentUserInfo = this.userInfoRepository
					.findById(entity.getUserId())
					.orElse(null);
			long userId = 1L;
			String nickname = "已注销";
			String avatarUrl = "/avatar/path/to/noSuchUser/avatar";// TODO: 11/19/2020 lzj 添加已注销用户路径
			if (parentUserInfo != null) {
				userId = parentUserInfo.getId();
				nickname = parentUserInfo.getNickName();
				avatarUrl = parentUserInfo.getAvatarUrl();
			}
			ret.add(new CommentElement(entity, nickname, avatarUrl, level, parentUserId, parentNickname));
			this.dfsChildren(level + 1, ret, entity, userId, nickname);
		}
	}
}
