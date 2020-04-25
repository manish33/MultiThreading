package Synchronized.ProducerConsumer;


import java.util.Random;
import java.util.Stack;

class Producer implements Runnable{

    Stack<Integer> st;
    int size;
    Random rand = new Random();
    Producer( Stack<Integer> st,int size){
        this.st=st;
        this.size=size;
    }

    @Override
    public void run() {

        while (true) {


            synchronized (st) {
                while (st.size() >= size) {


                    try {
                        st.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }


                }


                int n = rand.nextInt(10);
                System.out.println("value produced" + n);
                st.push(n);
                if (st.size() >= size)
                    st.notifyAll();

            }
        }


    }
}

class Consumer implements Runnable{


    Stack<Integer> st;
    Consumer(Stack<Integer> st){
        this.st=st;
    }
    @Override
    public void run() {

        while (true) {


            synchronized (st) {

                while (st.isEmpty()) {
                    try {
                        st.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                while (!st.isEmpty()) {
                    System.out.println("poped : " + st.pop());
                }


                if (st.isEmpty())
                    st.notify();


            }
        }




    }

}

public class Solution {

    public static void main(String[] args) throws InterruptedException {
        Stack<Integer> A= new Stack<Integer>();
        int size=10;


        Thread producer1 = new Thread(new Producer(A,size));
        Thread producer2 = new Thread(new Producer(A,size));
        Thread consumer1 = new Thread(new Consumer(A));
        producer1.start();
        producer2.start();
        consumer1.start();


    }







}
