package K_Lock.ReadWriteLock;

import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class CounterBoy {
    int count = 0 ;
    ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    ReentrantReadWriteLock.ReadLock readlock = lock.readLock();
    ReentrantReadWriteLock.WriteLock writelock = lock.writeLock();



    public void readCount()  {
        readlock.lock();
        System.out.println("read count"+count);
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        readlock.unlock();
    }
    public void updateCount()  {
         writelock.lock();
         count++;
        System.out.println("updated count"+count);
         writelock.unlock();

    }
}

public class Solution {

    public static void main(String[] args) {
        CounterBoy c1 = new CounterBoy();
        Executors.newSingleThreadScheduledExecutor();
        Thread t1 = new Thread(()->c1.updateCount());
        Thread t2 = new Thread(()->c1.readCount());
        Thread t3 = new Thread(()->c1.updateCount());
        Thread t4 = new Thread(()->c1.readCount());
        t1.start();
        t2.start();
        t3.start();
        t4.start();

    }

}
