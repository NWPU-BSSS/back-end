package com.nwpu.bsss.utils;

import com.nwpu.bsss.controller.UserController;
import com.nwpu.bsss.response.Code;
import com.nwpu.bsss.response.MyResponseEntity;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@Aspect
public class ValidateAspect {
    @Pointcut("execution(* com.nwpu.bsss.controller..*(..))")
    public void checkAccess() {
    }

    @Around("checkAccess()")
    public MyResponseEntity<Object> doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        //写了一个豁免token，暂时先写在这里，但是写在这里肯定有点问题
        UserController.token2Id.put("123456",1L);

        //获取相应参数
        Object[] args = joinPoint.getArgs();
        //获取参数名
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        String accessToken = null;

        for (int i=0;i<argNames.length;i++) {
            if(argNames[i].equals("accessToken")){
                accessToken = args[i].toString();
            }
        }
        Object result = null;
        if (accessToken != null){
            Long userId = UserController.token2Id.get(accessToken);
            if (userId == null) {
                log.error("用户无效");
                return new MyResponseEntity<>(Code.BAD_OPERATION, "token无效", null);
            }
            else{
                result = joinPoint.proceed();
            }
        }
        log.info(accessToken);


        return (MyResponseEntity<Object>) result;
    }
}
