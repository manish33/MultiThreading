package G_Yield.ChildThreadGivesTheChance;

class SmartThread implements Runnable{

    @Override
    public void run() {
        for(int i=0;i<100;i++){
            System.out.println(i);

                Thread.yield();

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

            System.out.println("main"+i);
        }

    }
}
