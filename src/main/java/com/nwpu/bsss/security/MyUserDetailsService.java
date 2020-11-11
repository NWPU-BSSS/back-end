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
        UserEntity userEntity = userService.findByUserEmail(s);
        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + userEntity.getRole()));
        return new User(userEntity.getUserName(), passwordEncoder.encode(userEntity.getPassword()), authorities);
    }
}
