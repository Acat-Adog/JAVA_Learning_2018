package NewBankSim;

public enum BusinessType {
	
	ck("存款", 1), qk("取款", 2), jnfk("缴纳罚款", 3), ktwy("开通网银", 4), jsdf("交水电费", 5), 
	gmjj("购买基金", 6), zzhk("转账汇款", 7), gdhk("个贷还款", 8);
	
	private String name;  
    private int index;
	
	BusinessType(String name, int index) {
		this.name = name;
		this.index = index;
	}
	
}
