package com.nwpu.bsss.security;

import com.nwpu.bsss.domain.UserEntity;
import com.nwpu.bsss.service.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailsService implements UserDetailsService {
	
	@Resource
	private UserService userService;
	@Resource
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
		UserEntity userEntity = this.userService.findByUserEmail(s);
		if (userEntity == null) {
			throw new UsernameNotFoundException("User not found");
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		// TODO: 11/19/2020 lzj 由于数据库没有role了，为了把程序跑起来随便改了这一行，据说好像security要重写所以这里不管啦
		authorities.add(new SimpleGrantedAuthority("ROLE_1"));
		return new User(userEntity.getUserName(), this.passwordEncoder.encode(userEntity.getPassword()), authorities);
	}
}
