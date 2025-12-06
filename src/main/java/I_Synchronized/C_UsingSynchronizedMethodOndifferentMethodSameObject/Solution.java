package I_Synchronized.C_UsingSynchronizedMethodOndifferentMethodSameObject;

class Display {
    public synchronized void displayc() {
        for (int i = 65; i < 80; i++) {
            System.out.print((char)i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } } }
    public  synchronized void displayn() {

        for (int i = 0; i < 10; i++) {
            System.out.print(i);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } } }}

class SmartThread1 implements Runnable {
    Display d;
    SmartThread1( Display d){
        this.d=d;
    }
    @Override
    public void run() {
        d.displayn();
    }}
class SmartThread2 implements Runnable {
    Display d;
    SmartThread2(Display d){
        this.d=d;
    }
    @Override
    public void run() {
        d.displayc();
    }
}
public class Solution {
    public static void main(String[] args) {
        Display d= new Display();
        Thread t1 = new Thread(new SmartThread1(d));
        Thread t2 = new Thread(new SmartThread2(d));
        t1.start();
        t2.start();
    }}

//One thread will acquire the lock first (e.g., t1).
//
//It will print its complete sequence of numbers: 0123456789.
//
//It will release the lock.
//
//The other thread (t2) will acquire the lock.
//
//It will print its complete sequence of characters: ABCDEFGHIJKLMNO.
