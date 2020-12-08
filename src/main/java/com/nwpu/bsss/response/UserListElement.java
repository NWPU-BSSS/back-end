package com.nwpu.bsss.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 这是前端要求的数组中的<b>一个元素</b>的格式，在返回时记得把它用List包起来
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserListElement {
	
	private long userId;
	private String username;
	private String nickname;
	private String avatar;
	private String introduction;
	private int gender;
	
}
