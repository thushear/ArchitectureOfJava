package com.github.thushear.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * 说明:
 * User: kongming
 * Date: 14-6-5
 * Time: 下午2:53
 */
@Aspect
public class AfterReturningAdviceTest {

    @AfterReturning(returning = "rvt",pointcut = "execution(* com.github.thushear.aop.*.*(..))")
    public void log(Object rvt){
        System.out.println("获取目标方法的返回值" + rvt);
        System.out.println("模拟日志记录功能");
    }


    @Around("execution(* com.github.thushear.aop.*.*(..))")
    public Object processTx(ProceedingJoinPoint jp)
            throws java.lang.Throwable
    {
        System.out.println("执行目标方法之前，模拟开始事务 ...");
        // 执行目标方法，并保存目标方法执行后的返回值
        Object rvt = jp.proceed(new String[]{"被改变的参数"});
        System.out.println("执行目标方法之后，模拟结束事务 ...");
        return rvt + " 新增的内容";
    }


}
