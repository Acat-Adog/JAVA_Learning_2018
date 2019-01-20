package BankSim;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class WindowV {
	public static int WvTimeing;
	public static PriorityQueue<Customer> queueV = new PriorityQueue<Customer>(11, new Comparator<Customer>() {
		@Override
		public int compare(Customer o1, Customer o2) {
			int i = 1;
			if (o2.getVip() - o1.getVip() > 0)
				return 1;
			else if (o2.getVip() - o1.getVip() < 0)
				return -1;
			else {
				if (o2.getArriveTime() - o1.getArriveTime() > 0)
					return -1;
				else if (o2.getArriveTime() - o1.getArriveTime() < 0)
					return 1;
				else
					return 0;
			}
			// TODO Auto-generated method stub

		}
	});

	public void CustomerLeave() {
		while (true) {
			if (Bank.coccurTime > 540) {
				break;
			}
			Customer.Customerlock.lock();

			while (!Bank.woccurTime.isEmpty() && !queueV.isEmpty()
					&& queueV.peek().getDuration() + WvTimeing == Bank.woccurTime.peek()
					&& Bank.woccurTime.peek() <= Bank.coccurTime) {
				Customer cleave = new Customer();
				cleave = queueV.poll();
				cleave.setLiveTime(Bank.woccurTime.peek() / 60 + Bank.woccurTime.peek() % 60 * 0.01 + 8);
				WvTimeing += cleave.getDuration();

				Bank.woccurTime.poll();
				Bank.Sum += cleave.getDuration();
				Bank.CustomerNum++;
				Bank.Ctxt.add(cleave);
				if (cleave.getnType() == 1)
					Bank.rate1++;
				if (cleave.getnType() == 5)
					Bank.rate5++;
				if (cleave.getnType() == 2)
					Bank.rate2++;
				if (cleave.getnType() == 6)
					Bank.rate6++;
				if (cleave.getnType() == 3)
					Bank.rate3++;
				if (cleave.getnType() == 7)
					Bank.rate7++;
				if (cleave.getnType() == 4)
					Bank.rate4++;
				if (cleave.getnType() == 8)
					Bank.rate8++;
				System.out.println(cleave.getName() + "离开");
				if (!queueV.isEmpty()) {
					Bank.woccurTime.offer(queueV.peek().getDuration() + WvTimeing);
				}
				// System.out.println("离开V银行: "+cleave);
				// System.out.println("下一个退出的时间点" + Bank.woccurTime.peek());
			}

			while (!Bank.woccurTime.isEmpty() && Bank.woccurTime.peek() > Bank.coccurTime) {
				try {
					Customer.leaveCus.signalAll();
					Customer.waitCus.await();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			Customer.Customerlock.unlock();
		}

	}

	public static int getWvTimeing() {
		return WvTimeing;
	}

	public static void setWvTimeing(int wvTimeing) {
		WvTimeing = wvTimeing;
	}

}
