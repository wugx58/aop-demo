package com.aop.demo.myaop;

import com.aop.demo.myaop.interceptor.AfterInterceptor;
import com.aop.demo.myaop.interceptor.BeforeInterceptor;
import com.aop.demo.myaop.interceptor.ExceptionInterceptor;
import com.aop.demo.myaop.service.GreetingService;
import com.aop.demo.myaop.service.HelloService;

import java.lang.reflect.Proxy;

/**
 * @description
 * @auhtor wuguangxi
 * @date 2021/12/26 19:57
 **/
public class AopDemo {

    private static final String NAME = "wugx";

    private static final BeforeInterceptor beforeInterceptor;
    private static final AfterInterceptor afterInterceptor;
    private static final ExceptionInterceptor exceptionInterceptor;

    public static void main(String[] args) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Object proxy = Proxy.newProxyInstance(classLoader, new Class[]{GreetingService.class}, (proxy1, method, args1) -> {
            if (!GreetingService.class.isAssignableFrom(method.getDeclaringClass())) {
                return null;
            }

            try {
                beforeInterceptor.doBeforeIntercept(proxy1, method, args1);

                GreetingService greetingService = new HelloService();
                greetingService.greeting((String) args1[0]);

                afterInterceptor.doAfterIntercept(proxy1, method, args1, null);
            }catch (Exception e){
                exceptionInterceptor.doExceptionIntercept(proxy1, method, args1, e);
            }
            return null;
        });

        GreetingService greetingService = (GreetingService)proxy;
        greetingService.greeting(NAME);
    }

    /**
     * 初始化拦截器
     */
    static {
        beforeInterceptor = (target, method, args) -> {
            System.out.println("beforeIntercept");
            return null;
        };
        afterInterceptor = (target, method, args, result) -> {
            System.out.println("afterIntercept");
            return null;
        };
        exceptionInterceptor = (target, method, args, throwable) -> {
            System.out.println("exceptionIntercept");
            return null;
        };
    }
}
