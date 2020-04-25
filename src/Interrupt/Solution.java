package Interrupt;

class SmartThread implements Runnable{

    @Override
    public void run() {

        for(int i=0;i<1000;i++){
            System.out.println(i);
        }
        for (int i=0;i<10000;i++){
            System.out.println(i+" sleep");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Solution {


    public static void main(String[] args) throws InterruptedException {

        SmartThread smt = new SmartThread();
        Thread t1 = new Thread(smt);
        t1.start();
        t1.interrupt();

        for(int i=0;i<100;i++){
            System.out.println("main thread");
            Thread.sleep(100);
        }



    }
}
