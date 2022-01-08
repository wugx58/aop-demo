package com.aop.demo.myaop.interceptor;

import java.lang.reflect.Method;

/**
 * @description 异常拦截器
 * @auhtor wuguangxi
 * @date 2021/12/26 20:13
 **/
public interface ExceptionInterceptor {

    /**
     * 异常拦截
     * @param target
     * @param method
     * @param args
     * @param throwable
     * @return
     */
    Object doExceptionIntercept(Object target, Method method, Object[] args, Throwable throwable);
}
