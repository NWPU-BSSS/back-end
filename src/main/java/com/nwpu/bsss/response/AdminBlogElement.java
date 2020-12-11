package com.nwpu.bsss.response;

import com.nwpu.bsss.domain.BlogEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created: 2020-12-11 19:22:33<b>
 *
 * @author Zejia Lin
 * @see com.nwpu.bsss.controller.AdminController
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminBlogElement {
	
	private long blogId;
	private long userId;
	private String title;
	private String tagA;
	private String tagB;
	private String tagC;
	private String preview;
	
	public AdminBlogElement(BlogEntity entity) {
		this.blogId = entity.getId();
		this.userId = entity.getAuthorId();
		this.title = entity.getTitle();
		this.tagA = entity.getTagA();
		this.tagB = entity.getTagB();
		this.tagC = entity.getTagC();
		this.preview = entity.getContent().substring(0, Math.min(200, entity.getContent().length()));
	}
	
}
