package com.bit2025.mysite.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class MeaureExecutionTimeAspect {
	private static final Log logger = LogFactory.getLog(MeaureExecutionTimeAspect.class);

	@Around("execution()")
	public Object adviceAround(ProceedingJoinPoint pjp) throws Throwable {
		StopWatch sw = new StopWatch();
		sw.start();

		Object result = pjp.proceed();

		sw.stop();

		long totalTime = sw.getTotalTimeMillis();
		logger.info(totalTime + "millis");

		return result;
	}
}
