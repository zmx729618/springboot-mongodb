package org.zmx.springboot.filter;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONArray;;

/**
 * @Description： 控制层入参出参拦截，并且计算方法执行时间
 * @date: 2017年5月24日 下午3:18:28 
 * @author zhangyufeng@citicpub.com
 */
@Aspect   //定义一个切面
@Configuration
public class LogRecordAspect {
private static final Logger logger = LoggerFactory.getLogger(LogRecordAspect.class);

    // 定义切点Pointcut
    @Pointcut("execution(* org.zmx.springboot.*.*Controller.*(..))")
    public void excudeService() {
    }

    @Around("excudeService()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
    	//请求唯一标识
    	String traceId = UUID.randomUUID().toString();
    	
    	Long start = System.currentTimeMillis();
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        //获取get中的参数
//        String queryString = request.getQueryString();
      //获取post中的参数
        StringBuffer payload = new StringBuffer("[");
		//访问目标方法的参数：
        Object[] args = pjp.getArgs();
        if (args != null && args.length > 0) {
        	for(Object obj : args){
        		if(obj instanceof HttpServletRequest || obj instanceof HttpServletResponse || obj instanceof  HttpSession){
        			continue;
        		}
        		payload.append(" "+JSONArray.toJSONString(obj));
        	}
        }
        payload.append("]");
        logger.info(traceId+"---请求开始: url: {}, method: {}, uri: {}, params: {}", url, method, uri, payload);

        // result的值就是被拦截方法的返回值
        Object result = pjp.proceed();
        Long end = System.currentTimeMillis();
        // logger.info("请求结束，controller的返回值是 " + JSON.toJSONString(result, SerializerFeature.WriteMapNullValue));
        logger.info(traceId+"---请求结束:" + result);
        logger.info(traceId+"---执行时间："+(end - start)+"ms");
        return result;
    }
}