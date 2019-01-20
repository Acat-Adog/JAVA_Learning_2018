package NewHongWu_Client2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Person implements Serializable{
	private String name;
    private static final long serialVersionUID = 2L;
	static List<Poker> mypokers = new ArrayList<Poker>();
	int point;
	
	ObjectOutputStream toServer = null;
	DataOutputStream toServerPoint = null;
	ObjectOutputStream toServerPoker = null;
	DataOutputStream toServerNum = null;
	ObjectInputStream inputPokerFromServer =  null;
	ObjectInputStream inputRestPokerFromServer =  null;
	DataInputStream inputPointFromServer = null;
	ObjectInputStream inputPersonFromServer =  null;
	
	public static void main(String[] args) {
		new Thread(new Person().new playPoker()).start();
	}
	
	class playPoker implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Socket socket;
			int sum=0;
			List<Poker> restPokers = new ArrayList<Poker>();
			Person person1 = new Person("No.2-player");
			try {
			socket = new Socket("localhost", 8000);
			toServer = new ObjectOutputStream(socket.getOutputStream());
			
			
			int ready;
			System.out.println("if ready, please input number 1");
			Scanner in = new Scanner(System.in);
			ready = in.nextInt();
			if(ready==1) {
				toServer.writeObject(person1);
				toServer.flush();
				System.out.println("Begin get poker: ");
				while(true) {
					inputPokerFromServer = new ObjectInputStream(socket.getInputStream());
					Object obj = inputPokerFromServer.readObject();
					if(((Poker) obj).getIndex()==2) {
						mypokers.add((Poker) obj);
					System.out.println("get: " + (Poker) obj + "  ");
					}
					sum++;
					if(sum==100) {
						break;
					}
				  }
				}
			
			for(int i=1;i<=8;i++) {
				inputRestPokerFromServer = new ObjectInputStream(socket.getInputStream());
				Object obj = inputRestPokerFromServer.readObject();
				restPokers.add((Poker) obj);
			}
			Collections.sort(mypokers, new Comparator<Poker>() {
				@Override
				public int compare(Poker o1, Poker o2) {
					// TODO Auto-generated method stub
					return o2.getSizeNum()- o1.getSizeNum();
				}
			});
			Collections.sort(restPokers, new Comparator<Poker>() {
				@Override
				public int compare(Poker o1, Poker o2) {
					// TODO Auto-generated method stub
					return o2.getSizeNum()- o1.getSizeNum();
				}
			});
			System.out.println("The rest of 8 pokers is: " + restPokers);
//			System.out.println("Mypokers: " + mypokers);
//			System.out.println("��ʼ����ׯ��, ������ȱ���Ϊ5, 10, 15, ����60��");
//			System.out.println("0---Give up");
//			while(true) {
//				inputPointFromServer = new DataInputStream(socket.getInputStream());		//���յ�����Ϣ�ӷ�����
//				int nowPoint = inputPointFromServer.readInt();
//				System.out.println("���ڵĵ����ǣ� " + nowPoint);
//				point = in.nextInt();
//				if(point==0) {
//					toServerPoint = new DataOutputStream(socket.getOutputStream());
//					toServerPoint.writeInt((Integer)point);		//���͸�������
//					break;
//				}
//				else {
//					toServerPoint = new DataOutputStream(socket.getOutputStream());
//					toServerPoint.writeInt((Integer)point);     //���͸�����������
//					
//				}
//			}
			System.out.println("-------------------------------------Start playing cards---------------------------------------");
			while(mypokers.size()!=0) {
				System.out.print("Mypokers: ");
				for(int i=1;i<=mypokers.size();i++) {
					System.out.print(i + "--" + mypokers.get(i-1) + "  ");
				}
				System.out.println();
				System.out.println("��������Ҫ����---1---2");
				int putNum = in.nextInt();
				toServerNum = new DataOutputStream(socket.getOutputStream());
				toServerNum.writeInt(putNum);
				if(putNum==1) {
					System.out.println("��������Ҫ��ڼ�����");
					int index = in.nextInt();
					toServerPoker = new ObjectOutputStream(socket.getOutputStream());
					toServerPoker.writeObject(mypokers.get(index-1));
					mypokers.remove(index-1);
				}
				else if(putNum==2) {
					System.out.println("��������Ҫ����������");
					int index = in.nextInt();int index2 = in.nextInt();
					toServerPoker = new ObjectOutputStream(socket.getOutputStream());
					toServerPoker.writeObject(mypokers.get(index-1));
					toServerPoker.writeObject(mypokers.get(index2-1));
					mypokers.remove(index2-1);
					mypokers.remove(index-1);
				}
				
				inputPersonFromServer = new ObjectInputStream(socket.getInputStream());
				Object obj = inputPersonFromServer.readObject();
				if(putNum==1) {
					System.out.println(((Person) obj).getName()+"���������,   �շ�: "+ ((Person) obj).getPoint()+ "��"  + "����һ���ȳ���");
					person1.point = person1.point+((Person) obj).getPoint();
				}
				if(putNum==2) {
					System.out.println(((Person) obj).getName()+"���������,   �շ�: "+ ((Person) obj).getPoint()*2+ "��"  + "����һ���ȳ���");
					person1.point = person1.point+((Person) obj).getPoint()*2;
				}
			}
			System.out.println("-------------------------------------Game over---------------------------------------");
			System.out.println("����: " + person1.point + "��");
			in.close();
			toServer.close();
			inputPokerFromServer.close();
			inputRestPokerFromServer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
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
