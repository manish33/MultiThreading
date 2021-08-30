package ThreadLocal.WithoutThreadLocal;

class Multiplication {
    int digitNumber;

    public void setDigitNumber(int digitNumber){
        this.digitNumber=digitNumber;
    }
    public  int getDigitNumber(){
        return digitNumber;
    }
    public void printTable(){

        for(int i=1;i<11;i++){
           System.out.print(Thread.currentThread().getName()+"  ");
            System.out.println(digitNumber+" x "+i+" = "+digitNumber*i);
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

        Multiplication m1= new Multiplication();
     //   Multiplication m2= new Multiplication(); (use this to solve the problem in thread 2);
         new Thread(()->{
            m1.setDigitNumber(3);
            m1.printTable();
        }).start();
        new Thread(()->{
            m1.setDigitNumber(5);
            m1.printTable();
        }).start();
    }
}
