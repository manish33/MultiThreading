package Synchronized.SynchronizedBlockClassLevel;

class Display {
    public void display(String name) {
        for (int i = 0; i < 10; i++) {
            System.out.print("good morning!");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name);
        }

    }

    public  static void display1(String name) {

        System.out.println("1000 line of code which doesn't require sync");

        synchronized (Display.class){
            for (int i = 0; i < 10; i++) {
                System.out.print("good morning!");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(name);
            }
        }


        System.out.println("1000 line of code which doesn't require sync");
    }
}

class SmartThread implements Runnable {
    String name;
    Display d;
    SmartThread(String name, Display d){
        this.name=name;
        this.d=d;
    }
    @Override
    public void run() {
        d.display1(this.name);


    }
}

public class Solution {
    public static void main(String[] args) {
        Display d1= new Display();
        Display d2= new Display();
        Thread t1 = new Thread(new SmartThread("dhoni",d1));
        Thread t2 = new Thread(new SmartThread("yuvraj",d2));
        t1.start();
        t2.start();
    }




}
