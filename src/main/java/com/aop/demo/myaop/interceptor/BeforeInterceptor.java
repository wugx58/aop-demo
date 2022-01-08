package com.aop.demo.myaop.interceptor;

import java.lang.reflect.Method;

/**
 * @description 前置拦截器
 * @auhtor wuguangxi
 * @date 2021/12/26 19:54
 **/
public interface BeforeInterceptor {

    /**
     * 前置拦截
     * @param target
     * @param method
     * @param args
     * @return
     */
    Object doBeforeIntercept(Object target, Method method, Object[] args);
}
