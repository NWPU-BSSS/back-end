package com.nwpu.bsss.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)


public class GetBlogResponse {
	protected String title;
	protected String content;
	protected long likeNum;
	protected long commentNum;
	protected long shareNum;
	protected long favoriteNum;
	
}



