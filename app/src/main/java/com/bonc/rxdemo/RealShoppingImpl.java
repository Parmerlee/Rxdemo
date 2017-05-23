package com.bonc.rxdemo;

public  class RealShoppingImpl implements Shopping {

        @Override
        public Object[] doShopping(long money) {
            System.out.println(String.format("cost%s RMB", money));
            return new Object[]{"AAAA", "BBBB", "CCCCC"};
        }
    }