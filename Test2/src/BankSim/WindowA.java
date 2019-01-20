package BankSim;

import java.util.LinkedList;

public class WindowA {
	public static int WaTimeing = 0;
	public static LinkedList<Customer> queueA = new LinkedList<Customer>();

	public void CustomerLeave() {
		while (true) {
			if (Bank.coccurTime > 540) {
				break;
			}
			Customer.Customerlock.lock();

			while (!Bank.woccurTime.isEmpty() && !queueA.isEmpty()
					&& queueA.getFirst().getDuration() + WaTimeing == Bank.woccurTime.peek()
					&& Bank.woccurTime.peek() <= Bank.coccurTime) {
				Customer cleave = new Customer();
				cleave = queueA.poll();
				cleave.setLiveTime(Bank.woccurTime.peek() / 60 + Bank.woccurTime.peek() % 60 * 0.01 + 8);
				Bank.Sum += cleave.getDuration();
				Bank.CustomerNum++;
				WaTimeing += cleave.getDuration();
				Bank.woccurTime.poll();
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
				if (!queueA.isEmpty()) {
					Bank.woccurTime.offer(queueA.getFirst().getDuration() + WaTimeing);
				}
				// System.out.println("离开A银行 : "+cleave);
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

	public static int getWaTimeing() {
		return WaTimeing;
	}

	public static void setWaTimeing(int waTimeing) {
		WaTimeing = waTimeing;
	}
}
