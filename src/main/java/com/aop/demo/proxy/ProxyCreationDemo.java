package com.aop.demo.proxy;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.TargetSource;
import org.springframework.aop.aspectj.annotation.AnnotationAwareAspectJAutoProxyCreator;
import org.springframework.aop.framework.autoproxy.target.AbstractBeanFactoryBasedTargetSourceCreator;
import org.springframework.aop.target.AbstractBeanFactoryBasedTargetSource;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @description
 * @auhtor wuguangxi
 * @date 2022/1/7 18:48
 **/
@Aspect
@Configuration
@EnableAspectJAutoProxy
public class ProxyCreationDemo {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(ProxyCreationDemo.class);
        applicationContext.refresh();

        applicationContext.register(ActionService.class);
        // 需要设置下autoProxyCreator
        setAutoProxyCreator(applicationContext);
        applicationContext.register(TargetSourceActionService.class);

        // 这样处理会通过AbstractAutoProxyCreator#postProcessAfterInitialization()方法来对原有类进行替换
        ActionService service = applicationContext.getBean(ActionService.class);
        service.action();

        // 这个targetSource的对象，用类型来获取会获取不到
        TargetSource targetSource = (TargetSource) applicationContext.getBean("proxyCreationDemo.TargetSourceActionService");
        ActionService targetSourceActionService = null;
        try {
            // 这个会通过AbstractAutoProxyCreator#postProcessBeforeInstantiation
            targetSourceActionService = (ActionService) targetSource.getTarget();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        targetSourceActionService.action();
    }

    private static void setAutoProxyCreator(ConfigurableApplicationContext applicationContext){
        AnnotationAwareAspectJAutoProxyCreator autoProxyCreator = (AnnotationAwareAspectJAutoProxyCreator) applicationContext.getBean("org.springframework.aop.config.internalAutoProxyCreator");
        AbstractBeanFactoryBasedTargetSourceCreator targetSourceCreator = new AbstractBeanFactoryBasedTargetSourceCreator() {
            @Override
            protected AbstractBeanFactoryBasedTargetSource createBeanFactoryBasedTargetSource(Class<?> beanClass, String beanName) {
                return new AbstractBeanFactoryBasedTargetSource() {
                    @Override
                    public Object getTarget() throws Exception {
                        return getBeanFactory().getBean(getTargetBeanName());
                    }
                };
            }
        };
        targetSourceCreator.setBeanFactory(applicationContext.getBeanFactory());
        autoProxyCreator.setCustomTargetSourceCreators(targetSourceCreator);
    }


    @Pointcut("execution(* com.aop.demo.proxy.ProxyCreationDemo..*(..)) ")
    public void action(){

    }

    @Before("action()")
    public void before(){
        System.out.println("before");
    }

    class ActionService{
        public void action(){
            System.out.println("action");
        }
    }

    class TargetSourceActionService implements TargetSource {

        @Override
        public Class<?> getTargetClass() {
            return ActionService.class;
        }

        @Override
        public boolean isStatic() {
            return false;
        }

        @Override
        public Object getTarget() throws Exception {
            return new ActionService();
        }

        @Override
        public void releaseTarget(Object target) throws Exception {

        }
    }
}
