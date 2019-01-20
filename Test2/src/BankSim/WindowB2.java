package BankSim;

import java.util.LinkedList;

public class WindowB2 {
	public static int Wb2Timeing;
	public static LinkedList<Customer> queueB2 = new LinkedList<Customer>();

	public void CustomerLeave() {
		while (true) {
			if (Bank.coccurTime > 540) {
				break;
			}
			Customer.Customerlock.lock();

			while (!Bank.woccurTime.isEmpty() && !queueB2.isEmpty()
					&& queueB2.getFirst().getDuration() + Wb2Timeing == Bank.woccurTime.peek()
					&& Bank.woccurTime.peek() <= Bank.coccurTime) {
				Customer cleave = new Customer();
				cleave = queueB2.poll();
				cleave.setLiveTime(Bank.woccurTime.peek() / 60 + Bank.woccurTime.peek() % 60 * 0.01 + 8);
				Wb2Timeing += cleave.getDuration();
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
				if (!queueB2.isEmpty()) {
					Bank.woccurTime.offer(queueB2.getFirst().getDuration() + Wb2Timeing);
				}
				// System.out.println("离开B2银行: "+cleave);
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

	public static int getWb2Timeing() {
		return Wb2Timeing;
	}

	public static void setWb2Timeing(int wb2Timeing) {
		Wb2Timeing = wb2Timeing;
	}

}
