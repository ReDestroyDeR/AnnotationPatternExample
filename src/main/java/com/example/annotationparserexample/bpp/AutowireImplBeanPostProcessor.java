package com.example.annotationparserexample.bpp;

import com.example.annotationparserexample.annotation.AutowireImpl;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.Arrays;

@Component
public class AutowireImplBeanPostProcessor implements BeanPostProcessor, BeanFactoryAware {
    private BeanFactory beanFactory;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        var fields = ClassUtils.getUserClass(bean).getSuperclass().getDeclaredFields();
        Arrays.stream(fields)
                .filter(field -> field.getAnnotation(AutowireImpl.class) != null)
                .forEach(field -> {
                    var impl = beanFactory.getBean(beanName);

                    if (!field.canAccess(this)) {
                        field.setAccessible(true); // NOSONAR Framework
                        try {
                            field.set(bean, impl); // NOSONAR Framework
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        } finally {
                            field.setAccessible(false);
                        }
                    }

                    try {
                        field.set(bean, impl); // NOSONAR Framework
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });

        return bean;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        if (this.beanFactory == null)
            synchronized (this) {
                this.beanFactory = beanFactory;
            }
    }
}
