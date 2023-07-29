package K_Lock.C_ReentrantLockComesWithJava;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class CounterBoy implements Runnable{
    int count = 0 ;
    Lock lock = new ReentrantLock(true);

    @Override
    public void run() {
        try {
            updateCount();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void updateCount() throws InterruptedException {
        lock.lock();
        count++;
        System.out.println(count);
        lock.unlock();
    }
}
public class Solution {
    public static void main(String[] args) {
        CounterBoy c1  =new CounterBoy();
        Thread t1= new Thread(c1);
        Thread t2= new Thread(c1);
        Thread t3= new Thread(c1);
       t1.start();
       t2.start();
       t3.start();
    }
}
