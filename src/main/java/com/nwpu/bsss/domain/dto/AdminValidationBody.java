package com.nwpu.bsss.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created: 2020-12-10 15:59:05<b>
 *
 * @author Zejia Lin
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminValidationBody {
	
	private String admin;
	private String password;
	
}
