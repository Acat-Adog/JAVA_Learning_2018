package NewBankSim;

public class GeneratorCustomer {
	private int gTime;

	public int getgTime() {
		return gTime;
	}

	public void setgTime(int gTime) {
		this.gTime = gTime;
	}
	
	public Customer CustomerGenerator() {
		int name=1;BusinessType type; int vip;
		
		type = BusinessType.ck;
		int gTime = (int) (Math.random() * 2 + 1);
		
		return null;
	}
}
