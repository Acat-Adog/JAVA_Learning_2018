package BankSim;

import java.io.Serializable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Customer implements Serializable {
	public static Lock Customerlock = new ReentrantLock();
	public static Condition leaveCus = Customerlock.newCondition(); // 等待客户离开队列
	public static Condition waitCus = Customerlock.newCondition(); // 等待客户进入队列
	public static Condition AwaitCus = Customerlock.newCondition(); // 等待客户进入A队列
	public static Condition B1waitCus = Customerlock.newCondition(); // 等待客户进入B1队列
	public static Condition B2waitCus = Customerlock.newCondition(); // 等待客户进入B2队列
	public static Condition VwaitCus = Customerlock.newCondition(); // 等待客户进入V队列
	private static int id = 1;
	private String name;
	private int vip;
	private int nType;
	private int duration; // 处理业务时间
	private int intertime; // 抵达bank所需要时间
	private double arriveTime; // 到达Bank的时刻
	private double liveTime; // 离开的时刻

	public Customer() {

	}

	public Customer(String name, int vip, int nType, int duration, int intertime, double arriveTime) {
		this.name = name;
		this.vip = vip;
		this.nType = nType;
		this.duration = duration; // 处理的时间
		this.intertime = intertime; // 进入Bank所需哟的时间
		this.arriveTime = arriveTime;
	}

	public void CustomerGenerator() {

		while (true) {
			Customerlock.lock();
			int intertime = (int) (Math.random() * 2 + 1);
			Bank.coccurTime += intertime;
			if (Bank.coccurTime <= 540) {
				int vip = 0, nType, duration;
				double arriveTime;
				if (id % 700 == 1)
					vip = 1;
				nType = (int) (Math.random() * 8 + 1) % 8 + 1;
				duration = new Customer().getRanDomDuration(nType);
				arriveTime = Bank.coccurTime / 60 + (Bank.coccurTime) % 60 * 0.01 + 8;
				Customer c = new Customer(String.valueOf(id), vip, nType, duration, intertime, arriveTime);
				id++;
				// Bank.CustomerNum++;
				// System.out.println(c);
				// System.out.println(Bank.CustomerNum);

				while (!Bank.woccurTime.isEmpty() && Bank.coccurTime >= Bank.woccurTime.peek()) {
					try {
						Customer.waitCus.signalAll();
						Customer.leaveCus.await();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block

						e.printStackTrace();
					}
				}

				if (c.getnType() == 3 || c.getnType() == 6 || c.getnType() == 8) {
					if (WindowA.queueA.size() <= WindowV.queueV.size()) {
						if (c.getVip() == 0) {
							if (WindowA.queueA.isEmpty()) {
								WindowA.WaTimeing = Bank.coccurTime;
								Bank.woccurTime.offer(Bank.coccurTime + c.getDuration());
								// System.out.println("此时的下一个离开时间为： " + Bank.woccurTime.peek());
							} // 判断是否为空，
							WindowA.queueA.offer(c);
							System.out.println(c.getName() + "进入A窗口");
						} else {
							if (WindowV.queueV.isEmpty()) {
								WindowV.WvTimeing = Bank.coccurTime;
								Bank.woccurTime.offer(Bank.coccurTime + c.getDuration());
								// System.out.println("此时的下一个离开时间为： " + Bank.woccurTime.peek());
							} // 判断是否为空，
							WindowV.queueV.offer(c);
							if (c.getnType() == 1 && WindowV.queueV.peek() == c) {

								Bank.woccurTime.offer(WindowV.queueV.peek().getDuration() + WindowV.WvTimeing);
							}
							System.out.println(c.getName() + "进入V窗口");
						}

					} else {
						if (WindowV.queueV.isEmpty()) {
							WindowV.WvTimeing = Bank.coccurTime;
							Bank.woccurTime.offer(Bank.coccurTime + c.getDuration());
							// System.out.println("此时的下一个离开时间为： " + Bank.woccurTime.peek());
						} // 判断是否为空，
						WindowV.queueV.offer(c);
						if (c.getnType() == 1 && WindowV.queueV.peek() == c) {

							Bank.woccurTime.offer(WindowV.queueV.peek().getDuration() + WindowV.WvTimeing);
						}
						System.out.println(c.getName() + "进入V窗口");
					}
				} // 办理的业务必须在A或V窗口执行
				else {
					if (WindowA.queueA.size() <= WindowB1.queueB1.size()
							&& WindowA.queueA.size() <= WindowB2.queueB2.size()
							&& WindowA.queueA.size() <= WindowV.queueV.size()) {
						if (c.getVip() == 0) {
							if (WindowA.queueA.isEmpty()) {
								WindowA.WaTimeing = Bank.coccurTime;
								Bank.woccurTime.offer(Bank.coccurTime + c.getDuration());
								// System.out.println("此时的下一个离开时间为： " + Bank.woccurTime.peek());
							} // 判断是否为空，
							WindowA.queueA.offer(c);
							System.out.println(c.getName() + "进入A窗口");
						} else {
							if (WindowV.queueV.isEmpty()) {
								WindowV.WvTimeing = Bank.coccurTime;
								Bank.woccurTime.offer(Bank.coccurTime + c.getDuration());
								// System.out.println("此时的下一个离开时间为： " + Bank.woccurTime.peek());
							} // 判断是否为空，
							WindowV.queueV.offer(c);
							if (c.getnType() == 1 && WindowV.queueV.peek() == c) {

								Bank.woccurTime.offer(WindowV.queueV.peek().getDuration() + WindowV.WvTimeing);
							}
							System.out.println(c.getName() + "进入V窗口");
						}

					} else if (WindowB1.queueB1.size() <= WindowA.queueA.size()
							&& WindowB1.queueB1.size() <= WindowB2.queueB2.size()
							&& WindowB1.queueB1.size() <= WindowV.queueV.size()) {
						if (c.getVip() == 0) {
							if (WindowB1.queueB1.isEmpty()) {
								WindowB1.Wb1Timeing = Bank.coccurTime;
								Bank.woccurTime.offer(Bank.coccurTime + c.getDuration());
								// System.out.println("此时的下一个离开时间为： " + Bank.woccurTime.peek());
							} // 判断是否为空，
							WindowB1.queueB1.offer(c);
							System.out.println(c.getName() + "进入B1窗口");
						} else {
							if (WindowV.queueV.isEmpty()) {
								WindowV.WvTimeing = Bank.coccurTime;
								Bank.woccurTime.offer(Bank.coccurTime + c.getDuration());
								// System.out.println("此时的下一个离开时间为： " + Bank.woccurTime.peek());
							} // 判断是否为空，
							WindowV.queueV.offer(c);
							if (c.getnType() == 1 && WindowV.queueV.peek() == c) {

								Bank.woccurTime.offer(WindowV.queueV.peek().getDuration() + WindowV.WvTimeing);
							}
							System.out.println(c.getName() + "进入V窗口");
						}

					} else if (WindowB2.queueB2.size() <= WindowB1.queueB1.size()
							&& WindowB2.queueB2.size() <= WindowA.queueA.size()
							&& WindowB2.queueB2.size() <= WindowV.queueV.size()) {
						if (c.getVip() == 0) {
							if (WindowB2.queueB2.isEmpty()) {
								WindowB2.Wb2Timeing = Bank.coccurTime;
								Bank.woccurTime.offer(Bank.coccurTime + c.getDuration());
								// System.out.println("此时的下一个离开时间为： " + Bank.woccurTime.peek());
							} // 判断是否为空，
							WindowB2.queueB2.offer(c);
							System.out.println(c.getName() + "进入B2窗口");
						} else {
							if (WindowV.queueV.isEmpty()) {
								WindowV.WvTimeing = Bank.coccurTime;
								Bank.woccurTime.offer(Bank.coccurTime + c.getDuration());
								// System.out.println("此时的下一个离开时间为： " + Bank.woccurTime.peek());
							} // 判断是否为空，
							WindowV.queueV.offer(c);
							if (c.getnType() == 1 && WindowV.queueV.peek() == c) {

								Bank.woccurTime.offer(WindowV.queueV.peek().getDuration() + WindowV.WvTimeing);
							}
							System.out.println(c.getName() + "进入V窗口");
						}

					} else if (WindowV.queueV.size() <= WindowB1.queueB1.size()
							&& WindowV.queueV.size() <= WindowB2.queueB2.size()
							&& WindowV.queueV.size() <= WindowA.queueA.size()) {
						if (WindowV.queueV.isEmpty()) {
							WindowV.WvTimeing = Bank.coccurTime;
							Bank.woccurTime.offer(Bank.coccurTime + c.getDuration());
							// System.out.println("此时的下一个离开时间为： " + Bank.woccurTime.peek());
						} // 判断是否为空，
						WindowV.queueV.offer(c);
						if (c.getnType() == 1 && WindowV.queueV.peek() == c) {

							Bank.woccurTime.offer(WindowV.queueV.peek().getDuration() + WindowV.WvTimeing);
						}
						System.out.println(c.getName() + "进入V窗口");
					}
				} // 办理的业务在A B1 B2 V 窗口都可以执行

				Customerlock.unlock();
			}

			else {
				System.out.println("营业结束");
				Customer.waitCus.signalAll();
				Customerlock.unlock();
				break;
			}
		}

	}

	@Override
	public String toString() {
		return "Customer [name=" + name + ", vip=" + vip + ", nType=" + nType + ", arriveTime=" + arriveTime
				+ ", liveTime=" + liveTime + "]";
	}

	public int getRanDomDuration(int nType) {
		if (nType == 1)
			return (int) (Math.random() * 4 + 2);
		if (nType == 5)
			return (int) (Math.random() * 2 + 6);
		if (nType == 2)
			return (int) (Math.random() * 4 + 2);
		if (nType == 6)
			return (int) (Math.random() * 4 + 8);
		if (nType == 3)
			return (int) (Math.random() * 3 + 5);
		if (nType == 7)
			return (int) (Math.random() * 4 + 12);
		if (nType == 4)
			return (int) (Math.random() * 12 + 20);
		if (nType == 8)
			return (int) (Math.random() * 8 + 8);
		return 0;
	}

	public int getnType() {
		return nType;
	}

	public void setnType(int nType) {
		this.nType = nType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getVip() {
		return vip;
	}

	public void setVip(int vip) {
		this.vip = vip;
	}

	public double getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(double arriveTime) {
		this.arriveTime = arriveTime;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getIntertime() {
		return intertime;
	}

	public void setIntertime(int intertime) {
		this.intertime = intertime;
	}

	public static Condition getLeaveCus() {
		return leaveCus;
	}

	public static void setLeaveCus(Condition leaveCus) {
		Customer.leaveCus = leaveCus;
	}

	public double getLiveTime() {
		return liveTime;
	}

	public void setLiveTime(double liveTime) {
		this.liveTime = liveTime;
	}
}
