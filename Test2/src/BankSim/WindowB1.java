package BankSim;

import java.util.LinkedList;

public class WindowB1 {
	public static int Wb1Timeing = 0;
	public static LinkedList<Customer> queueB1 = new LinkedList<Customer>();

	public void CustomerLeave() {
		while (true) {
			if (Bank.coccurTime > 540) {
				break;
			}
			Customer.Customerlock.lock();

			while (!Bank.woccurTime.isEmpty() && !queueB1.isEmpty()
					&& queueB1.getFirst().getDuration() + Wb1Timeing == Bank.woccurTime.peek()
					&& Bank.woccurTime.peek() <= Bank.coccurTime) {
				Customer cleave = new Customer();
				cleave = queueB1.poll();
				cleave.setLiveTime(Bank.woccurTime.peek() / 60 + Bank.woccurTime.peek() % 60 * 0.01 + 8);
				Wb1Timeing += cleave.getDuration();
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
				if (!queueB1.isEmpty()) {
					Bank.woccurTime.offer(queueB1.getFirst().getDuration() + Wb1Timeing);
				}
				// System.out.println("离开B1银行: "+cleave);
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

	public static int getWb1Timeing() {
		return Wb1Timeing;
	}

	public static void setWb1Timeing(int wb1Timeing) {
		Wb1Timeing = wb1Timeing;
	}
}
