package com.aop.demo.myaop.interceptor;

import java.lang.reflect.Method;

/**
 * @description 后置拦截器
 * @auhtor wuguangxi
 * @date 2021/12/26 19:54
 **/
public interface AfterInterceptor {

    /**
     * 后置拦截
     * @param target
     * @param method
     * @param args
     * @param result
     * @return
     */
    Object doAfterIntercept(Object target, Method method, Object[] args, Object result);
}
