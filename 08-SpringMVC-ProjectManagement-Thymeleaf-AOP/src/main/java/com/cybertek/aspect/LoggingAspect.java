package com.cybertek.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Aspect
@Configuration
public class LoggingAspect {

	Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	
	@Before( "anyControllerOperation()")
	public void anyBeforeControllerOperationAdvice(JoinPoint joinPoint) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		
		logger.info("Before ( User : {}  Method : {} - Parameters : {} ",auth.getName(),joinPoint.getSignature().toShortString(),joinPoint.getArgs());
		


	}
	@AfterReturning( pointcut =  "anyControllerOperation()", returning="results")
	public void anyAfterReturningControllerOperationAdvice(JoinPoint joinPoint,Object results) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("AfterReturning ( User : {}  Method : {} - Results : {} ",auth.getName(),joinPoint.getSignature().toShortString(),results.toString());
		


	}
	@AfterThrowing( pointcut =  "anyControllerOperation()", throwing="exception")
	public void anyAfterThrowingControllerOperationAdvice(JoinPoint joinPoint,RuntimeException exception) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("After Throwing (  User : {} ->  Method : {} - Exception : {} ",auth.getName(),joinPoint.getSignature().toShortString(),exception.getLocalizedMessage());
		


	}
	
	

	@Pointcut("execution(* com.cybertek.controller.ProjectController.*(..)) || execution(* com.cybertek.controller.TaskController.*(..))")
	private void anyControllerOperation() {} 
	
	


}
