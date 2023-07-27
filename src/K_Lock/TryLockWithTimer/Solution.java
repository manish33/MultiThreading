package K_Lock.TryLockWithTimer;


import java.util.concurrent.TimeUnit;
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
        do{
            if(lock.tryLock(5000, TimeUnit.MILLISECONDS)){
                count++;
                System.out.println(count);
                Thread.sleep(30000);
                lock.unlock();
                break;
            }
            else {
                System.out.println("could not get lock will try again");

            }
        }while (true);



    }
}

public class Solution {
    public static void main(String[] args) {
        CounterBoy c1  =new CounterBoy();
        Thread t1= new Thread(c1);
        Thread t2= new Thread(c1);

       t1.start();
       t2.start();

    }
}
