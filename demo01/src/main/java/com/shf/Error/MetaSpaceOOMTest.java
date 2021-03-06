package com.shf.Error;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * -XX:MetaspaceSize=8m -XX:MaxMetaspaceSize=8m
 * Java 8及之后的版本使用Metaspace来替代永久代。
 * Metaspace是方法区在HotSpot中的实现，它与持久代最大的区别在于:
 * Metaspace并不在虚拟机内存中而是使用本地内存
 * 也即在java8中,c.asse metadata(the virtual machines internal presentation of Java class),被存储在叫做
 * Metaspace 的native memory
 * *
 * 永久代(java8后被原空间Metaspace取代了）存放了以下信息:
 * 虚拟机加载的类信息*常量池
 * 静态变量
 * 即时编译后的代码
 * 模拟metaspace空间溢出，我们不断生成类往元空间灌，类占据的空间总是会超过metaspace指定的空间大小的
 */
public class MetaSpaceOOMTest {

    static class OOMTest {

    }

    public static void main(String[] args) {
        int i = 0;

        try {
            while (true){
                i++;
                Enhancer enhancer = new Enhancer();
                enhancer.setSuperclass(OOMTest.class);
                enhancer.setUseCache(false);
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                        return methodProxy.invokeSuper(o,args);
                    }
                });
                enhancer.create();
            }
        } catch (Exception e) {
            System.out.println("*********** 多少次后发生了异常："+i);
            throw new RuntimeException(e);
        }
    }
}
