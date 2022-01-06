package org.devio.hi.library.coroutine;

import java.util.concurrent.CountDownLatch;

/**
 * 演示一个多人过山车的场景
 * <p>
 * 我们假设有5人去乘坐过山车，等待5人全部准备好，才能发车
 */
public class CountDownLatchDemo {
    public static void main() throws InterruptedException {
        CountDownLatch downLatch = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "准备好了");
                downLatch.countDown();
            }).start();
        }

        downLatch.await();
        System.out.println("所有人都准备好了, 准备发车...");
    }
}
