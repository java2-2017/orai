package hu.mik.java2.book.log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class LoggingAspect {

	@Around("execution (* hu.mik.java2.service.*.*(..))")
	public Object logAround(ProceedingJoinPoint joinPoint) {

		System.out.println("logging aspect is running");
		System.out.println("hijacked: " + joinPoint.getSignature().getName());
		System.out.println("hijacked arguments: " + joinPoint.getArgs());

		
		Object returnValue = null;
		try {
			returnValue = joinPoint.proceed(joinPoint.getArgs());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return returnValue;
	}

}
