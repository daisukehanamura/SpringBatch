package com.javacodingskills.spring.batch.demo0.callable;

import java.util.concurrent.Callable;

public class MyCallable01 implements Callable<String> {

    public int i;

    public MyCallable01(int i){
        this.i = i;

    }
    @Override
    public String call() throws Exception {
        try {
            if (i == 1) {
                throw new Exception("eroer");
            }
            if(i == 0){
                Thread.sleep(3000);
            }
            System.out.println("正常終了:" +i);
            return "";
        } catch (Exception e) {
            System.out.println("異常終了" +i);
            return "";
        }
    }
}