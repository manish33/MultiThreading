package HowToCreateThread.UsingThread;

class SmartThread extends Thread{

   public void run(){
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

class SmartThread2 extends Thread{

    public void run(){
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
        SmartThread st1 = new SmartThread();
        st1.start();
        SmartThread2 st2 = new SmartThread2();
        st2.start();

    }

}
