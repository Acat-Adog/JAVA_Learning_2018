package aboutThread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadCooperation {
	private static Account account = new Account();
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.execute(new DeposiTask());
		executor.execute(new WithdrawTask());
		executor.shutdown();
	}
	public static class DeposiTask implements Runnable {
		public void run() {
			try {
				while(true) {
					account.deposit((int)(Math.random()*10)+1);
					Thread.sleep(1000);
				}
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	public static class WithdrawTask implements Runnable {
		public void run() {
				while(true) {
					account.withdraw((int)(Math.random()*10)+1);
				}
		}
	}
	
	private static class Account{
		private static Lock lock = new ReentrantLock(); 
		private static Condition newDeposit = lock.newCondition();
		private int balance = 0;
		public int getBalance(){
			return balance;
		}
		public void withdraw(int amount) {
			lock.lock();
			try {
				while(balance<amount) {
					System.out.println("wait fot a deposit");
					newDeposit.await();
				}
				balance -= amount;
				System.out.println("Withdraw: " + amount + " Account'balance: " + getBalance());
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			//finally {
				lock.unlock();
			//}
		}
		
		public void deposit(int amount)  {
			lock.lock();
			try {
				balance += amount;
				System.out.println("deposit: " + amount + " Account'balance: " + getBalance());
				newDeposit.signalAll();
				
			}finally{
				lock.unlock();
			}
		}
		
	}
}
