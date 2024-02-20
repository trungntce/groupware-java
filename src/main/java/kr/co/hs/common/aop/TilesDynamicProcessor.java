package kr.co.hs.common.aop;

import javax.servlet.http.HttpServletRequest;

import kr.co.hs.common.tiles.TilesDynamic;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class TilesDynamicProcessor {

	@Around("@annotation(kr.co.hs.common.tiles.TilesDynamic)")
	public Object before(ProceedingJoinPoint joinPoint) throws Throwable {


		MethodSignature method= (MethodSignature)joinPoint.getSignature();
		TilesDynamic annotation = method.getMethod().getAnnotation(TilesDynamic.class);
		if( annotation != null ) {
			getCurrentRequest().setAttribute("__template", annotation.value() );
			getCurrentRequest().setAttribute("title", annotation.title() );
		}

		return joinPoint.proceed();
	}

	private HttpServletRequest getCurrentRequest() {
		ServletRequestAttributes sra = (ServletRequestAttributes)RequestContextHolder.currentRequestAttributes();
		return sra.getRequest();
	}

}
