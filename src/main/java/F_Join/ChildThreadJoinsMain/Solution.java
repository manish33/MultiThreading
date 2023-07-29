package F_Join.ChildThreadJoinsMain;

class SmartThread implements Runnable{
static Thread mt;
    @Override
    public void run() {

        for(int i=0;i<100;i++){
            System.out.println(i);
            try {
                mt.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Solution {


    public static void main(String[] args) throws InterruptedException {

        SmartThread smt = new SmartThread();
        smt.mt= Thread.currentThread();
        Thread t1 = new Thread(smt);
        t1.start();

        for(int i=0;i<100;i++){
            System.out.println("main thread");
            Thread.sleep(100);
        }



    }
}
