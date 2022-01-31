package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect // aspectJ가 제공하는 애노테이션, 차용해서 쓰는거지 framework를 쓰는게 아니다.
public class AspectV2 {

    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){} // pointcut signature


    @Around("allOrder()") // 포인트컷 재사용
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
        /**
         * doLog 메서드가 advice 이다.
         */
        log.info("[log] {} ", joinPoint.getSignature()); //joinPoint 시그니처
        return joinPoint.proceed();
    }
}
