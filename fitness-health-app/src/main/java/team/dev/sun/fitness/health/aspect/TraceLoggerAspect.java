package team.dev.sun.fitness.health.aspect;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
public class TraceLoggerAspect {

  @Pointcut("execution(public * (@org.springframework.stereotype.Service team.dev.sun.fitness.health.service..*).*(..))")
  public void serviceMethods() {

  }

  @Around("serviceMethods()")
  public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

    StopWatch stopWatch = new StopWatch();
    stopWatch.start();

    Logger log = getLogger(joinPoint.getTarget());
    String methodName = joinPoint.getSignature().getName();
    log.debug("Entering {}", methodName);
    boolean exitThroughException = false;

    try {
      return joinPoint.proceed();
    } catch (Throwable ex) {
      exitThroughException = true;
      String exceptionSimpleName = ex.getClass().getSimpleName();
      log.debug("Exception {} thrown in method {}, took {} ms.", exceptionSimpleName, methodName, getExecutionTime(stopWatch));
      log.error("{} details:", exceptionSimpleName, ex);
      throw ex;
    } finally {
      if (!exitThroughException) {
        log.debug("Leaving {}, took {} ms.", methodName, getExecutionTime(stopWatch));
      }
    }
  }

  private Logger getLogger(Object targetObject) {

    Class<?> clazz = AopUtils.getTargetClass(targetObject);

    return LogManager.getLogger(clazz);
  }

  private long getExecutionTime(StopWatch stopWatch) {

    if (stopWatch.isRunning()) {
      stopWatch.stop();
    }
    return stopWatch.getTotalTimeMillis();
  }
}