package com.dgit.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class SampleAdvice {
	private static final Logger logger = LoggerFactory.getLogger(SampleAdvice.class);
	
	@Before("execution(* com.dgit.service.MessageServiceImpl.addMessage(..))")
	public void startLog(JoinPoint jp){
		logger.info("=============================================");
		logger.info("[startLog]");
		logger.info("=============================================");
	}
	
	@Around("execution(* com.dgit.service.MessageServiceImpl.readMessage(..))")
	public Object timeLog(ProceedingJoinPoint pjp) throws Throwable{
		long startTime = System.currentTimeMillis();
		logger.info("=============================================");
		logger.info("[timeLog] START");
		logger.info("=============================================");
		
		Object result = pjp.proceed();
		
		long endTime = System.currentTimeMillis();
		logger.info("=============================================");
		logger.info("[timeLog] END , time :" + (endTime - startTime));
		logger.info("=============================================");
		
		return result;
	}
}
