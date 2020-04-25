package ThreadName;

class SmartThread implements Runnable {
    String name;
    Thread t1;

    SmartThread(String name) {
        this.name = name;
        t1 = new Thread(this,"man");
        t1.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            System.out.println(i+t1.getName());
            Thread.currentThread().setName("new name");
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
        SmartThread st1 = new SmartThread("man");

    }
}
