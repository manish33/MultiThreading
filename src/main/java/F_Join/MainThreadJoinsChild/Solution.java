package F_Join.MainThreadJoinsChild;

class SmartThread implements Runnable{

    @Override
    public void run() {
        for(int i=0;i<100;i++){
            System.out.println(i);
        }
    }
}

public class Solution {


    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(new SmartThread());
        t1.start();

        t1.join();
        for(int i=0;i<100;i++){
            System.out.println("main thread");
        }



    }
}

