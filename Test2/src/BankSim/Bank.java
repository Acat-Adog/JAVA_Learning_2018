package BankSim;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import about_IO.Person;


public class Bank {
	public static Lock windowslock = new ReentrantLock();
	public static  ArrayList<Customer> Ctxt = new ArrayList<Customer>();
	public static int coccurTime=0;
	public static int nowTiming=0;
	public static int CustomerNum=0;
	public static int Sum=0;
	public static int rate1=0;public static int rate2=0;public static int rate3=0;
	public static int rate4=0;public static int rate5=0;public static int rate6=0;
	public static int rate7=0;public static int rate8=0;
	
	
	public static PriorityQueue<Integer> woccurTime = new PriorityQueue<Integer>(11,
			new Comparator<Integer>() {
		@Override
		public int compare(Integer o1, Integer o2) {
			if(o2-o1>0)  return -1;
			else if(o2-o1<0)  return 1;
			else return 0;
		}
	});
	
	
	
	
	public static class indoor implements Runnable{
		public void run(){
			new Customer().CustomerGenerator();
		}
	}
	
	public static class outdoor1 implements Runnable{
		public void run(){
			new WindowA().CustomerLeave();
		}
	}
	public static class outdoor2 implements Runnable{
		public void run(){
			new WindowB1().CustomerLeave();
		}
	}
	public static class outdoor3 implements Runnable{
		public void run(){
			new WindowB2().CustomerLeave();
		}
	}
	public static class outdoor4 implements Runnable{
		public void run(){
			new WindowV().CustomerLeave();
		}
	}
	
	public static void main(String[] args) throws FileNotFoundException, IOException {
		ExecutorService executor = Executors.newFixedThreadPool(5);
		executor.execute(new indoor());

		executor.execute(new outdoor1());
		executor.execute(new outdoor2());
		executor.execute(new outdoor3());
		executor.execute(new outdoor4());
		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
			System.out.println("——————————————————————————————————————————————————");
			System.out.println("银行平均处理时间： " + Bank.Sum/Bank.CustomerNum);
			System.out.println("1业务个数： " + Bank.rate1);
			System.out.println("2业务个数： " + Bank.rate2);
			System.out.println("3业务个数： " + Bank.rate3);
			System.out.println("4业务个数： " + Bank.rate4);
			System.out.println("5业务个数： " + Bank.rate5);
			System.out.println("6业务个数： " + Bank.rate6);
			System.out.println("7业务个数： " + Bank.rate7);
			System.out.println("8业务个数： " + Bank.rate8);
			System.out.println("一天一共的业务： " + Bank.CustomerNum);
			System.out.println("——————————————————————————————————————————————————");
			System.out.println("客户列表:  ");
			ObjectOutputStream oos = new  ObjectOutputStream(new FileOutputStream("E:/作业/code/Niko.txt"));
			for(Customer c:Ctxt) {
				oos.writeObject(c);
			}
			oos.close();
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("E:/作业/code/Niko.txt"));
			Object obj = null;
			try {
			while((obj=ois.readObject())!=null) {
				Customer c = (Customer) obj;
				System.out.println(c.toString()); 
			}
			}catch(ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (java.io.EOFException e) {
				// TODO Auto-generated catch block
				  System.out.println("读到了文件末尾"); 
			}
			ois.close();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
