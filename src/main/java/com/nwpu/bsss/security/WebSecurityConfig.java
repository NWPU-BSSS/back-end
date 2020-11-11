package com.nwpu.bsss.security;

import com.nwpu.bsss.security.handler.LoginFailureHandler;
import com.nwpu.bsss.security.handler.LoginSuccessHandler;
import com.nwpu.bsss.security.handler.MyLogoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) //启用方法级别针对@PreAuthorize的拦截
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private MyUserDetailsService myUserDetailsService;

    @Resource
    private LoginSuccessHandler loginSuccessHandler;
    @Resource
    private LoginFailureHandler loginFailureHandler;
    @Resource
    private MyLogoutHandler logoutHandler;

    //datasource的配置是通过配置文件加载过来的
    @Resource
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //禁用跨站csrf攻击防御
                .formLogin()
//                .loginPage("/login")//用户未登录时，访问任何资源都转跳到该路径，即登录页面
                .loginProcessingUrl("/user/login")
                .successHandler(loginSuccessHandler)
                .failureHandler(loginFailureHandler)
                .permitAll()
//                .defaultSuccessUrl("/success")//登录认证成功后默认转跳的路径
                .and()
                .rememberMe()
                .userDetailsService(myUserDetailsService)
                .tokenRepository(persistentTokenRepository())   //token持久化方案
                .tokenValiditySeconds(600)   //token有效期
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(new Http403ForbiddenEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/login","/").permitAll()//不需要通过登录验证就可以被访问的资源路径
                .anyRequest().authenticated()
                .and()
                .logout()
//                .logoutSuccessUrl("/")
                .logoutSuccessHandler(logoutHandler)
                .deleteCookies("remember-me");

    }

    @Bean   //注入passedEncoder
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();

        //create table 只能用一次，第二次往后记得注释掉
//        jdbcTokenRepository.setCreateTableOnStartup(true);

        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}
