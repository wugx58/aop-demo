package com.aop.demo.service;

/**
 * @description
 * @auhtor wuguangxi
 * @date 2021/12/26 19:58
 **/
public class HelloService implements GreetingService {
    @Override
    public void greeting(String name) {
        System.out.printf("%s : hello \n", name);
    }
}
