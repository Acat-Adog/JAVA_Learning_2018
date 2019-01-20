package NewHongWu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

public class Main {
	static LinkedList<Poker> pokers = new LinkedList<Poker>();
	static HashMap<Person,Poker> handlePokers = new HashMap<Person,Poker>();
	
	static int readyNum = 0;
	static int pointMax=60;
	static int indexC=1;
	static HashMap<String, Socket> socketList = new HashMap<>();
	static Lock lock = new ReentrantLock();
	static Condition cLock = lock.newCondition();
	public static void main(String[] args) throws IOException {
		int port = 8000;
		int judge=0;
		ServerSocket server = new ServerSocket(port);
		greatPokers();      								//创建两副扑克牌
		upsetPokers();										//洗牌
		System.out.println("wait people come in.......");
		while(true) {
		Socket socket = server.accept();
		
		Socket socket2 = server.accept();
		Socket socket3 = server.accept();
		Socket socket4 = server.accept();
		System.out.println("wait player ready");
		
		InetAddress inetadress = socket.getInetAddress();
		socketList.put(String.valueOf(inetadress.getHostAddress()), socket);
//		System.out.println(inetadress.getHostAddress());
//		System.out.println(inetadress.getHostName());
		new Thread(new Main().new handlePoker(socket, socket2, socket3, socket4)).start();
		new Thread(new Main().new handlePoker(socket2, socket, socket3, socket4)).start();
		new Thread(new Main().new handlePoker(socket3, socket2, socket, socket4)).start();
		new Thread(new Main().new handlePoker(socket4, socket2, socket3, socket)).start();
	}	
		
	}

	class handlePoker implements Runnable {
		private Socket socket;
		private Socket socket2;
		private Socket socket3;
		private Socket socket4;
		public handlePoker(Socket socket, Socket socket2, Socket socket3, Socket socket4) {
			this.socket = socket;
			this.socket2 = socket2;
			this.socket3 = socket3;
			this.socket4 = socket4;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				Person itsme=null;
				ObjectInputStream inputFromClient = new ObjectInputStream(socket.getInputStream());
				Object object = null;
				object=inputFromClient.readObject();
				if(object!=null) {
					Main.readyNum++;
					itsme=(Person) object;
				}
				if(Main.readyNum>0&&Main.readyNum<4) {
					System.out.println(((Person) object).getName() + "  ready");
					System.out.println("wait for " + (4-readyNum) + "people");
				}
				
				if(Main.readyNum<4) {
					lock.lock();
					cLock.await();
					lock.unlock();
					//System.out.println("wait for ");
				}
				lock.lock();
				cLock.signalAll();
				lock.unlock();
				System.out.println("Game begin!!!");
				if(Main.readyNum==4) {
//					ObjectOutputStream outputPokerToServer =  new ObjectOutputStream(socket.getOutputStream());
					LinkedList<Poker> toClientPokers = new LinkedList<Poker>();
					toClientPokers.addAll(pokers);
					for(int i=1; i<=100;i++) {
						lock.lock();
						ObjectOutputStream outputPokerToServer =  new ObjectOutputStream(socket.getOutputStream());
						toClientPokers.getFirst().setIndex(i%4);
						Poker p = new Poker();
						p = toClientPokers.getFirst();
						toClientPokers.removeFirst();
						outputPokerToServer.writeObject(p);
						lock.unlock();
						Thread.sleep(200);
					}
					for(int i=1;i<=8;i++) {
						ObjectOutputStream outputRestPokerToServer =  new ObjectOutputStream(socket.getOutputStream());
						outputRestPokerToServer.writeObject(toClientPokers.removeFirst());
					}
					readyNum=0;
//					System.out.println("The rest of 8 pokers is: " + toClientPokers + "  "); //剩下的8张牌
//					while(true) {
//						DataInputStream inputPointFromClient = new DataInputStream(socket.getInputStream());
//						int point = inputPointFromClient.readInt();
//						
//						if(point==0) {
//							break;
//						}
//						else if(point>pointMax) {
//							pointMax = point;
//							DataOutputStream outputNowPointToClient = new DataOutputStream(socket.getOutputStream());
//							System.out.println(pointMax);
//							outputNowPointToClient.writeInt((Integer)pointMax);
//						}
//					}
				}
				while(true) {
						DataInputStream inputNumFromClient = new DataInputStream(socket.getInputStream());
						int putNum = inputNumFromClient.readInt();
						if(putNum==1) {
							ObjectInputStream inputPokerFromClient = new ObjectInputStream(socket.getInputStream());
							Object obj = inputPokerFromClient.readObject();
							if(obj!=null) {
								handlePokers.put(itsme,(Poker) obj);
								
								}
								System.out.println(handlePokers);
						}
						else if(putNum==2) {
							ObjectInputStream inputPokerFromClient1 = new ObjectInputStream(socket.getInputStream());
							Object obj1 = inputPokerFromClient1.readObject();
							Object obj2 = inputPokerFromClient1.readObject();
							if(!((Poker)obj1).getPoker().equals(((Poker)obj2).getPoker())) {
								((Poker) obj2).setRealNum(0);
							}
							handlePokers.put(itsme,(Poker) obj1);
							handlePokers.put(itsme,(Poker) obj2);
							System.out.println(handlePokers);
						}
						
						if(handlePokers.size()%4==0) {
							//计分并发送
							Person newP = null;
							newP  = getMaxPoker(itsme);
							ObjectOutputStream outputPersonToServer =  new ObjectOutputStream(socket.getOutputStream());
							outputPersonToServer.writeObject(newP);
							outputPersonToServer =  new ObjectOutputStream(socket2.getOutputStream());
							outputPersonToServer.writeObject(newP);
							outputPersonToServer =  new ObjectOutputStream(socket3.getOutputStream());
							outputPersonToServer.writeObject(newP);
							outputPersonToServer =  new ObjectOutputStream(socket4.getOutputStream());
							outputPersonToServer.writeObject(newP);
							handlePokers.clear();
						}
					}
					
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	
	public static Person getMaxPoker(Person itsme) {
		Set<Person> key = handlePokers.keySet();
	//	System.out.println("key num is " + key.size());
		Poker max = handlePokers.get(itsme);
		Person newP = itsme;
		int sum=0;
		System.out.println("max" + max);
		for (Person p : key) {
			if(max.getRealNum()<handlePokers.get(p).getRealNum()) {
		//		System.out.println("max" + max);
		//		System.out.println("handlePokers.get(p)" + handlePokers.get(p));
				max=handlePokers.get(p);
				newP = p;
			}
			if(handlePokers.get(p).getSize()!=null) {
			if(handlePokers.get(p).getSize().equals("K")||handlePokers.get(p).getSize().equals("10")) {
				sum=sum+10;
			}
			if(handlePokers.get(p).getSize().equals("5")) {
				sum=sum+5;
			}
		}
			
		}
		newP.setPoint(sum);
		System.out.println(newP.getPoint());    //Person的点数
		return newP;
	}
	
	public static void upsetPokers() {     					  //打乱扑克牌
		for(int i=0; i<1000; i++) {
			int index = (int)(1+Math.random()*107);
		//	System.out.println("随机数：" + index);
			Poker oldp = new Poker();
			oldp = pokers.get(index);
			pokers.remove(index);
			pokers.add(oldp);
		}
	}
	
	
	public static void greatPokers() {     //创建两副牌
		newPoker();
		newPoker();
		pokers.add(new Poker(String.valueOf(PokerType.King), 422, 422));
		pokers.add(new Poker(String.valueOf(PokerType.King), 422, 422));
		pokers.add(new Poker(String.valueOf(PokerType.Queen), 421, 421));
		pokers.add(new Poker(String.valueOf(PokerType.Queen),  421, 421));
	}
	
	
	
	
	
	public static void newPoker(){				//创建一副牌
		for(int i=1; i<=13; i++) {
			for(int j=1; j<=4; j++) {
				if(j==1) {
					String pokersize = null;
					int pokersizeNum;
					int pokerrealNum;
					if(i%13==0) {
						pokersizeNum = 313;
						pokerrealNum = 313;
					}
					else if(i%13==1) {
						pokersizeNum = 314;
						pokerrealNum=314;
					}
					else if(i%13==2) {
						pokersizeNum = 417;
						pokerrealNum=417;
					}
					else {
						pokersizeNum = i%13+300;
						pokerrealNum = i%13+300;
					}
					
					if(i%13==1) {
						pokersize = "A";
					}
					else if(i%13==11) {
						pokersize = "J";
					}
					else if(i%13==12) {
						pokersize = "Q";
					}
					else if(i%13==0) {
						pokersize = "K";
					}
					else {
						pokersize = String.valueOf(i%13);
					}
					pokers.add(new Poker(String.valueOf(PokerType.Spade),pokersize, pokersizeNum, pokerrealNum));
				}
				else if(j==2) {
					String pokersize = null;
					int pokersizeNum;
					int pokerrealNum;
					if(i%13==0) {
						pokersizeNum = 413;
						pokerrealNum=413;
					}
					else if(i%13==1) {
						pokersizeNum = 414;
						pokerrealNum=414;
					}
					else if(i%13==2) {
						pokersizeNum = 418;
						pokerrealNum=418;
					}
					else if(i%13==3) {
						pokersizeNum =420;
						pokerrealNum=420;
					}
					else if(i%13==5) {
						pokersizeNum =423;
						pokerrealNum=423;
					}
					else {
						pokersizeNum = i%13+400;
						pokerrealNum = i%13+400;
					}
					
					if(i%13==1) {
						pokersize = "A";
					}
					else if(i%13==11) {
						pokersize = "J";
					}
					else if(i%13==12) {
						pokersize = "Q";
					}
					else if(i%13==0) {
						pokersize = "K";
					}
					else {
						pokersize = String.valueOf(i%13);
					}
					pokers.add(new Poker(String.valueOf(PokerType.Heart),pokersize, pokersizeNum, pokerrealNum));
				}
				else if(j==3) {
					String pokersize = null;
					int pokersizeNum;
					int pokerrealNum = 0;
					if(i%13==0) {
						pokersizeNum = 113;
						pokerrealNum=313;
					}
					else if(i%13==1) {
						pokersizeNum = 114;
						pokerrealNum=314;
					}
					else if(i%13==2) {
						pokersizeNum = 415;
					}
					else if(i%13==3) {
						pokersizeNum = 419;
						pokerrealNum=419;
					}
					else {
						pokersizeNum = i%13+100;
						pokerrealNum = i%13+300;
					}
					
					if(i%13==1) {
						pokersize = "A";
					}
					else if(i%13==11) {
						pokersize = "J";
					}
					else if(i%13==12) {
						pokersize = "Q";
					}
					else if(i%13==0) {
						pokersize = "K";
					}
					else {
						pokersize = String.valueOf(i%13);
					}
					pokers.add(new Poker(String.valueOf(PokerType.Diamond),pokersize, pokersizeNum, pokerrealNum));
				}
				else if(j==4) {
					String pokersize = null;
					int pokersizeNum;
					int pokerrealNum = 0;
					if(i%13==0) {
						pokersizeNum = 213;
						pokerrealNum=313;
					}
					else if(i%13==1) {
						pokersizeNum = 214;
						pokerrealNum=314;
					}
					else if(i%13==2) {
						pokersizeNum = 416;
					}
					else {
						pokersizeNum = i%13+200;
						pokerrealNum = i%13+300;
					}
					
					if(i%13==1) {
						pokersize = "A";
					}
					else if(i%13==11) {
						pokersize = "J";
					}
					else if(i%13==12) {
						pokersize = "Q";
					}
					else if(i%13==0) {
						pokersize = "K";
					}
					else {
						pokersize = String.valueOf(i%13);
					}
					pokers.add(new Poker(String.valueOf(PokerType.Club),pokersize, pokersizeNum, pokerrealNum));
				}
			}
		}
	}
}
