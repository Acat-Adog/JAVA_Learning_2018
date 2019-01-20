import java.util.ArrayList;
import java.util.TreeSet;

public class Question_1<E> {
	private ArrayList<E> list = new ArrayList<>();
	public int getSize() {
		return list.size();
	}
	public E peek() {
		return list.get(getSize()-1);
	}
	public void push(E o) {
		list.add(o);
	}
	public E pop() {
		E o;
		o = list.get(getSize()-1);
		list.remove(getSize()-1);
		return o;
	}
	public boolean isEmpty() {
		return list.isEmpty();
	}
	public String toString() {
		return "stack" + list.toString();
	}
	public static void main(String[] args) {
		Question_1<String> q1 = new Question_1<>();
		TreeSet<String> t = new TreeSet<>();
		q1.push("QQQ");
		q1.push("WWW");
		q1.push("EEE");
		q1.pop();
		q1.pop();
		
		
		Question_1<Integer> q2 = new Question_1<>();
		q2.push(1);
		q2.push(2);
		q2.push(3);
		
		
		System.out.println(q1.isEmpty());
	//	System.out.println(q2.toString());
	}
}
