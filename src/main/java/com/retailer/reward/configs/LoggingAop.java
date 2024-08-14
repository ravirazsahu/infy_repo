package com.retailer.reward.configs;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAop {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Before("execution(* com.retailer.reward..*.*(..))")
	public void logbefore(JoinPoint jp) {
		log.info("Method execution started: {}", jp.getSignature());
	}

	@After("execution(* com.retailer.reward..*.*(..))")
	public void logAfter(JoinPoint joinPoint) {
		log.info("Method execution completed: {}", joinPoint.getSignature());
	}

}
