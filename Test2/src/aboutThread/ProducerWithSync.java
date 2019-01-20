package aboutThread;


import java.util.ArrayList;
import java.util.Random;


public class ProducerWithSync {
	public static int index=0;
	public static void main(String[] args) {

		Product1 r1 = new Product1("1");
		Runnable c1 = new Cusstomer1("1");
		Runnable c2 = new Cusstomer1("2");
		
		Thread t1 = new Thread(r1);
		Thread t2 = new Thread(c1);
		Thread t3 = new Thread(c2);
		
		t1.start();
		t2.start();
		t3.start();
		
	}
}

class Product1 implements Runnable {
	public static ArrayList<Product1> Products = Products = new ArrayList<Product1>();
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Product1(String name) {
		this.name = name;
	}

	public void run() {
		Random r = new Random();
		
		for (int i = 0; i < 10; i++) {
			int name = r.nextInt(1000);
			Product1 product = new Product1(String.valueOf(name));
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Products.add(product);
			System.out.println("product'ID:"+Products.get(i).getName());
		}

	}
}

class Cusstomer1 implements Runnable{
	private String getProduct;
	private static int i=0;
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public String getGetProduct() {
		return getProduct;
	}
	public void setGetProduct(String getProduct) {
		
		this.getProduct = getProduct;
	}
	public Cusstomer1(String name){
		this.name = name;
	}
	public Cusstomer1(String name, String getProduct){
		this.name = name;
		this.getProduct = getProduct;
	}	
	public synchronized void Creat() {
		while(ProducerWithSync.index<10){
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(ProducerWithSync.index<10&&Product1.Products.get(ProducerWithSync.index).getName() != null){
			Cusstomer1 cusstomer = new Cusstomer1(this.name,Product1.Products.get(ProducerWithSync.index).getName());
			System.out.println(cusstomer.getName()+"客户获得了: "+cusstomer.getProduct);
		}
		//index++;
		ProducerWithSync.index++;
		}
		
	}
	public void run(){
		Creat();
	}
}
