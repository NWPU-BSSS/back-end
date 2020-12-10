package com.nwpu.bsss.utils;

import com.nwpu.bsss.domain.AccessTokensEntity;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import com.nwpu.bsss.service.AdminService;
import com.nwpu.bsss.service.TokenCheckService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Component
@Aspect
public class ValidateAspect {
	
	@Resource
	private TokenCheckService tokenCheckService;
	@Resource
	private AdminService adminService;
	
	@Pointcut("execution(* com.nwpu.bsss.controller..*(..))" +
			"&& !execution(* com.nwpu.bsss.controller.AdminController.*(..))" +
			"&& !execution(* com.nwpu.bsss.controller.IdentificationController.*(..))")
	public void checkAccess() {
	}
	
	@Pointcut("execution(* com.nwpu.bsss.controller.AdminController.*(..))")
	public void checkAdminPointCut() {
	}
	
	@Around("checkAdminPointCut()")
	public MyResponseEntity<Object> checkAdmin(ProceedingJoinPoint joinPoint) throws Throwable {
		Object[] args = joinPoint.getArgs();
		List<String> argNames = new ArrayList<String>(Arrays.asList(((MethodSignature) joinPoint.getSignature()).getParameterNames()));
		String admin = (String) args[argNames.indexOf("admin")];
		String password = (String) args[argNames.indexOf("password")];
		long adminId = this.adminService.check(admin, password);
		if (adminId == -1) {
			log.error("管理员密码错误");
			return new MyResponseEntity<>(Code.BAD_OPERATION, "管理员账号或密码错误", null);
		}
		Object result = joinPoint.proceed();
		log.info(String.valueOf(adminId));
		return (MyResponseEntity<Object>) result;
	}
	
	@Around("checkAccess()")
	public MyResponseEntity<Object> doAround(ProceedingJoinPoint joinPoint) throws Throwable {
		
		//获取相应参数
		Object[] args = joinPoint.getArgs();
		//获取方法的参数名
		String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
		String accessToken = null;
		Long userId = null;
		
		for (int i = 0; i < argNames.length; i++) {
//            log.info(argNames[i] + "  " + args[i].toString());
			if (argNames[i].equals("accessToken")) {
				accessToken = args[i].toString();
			} else if (argNames[i].equals("userId")) {
				try {
					userId = Long.parseLong(args[i].toString());
				} catch (NumberFormatException e) {
					return new MyResponseEntity<>(Code.BAD_REQUEST, "userId格式错误", null);
				}
			}
		}
		
		if (accessToken != null) {
			if (userId == null) {
				return new MyResponseEntity<>(Code.BAD_REQUEST, "缺少userId", null);
			}
			AccessTokensEntity entity = this.tokenCheckService.findIdByToken(accessToken);
			if (entity == null || !userId.equals(entity.getUserId())) {
				//没有存Token或者Token和userId不匹配
				log.error("Token无效");
				return new MyResponseEntity<>(Code.INVALID_TOKEN, "token无效", null);
			}
		}
		
		Object result = joinPoint.proceed();
		log.info(accessToken);
		
		return (MyResponseEntity<Object>) result;
	}
}
