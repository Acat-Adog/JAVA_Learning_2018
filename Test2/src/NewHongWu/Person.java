package NewHongWu;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Person implements Serializable{
	private String name;

    private static final long serialVersionUID = 2L;
	static List<Poker> mypokers = new ArrayList<Poker>();
	int point;

	
	public static void main(String[] args) {
		new Thread(new Person().new playPoker()).start();
	}
	
	class playPoker implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			ObjectOutputStream toServer = null;
			Socket socket;
			Person person1 = new Person("No.1-player");
			try {
			socket = new Socket("localhost", 8000);
			int ready;
			System.out.println("if ready, please input number 1");
			Scanner in = new Scanner(System.in);
			ready = in.nextInt();
			if(ready==1) {
				toServer = new ObjectOutputStream(socket.getOutputStream());
				toServer.writeObject(person1);
				toServer.close();
				}
			in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	public Person() {
		
	}
	public Person(String name){
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Person1 [name=" + name + "]";
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
}
