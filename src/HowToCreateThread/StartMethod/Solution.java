package HowToCreateThread.StartMethod;



class SmartThread extends Thread {



    public void start(){
        System.out.println("tricky");
        super.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(i+Thread.currentThread().getName());
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
        SmartThread st1 = new SmartThread();
        st1.start();
    }
}

