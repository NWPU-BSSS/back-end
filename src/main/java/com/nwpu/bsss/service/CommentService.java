package com.nwpu.bsss.service;


import com.nwpu.bsss.domain.CommentEntity;
import com.nwpu.bsss.response.blog.CommentElement;

import java.util.List;

public interface CommentService {
	
	/**
	 * @return comment id
	 */
	long createComment(long userId, long blogId, long parentId, String content);
	
	List<CommentElement> getCommentList(long blogId);

	long getCommentsNum(long blogId);

	long saveComment(CommentEntity commentEntity);
}
