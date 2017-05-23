package com.bonc.rxdemo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2017/5/23.
 */

public class Test {

    public static void main(String[] args) {

        //真实实现类
        RealShoppingImpl impl = new RealShoppingImpl();
        impl.doShopping(200);

        //静态代理
        ProxyShoppingImpl proxyShopping = new ProxyShoppingImpl(impl);
        proxyShopping.doShopping(200);


        //动态代理 jdk实现方式。 优点：用反射方式来实现动态地创建代理类，可以根据name来做中间过滤，可以在一个类中实现多个代理类；缺点：只能对接口进行代理。
        InvocationHandler invocationHandler = new MyInvocationHandler(impl);
       /*
         * 通过Proxy的newProxyInstance方法来创建我们的代理对象，我们来看看其三个参数
         * 第一个参数 handler.getClass().getClassLoader() ，我们这里使用handler这个类的ClassLoader对象来加载我们的代理对象
         * 第二个参数realSubject.getClass().getInterfaces()，我们这里为代理对象提供的接口是真实对象所实行的接口，表示我要代理的是该真实对象，这样我就能调用这组接口中的方法了
         * 第三个参数handler， 我们这里将这个代理对象关联到了上方的 InvocationHandler 这个对象上
         */
        RealShoppingImpl userServiceProxy = (RealShoppingImpl) Proxy.newProxyInstance(impl.getClass().getClassLoader(),
                impl.getClass().getInterfaces(), invocationHandler);
        userServiceProxy.doShopping(200);
    }


}
