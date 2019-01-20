package NewHongWu_Client1;

import java.io.Serializable;

public class Poker implements Serializable{
		private String poker;
		private String pokertype;
		private String size;
		private int sizeNum;
		private int realNum;
		private int index;
		private static final long serialVersionUID = 3L;
		public Poker(String pokertype, String size){
			this.pokertype = pokertype;
			this.size = size;
			this.poker = pokertype+size;
		}
		public Poker(String pokertype, int sizeNum) {
			this.pokertype = pokertype;
			this.sizeNum = sizeNum;
			this.poker = pokertype;
		}
		public Poker() {
			
		}
		
		public String getPokertype() {
			return pokertype;
		}

		public void setPokertype(String pokertype) {
			this.pokertype = pokertype;
		}
		public String getSize() {
			return size;
		}
		public void setSize(String size) {
			this.size = size;
		}
		public String getPoker() {
			return poker;
		}
		public void setPoker(String poker) {
			this.poker = poker;
		}
		@Override
		public String toString() {
			return poker;
		}
		public int getIndex() {
			return index;
		}
		public void setIndex(int index) {
			this.index = index;
		}
		public int getSizeNum() {
			return sizeNum;
		}
		public void setSizeNum(int sizeNum) {
			this.sizeNum = sizeNum;
		}
		public int getRealNum() {
			return realNum;
		}
		public void setRealNum(int realNum) {
			this.realNum = realNum;
		}
		
}
