package Yield.MainThreadGiveChance;

class SmartThread implements Runnable{

    @Override
    public void run() {
        for(int i=0;i<100;i++){
            System.out.println(i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

public class Solution {


    public static void main(String[] args) {


        Thread t1 = new Thread(new SmartThread());
        Thread t2 = new Thread(new SmartThread());
        t1.start();
        t2.start();
        for(int i=0;i<10;i++){
            Thread.yield();
            System.out.println("main"+i);
        }



    }



}
