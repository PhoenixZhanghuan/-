package org.devio.hi.library.coroutine;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class SemaphoreDemo {
    public static void main() {
        Semaphore semaphore = new Semaphore(3, true);
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    String name = Thread.currentThread().getName();
                    semaphore.acquire(2);
                    System.out.println(name + "获取了许可证，进去游玩了");
                    Thread.sleep(new Random().nextInt(5000));
                    semaphore.release(2);
                    System.out.println(name + "归还了许可证");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}
