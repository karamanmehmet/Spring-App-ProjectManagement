package com.cybertek.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class PerformanceAspect {

	Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);

	@Around("anyExecutionTimeOperation()")
	public Object anyExecutionTimeOperationAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		long beforeTime = System.currentTimeMillis();
		Object result = null;
		try {
			result = proceedingJoinPoint.proceed();// Important
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
		long afterTime = System.currentTimeMillis();

		logger.info("Time taken to execute:{} ms ( Method : {} - Parameters : {} ", (afterTime - beforeTime),
				proceedingJoinPoint.getSignature().toShortString(), proceedingJoinPoint.getArgs());

		return result;

	}

	@Pointcut("@annotation(com.cybertek.annotation.ExecutionTime)")
	private void anyExecutionTimeOperation() {
	}

}
