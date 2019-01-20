package aboutThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskThreadDemo {
	public static void main(String[] args){
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(new PrintChar('a', 100));
		executor.execute(new PrintChar('b', 100));
		executor.execute(new PrintNum(100));
		executor.shutdown();
		
		
//		Runnable printA = new PrintChar('a', 100);
//		Runnable printB = new PrintChar('b', 100);
//		Runnable print100 = new PrintNum(100);
//		Thread thread1 = new Thread(printA);
//		Thread thread2 = new Thread(printB);
//		Thread thread3 = new Thread(print100);
//		thread1.start();
//		thread2.start();
//		thread3.start();
	}
}
	
	class PrintChar implements Runnable{
		private char charToPrint;
		private int times;
		public PrintChar(char c, int i){
			charToPrint =c;
			times = i;
		}
		public void run(){
			try {
				//Thread.sleep(10);
			for(int i=0; i<times ; i++){
				Thread.sleep(1);
				System.out.print(charToPrint);
		}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	class PrintNum implements Runnable{
		private int lastNum;
		public PrintNum(int n) {
			lastNum = n;
		}
		public void run() {
//			Thread thread4 = new Thread(new PrintChar('c',100));
//			thread4.start();
			try {
			for(int i=1;i<=lastNum;i++) {
			    Thread.sleep(1);
				System.out.print(" "+i);
//				if(i==50) thread4.join();
			}
		}catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
