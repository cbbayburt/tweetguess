package com.dedeler.tweetguess.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Aykut on 20.02.2016.
 */
@Aspect
@Component
public class LoggingAspect {

    private static final Logger ASPECT_LOGGER = LoggerFactory.getLogger("ASPECT_LOGGER");

    @Pointcut("within(@com.dedeler.tweetguess.aspect.annotation.AspectLogged *)")
    public void loggedAnnotatedClasses() {}

    @Around(value = "loggedAnnotatedClasses()")
    public Object loggedClasses(ProceedingJoinPoint joinPoint) throws Throwable {
        return around(joinPoint);
    }

    private Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        before(joinPoint);
        long startTime = System.nanoTime();
        Object returnValue = joinPoint.proceed();
        final long endTime = System.nanoTime();
        final long duration = (endTime - startTime) / 1000000;
        after(joinPoint, returnValue, duration);

        return returnValue;
    }

    private void before(JoinPoint joinPoint) {
        final String fullyQualifiedMethodName = joinPoint.getSignature().getDeclaringType().getSimpleName() + "#" + joinPoint.getSignature().getName();

        if(joinPoint.getArgs() == null || joinPoint.getArgs().length == 0) {
            ASPECT_LOGGER.debug("Entering {}", fullyQualifiedMethodName);
        } else {
            ASPECT_LOGGER.debug("Entering {} | Arguments: [{}]", fullyQualifiedMethodName, extractArguments(joinPoint.getArgs()));
        }
    }

    private void after(JoinPoint joinPoint, Object returnValue, long duration) {
        String returnValueString = returnValue != null ? returnValue.toString() : "";
        final String fullyQualifiedMethodName = joinPoint.getSignature().getDeclaringType().getSimpleName() + "#" + joinPoint.getSignature().getName();

        if(joinPoint.getSignature() instanceof MethodSignature) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Class<?> returnType = signature.getReturnType();
            if(returnType.getName().compareTo("void") == 0) {
                returnValueString = "void";
            }
        }
        ASPECT_LOGGER.debug("Returning {} | Execution time: {}ms | Return value: {}", fullyQualifiedMethodName, duration, returnValueString);
    }

    private String extractArguments(Object[] args) {
        if(args == null || args.length == 0) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        for(Object object : args) {
            if (object instanceof byte[]) {
                builder.append(" " + object.getClass().getCanonicalName() + ", ");
            }
            else {
                builder.append(" " + object.getClass().getCanonicalName() + ":" + object.toString() + ", ");
            }
        }

        return builder.toString();
    }
}