package HongWu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class HongWu {

	public static void main(String[] args) {
		Deck deck = new Deck();
		deck.reset();
		deck.shuffle();
		for (int i = 0; i < 53; i++) {
			deck.drawCard();
		}
	}
}

class Card {
	private String value;
	private String suit;

	public String getSuit() {
		return suit;
	}

	public void setSuit(String suit) {
		this.suit = suit;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String toString() {
		return suit + value;
	}
}

class Deck {
	private String[] huase = { "红桃", "黑桃", "方片", "梅花" };
	private String[] value = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
	private Card[] card = null;
	private List randomList = new ArrayList();
	private int idx = 0;

	public void reset() {
		List data = new ArrayList();
		for (int i = 0; i < huase.length; i++) {
			for (int j = 0; j < value.length; j++) {
				Card c = new Card();
				c.setSuit(huase[i]);
				c.setValue(value[j]);
				data.add(c);
			}
		}
		card = (Card[]) data.toArray(new Card[0]);

	}

	public void shuffle() {
		while (randomList.size() < 52) {
			int i = (int) (Math.random() * 52);
			if (!randomList.contains(i)) {
				randomList.add(i);
			}
		}
		for (int i = 0; i < randomList.size(); i++) {
			int idx = Integer.parseInt(String.valueOf(randomList.get(i)));
			System.out.print(card[idx] + ",");
		}
		System.out.println();
	}

	public void drawCard() {
		if (idx >= 52) {
			System.out.println("已发完毕");
		} else {
			int a = Integer.parseInt(String.valueOf(randomList.get(idx++)));
		}
	}
}
