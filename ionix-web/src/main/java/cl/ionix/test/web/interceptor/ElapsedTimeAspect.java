package cl.ionix.test.web.interceptor;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import cl.ionix.test.web.model.response.DataWebResponse;

@Component
@Aspect
@Configuration
public class ElapsedTimeAspect  {
	
	@Around("within(cl.ionix.test.web.controller..*))")
	public Object calculateElapsedTime(ProceedingJoinPoint joinPoint) throws Throwable {
		
		long start = System.currentTimeMillis();
		Object response = joinPoint.proceed();
		long end = System.currentTimeMillis();
		if(response instanceof ResponseEntity) {
			Object body = ((ResponseEntity<?>) response).getBody();
			if(body instanceof DataWebResponse) {
				((DataWebResponse) body).setElapsedTime(end - start);
			}
		}
		return response;
	}
}
