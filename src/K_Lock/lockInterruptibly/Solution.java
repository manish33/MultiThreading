package K_Lock.lockInterruptibly;

import java.util.concurrent.locks.ReentrantLock;

public class Solution {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Thread thread = new Thread(() -> {
            int i = 0;
            System.out.println("before entering ReentrankLock block");
            try {
                lock.lockInterruptibly();
                while (0 < 1) {
                    System.out.println("in the ReentrankLock block counting: " + i++);
                }
            } catch (InterruptedException e) {
                System.out.println("ReentrankLock block interrupted");
            }
        });
        lock.lock(); // lock first to make the lock in the thread "waiting" and then interruptible
        thread.start();
        thread.interrupt();


    }
}
