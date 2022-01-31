package hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect // aspectJ가 제공하는 애노테이션, 차용해서 쓰는거지 framework를 쓰는게 아니다.
public class AspectV3 {

    @Pointcut("execution(* hello.aop.order..*(..))")
    private void allOrder(){} // pointcut signature

    // 클래스 이름 패턴이 *Service
    @Pointcut("execution(* *..*Service.*(..))")
    private void allService(){}


    @Around("allOrder()") // 포인트컷 재사용
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
        /**
         * doLog 메서드가 advice 이다.
         */
        log.info("[log] {} ", joinPoint.getSignature()); //joinPoint 시그니처
        return joinPoint.proceed();
    }
    
    
    // hello.aop.order 패키지와 하위 패키지 이면서 클래스 이름 패턴이 *Service 인것
    @Around("allOrder() && allService()")
    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{
        try{
            log.info("[트랜잭션 시작] {}", joinPoint.getSignature());
            Object result = joinPoint.proceed();
            log.info("[트랜잭션 커밋] {}", joinPoint.getSignature());
            return result;
        } catch (Exception e){
            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
            throw e;
        } finally {
            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
        }
    }
}
