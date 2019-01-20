package aboutThread;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerWithNotify {
	static LinkedList<Product> Products = new LinkedList<Product>();
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newCachedThreadPool();
		executor.execute(new DeposiProduct());
		executor.execute(new WithdrawProduct("kenny"));
		executor.shutdown();
	
	}
	
	
	
	public static class DeposiProduct implements Runnable {
		public void run() {
			while(true) {
				new transaction().producePro();
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	public static class WithdrawProduct implements Runnable {
		private String name;
		WithdrawProduct(String name){
			this.name = name;
		}
		public void run() {
			while(true) {
				(new transaction()).counsumePro(name);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
	
	
	
static	class Product{
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	Product(String name){
		this.name = name;
	}
	
}

static class Customer{
	private String cname;

	public String getcname() {
		return cname;
	}

	public void setcname(String cname) {
		this.cname = cname;
	}
	Customer(String Cname){
		this.cname = cname;
	}
	
}
static class transaction{
	private static Lock lock = new ReentrantLock(); 
	private static Condition newDeposit = lock.newCondition();
	public void producePro() {
			lock.lock();
			try {
			String name = String.valueOf((int)(Math.random()*100));
			Product p =new Product(name);
			System.out.println("Product ID is: "+p.name+"  ");
			ProducerWithNotify.Products.addLast(p);
			newDeposit.signalAll();
			}finally {
				lock.unlock();
			}
	}
	public void counsumePro(String name) {
		lock.lock();
		Customer c = new Customer("name");
		try {
				while(ProducerWithNotify.Products.isEmpty()) {
					try {
						newDeposit.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			       
			 }
				System.out.println(c.getcname()+"Get product ID is: "+ProducerWithNotify.Products.getFirst().getName());
		        ProducerWithNotify.Products.removeFirst(); 
		}finally {
			lock.unlock();
		}
	}
	
  }

}



