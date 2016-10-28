package ua.rd.pizzaservice.infrastructure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class BenchmarkBeanProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (hasAnnotation(bean.getClass(), Benchmark.class)) {
            System.out.println("has @Benchmark annotation: " + bean);
            bean = applyBenchmarkProxy(bean);
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private Object applyBenchmarkProxy(Object bean) {
        ClassLoader classLoader = this.getClass().getClassLoader();
        Class<?>[] interfaces = getAllDeclaredInterfaces(bean);
        Object returnedBean = Proxy.newProxyInstance(classLoader, interfaces, new InvocationHandler() {
            Object targetBean = bean;

            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Method beanMethod = targetBean.getClass().getMethod(method.getName(), method.getParameterTypes());

                Object o;
                Benchmark benchmark = beanMethod.getAnnotation(Benchmark.class);
                if ((benchmark != null) && (benchmark.value())) {
                    long start = System.nanoTime();
                    o = method.invoke(targetBean, args);
                    long end = System.nanoTime();
                    System.out.printf("working time of %s method with arguments: %d nanoseconds%n", method.getName(), (end - start));
                } else {
                    o = method.invoke(targetBean, args);
                }
                return o;
            }
        });

        return returnedBean;
    }

    private Class<?>[] getAllDeclaredInterfaces(Object bean) {
        List<Class<?>> interfaces = new ArrayList<>();
        Class<?> currentClass = bean.getClass();
        while(currentClass != Object.class) {
            for(Class<?> intrface: currentClass.getInterfaces()) {
                interfaces.add(intrface);
            }
            currentClass = currentClass.getSuperclass();
        }
        Class<?>[] arrayOfInterfaces = new Class[interfaces.size()];
        return interfaces.toArray(arrayOfInterfaces);
    }

    private boolean hasAnnotation(Class<?> beanClass, Class<?> annotation) {
        for (Method method : beanClass.getMethods()) {
            if (method.getAnnotation(Benchmark.class) != null) {
                return true;
            }
        }

        return false;
    }
}