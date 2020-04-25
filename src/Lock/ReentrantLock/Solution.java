package Lock.ReentrantLock;

class Lock{
    private boolean isLocked = false;
    private Thread lockedBy;
    private int lockcount=0;
    public synchronized void lock()  throws InterruptedException{
    Thread currentThread = Thread.currentThread();
     if(isLocked && lockedBy!=currentThread){
         wait();
     }
     isLocked=true;
     lockcount++;
     lockedBy=currentThread;

    }
    public synchronized void unlock() throws InterruptedException {
        if(Thread.currentThread()==lockedBy){
            lockcount--;

            if(lockcount==0){
                isLocked=false;
                notify();
            }
        }

    }
}

class CounterBoy implements Runnable{
    int count = 0 ;
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
