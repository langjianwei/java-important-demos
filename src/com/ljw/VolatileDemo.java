package com.ljw;

import java.util.concurrent.TimeUnit;

public class VolatileDemo {

    public static void main(String[] args) {

        volatileVisibilityMethod();


    }

    // 验证可见性
    private static void volatileVisibilityMethod() {
        DataVisibility data = new DataVisibility();
        new Thread(() ->{
            System.out.println(Thread.currentThread().getName() + "读取初始值：" + data.number);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            data.change();
            System.out.println(Thread.currentThread().getName() + "改变后的值：" + data.number);
        }, "DataVisibility").start();

        while (0 == data.number) {
            // 1.number不加volatile修饰，会一直死循环阻塞在这里，永远也不会打印出下面日志
            // 2.number加上volatile修饰，其保证可见性，在其他线程改变number的值后main线程会立马知道，打印下面日志

        }
        System.out.println(Thread.currentThread().getName() + "读取number的值为：" + data.number);
    }

    // 可见性
    static class DataVisibility {
        int number = 0;
//        volatile int number = 0;

        public void change() {
            this.number = 100;
        }

    }

    // 非原子性
    static class DataNoAtomicity {
        volatile int number = 0;
    }

}
