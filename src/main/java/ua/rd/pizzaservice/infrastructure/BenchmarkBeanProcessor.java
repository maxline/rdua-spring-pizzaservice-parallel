package ua.rd.pizzaservice.infrastructure;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
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

                if (isBenchMarkAnnotationPresentAndTrue(beanMethod)) {
                    return wrapMethodInBenchmark(targetBean, beanMethod, args);
                }
                return beanMethod.invoke(targetBean, args);
            }
        });

        return returnedBean;
    }

    private boolean isBenchMarkAnnotationPresentAndTrue(Method beanMethod) {
        return beanMethod.isAnnotationPresent(Benchmark.class) && beanMethod.getAnnotation(Benchmark.class).value();
    }

    private <T> Object wrapMethodInBenchmark(T original, Method beanMethod, Object[] args)
            throws InvocationTargetException, IllegalAccessException {
        long start = System.nanoTime();
        Object invoke = beanMethod.invoke(original, args);
        long end = System.nanoTime();
        System.out.println(String.format("Method '%s', execution time %d", beanMethod.getName(), end - start));
        return invoke;
    }

    private Class<?>[] getAllDeclaredInterfaces(Object bean) {
        List<Class<?>> interfaces = new ArrayList<>();
        Class<?> currentClass = bean.getClass();
        while (currentClass != Object.class) {
            for (Class<?> intrface : currentClass.getInterfaces()) {
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