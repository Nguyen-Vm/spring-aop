package org.linker.aop;

import io.swagger.annotations.ApiOperation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.linker.model.AccessLog;
import org.linker.service.AccessLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author RWM
 * @date 2018/8/29
 */
//@Aspect
//@Component
public class OperationAspect {

    @Autowired
    AccessLogService accessLogService;

    @Around("@annotation(operation)")
    public Object operationLog(ProceedingJoinPoint joinPoint, ApiOperation operation){
        AccessLog accessLog = new AccessLog();
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录下请求内容
        accessLog.id = UUID.randomUUID().toString().replace("-", "");
        accessLog.url = request.getRequestURL().toString();
        accessLog.httpMethod = request.getMethod();
        accessLog.ip = request.getRemoteAddr();
        accessLog.classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        accessLog.args = Arrays.toString(joinPoint.getArgs());
        accessLog.api = operation.value();
        try {
            Object o = joinPoint.proceed();
            System.out.println("方法环绕proceed，结果是 :" + o);
            accessLog.response = o.toString();
            accessLogService.save(accessLog);
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }
}
