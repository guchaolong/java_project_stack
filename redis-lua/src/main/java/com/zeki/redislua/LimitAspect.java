package com.zeki.redislua;

import com.google.common.collect.Lists;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Description:
 * <p>
 * 拦截器 拦截 @RateLimit 注解的方法，使用Redsi execute 方法执行我们的限流脚本，判断是否超过限流次数，
 *
 * @author AA
 * @date 2020/10/10 0:57
 */
@Aspect
@Configuration
public class LimitAspect {

    private static final Logger logger = LoggerFactory.getLogger(LimitAspect.class);

    @Autowired
    private RedisTemplate<String, Serializable> limitRedisTemplate;


    @Autowired
    private DefaultRedisScript<Number> redisluaScript;


    @Around("execution(* com.zeki.redislua ..*(..) )")//要拦截的方法
    public Object interceptor(ProceedingJoinPoint joinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Class<?> targetClass = method.getDeclaringClass();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);

        if (rateLimit != null) {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String ipAddress = getIpAddr(request);

            //生产用于Lua脚本的key
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(ipAddress).append("-")
                    .append(targetClass.getName()).append("- ")
                    .append(method.getName()).append("-")
                    .append(rateLimit.key());

            //List设置Lua的KEY[1]
//            List<String> strings = Lists.newArrayList(stringBuffer.toString());
            List<String> keys = Collections.singletonList(stringBuffer.toString());

            /*
            调用并执行Lua脚本
            redisluaScript: 脚本对象
            keys: 键的集合，在脚本中使用 KEY[1]、KEY[2]...来获取
            后面是附加的参数，可以有多个,在脚本中使用 ARGV[1] ARGV[2]...来获取
            */
            Number number = limitRedisTemplate.execute(redisluaScript, keys, rateLimit.count(), rateLimit.time());

            //返回0表示超出流量大小
            if (number != null && number.intValue() != 0 && number.intValue() <= rateLimit.count()) {
                logger.info("限流时间段内访问第：{} 次", number.toString());
                return joinPoint.proceed();
            } else {
                //服务降级
                return fallback();
            }

        } else {
            return joinPoint.proceed();
        }
    }

    private static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = "";
        }
        return ipAddress;
    }

    private static String fallback() {
        logger.info("已经到设置限流次数");

        return "哎呀！服务器顶不住了！";
        //由于本文没有配置公共异常类，如果配置可替换
//        throw new RuntimeException("已经到设置限流次数");
    }
}
