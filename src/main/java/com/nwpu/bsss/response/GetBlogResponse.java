package com.nwpu.bsss.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Project: bsss
 *
 * @author liuziyu
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class GetBlogResponse {
	protected String title;
	protected String content;
	protected long likeNum;
	protected long commentNum;
	protected long shareNum;
	protected long favoriteNum;
	
}



