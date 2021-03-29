package com.cosson.library.librarymanagement.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspectConfig {

	@Pointcut("execution (* *..controller.*Controller+.*(..))")
	public void controllerPointcut() {
	}

	@Before("controllerPointcut()")
	public void logApiRequest(JoinPoint point) {
		log.info("API request to controller method {} with parameter {}",
				point.getSignature(), point.getArgs());
	}

	@AfterReturning(pointcut = "controllerPointcut()", returning = "result")
	public void logApiResponse(JoinPoint point, Object result) {
		log.info("API controller method {} with response: {}",
				point.getSignature(), result);
	}
}
