package NewBankSim;

public class Customer {
	private String name;
	private int type;
	private int vip;
	private int arriveTime;
	private int useingTime;
	
	Customer(){
		
	}
	Customer(String name, int type, int vip, int arriveTime){
		this.name = name;
		this.type = type;
		this.vip = vip;
		this.arriveTime = arriveTime;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getVip() {
		return vip;
	}
	public void setVip(int vip) {
		this.vip = vip;
	}
	public int getArriveTime() {
		return arriveTime;
	}
	public void setArriveTime(int arriveTime) {
		this.arriveTime = arriveTime;
	}
	public int getUseingTime() {
		return useingTime;
	}
	public void setUseingTime(int useingTime) {
		this.useingTime = useingTime;
	}
	@Override
	public String toString() {
		return "Customer [name=" + name + ", type=" + type + ", vip=" + vip + ", arriveTime=" + arriveTime
				+ ", useingTime=" + useingTime + "]";
	}
}
