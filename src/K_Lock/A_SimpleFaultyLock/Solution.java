package K_Lock.A_SimpleFaultyLock;

 class Lock{

    private boolean isLocked = false;

    public synchronized void lock()
            throws InterruptedException{
        while(isLocked){
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock(){
        isLocked = false;
        notify();
    }
}

class CounterBoy implements Runnable{
    int count1 = 0 ;
    int count2 = 0 ;
    Lock lock = new Lock();

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
        count1++;
        System.out.println(count1);
       // updateCount2(); // can not reenter the lock and deadlock
        lock.unlock();
    }

    public void updateCount2() throws InterruptedException {
        lock.lock();
        count2++;
        System.out.println(count2);
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
