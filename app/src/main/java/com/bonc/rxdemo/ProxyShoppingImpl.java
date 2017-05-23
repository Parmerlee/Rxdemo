package com.bonc.rxdemo;

public class ProxyShoppingImpl implements Shopping {

    private RealShoppingImpl impl;

    public ProxyShoppingImpl(RealShoppingImpl impl) {
        this.impl = impl;
    }


    @Override
    public Object[] doShopping(long money) {
        System.out.println(String.format("花了%s块钱", money));
        Object[] things = impl.doShopping(money / 2);
        return things;
    }
}