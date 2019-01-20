import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class LinkedList_look {
	public static void main(String[] args) {
		LinkedList<test> queue = new LinkedList<test>();
		queue.add(new test("club3"));queue.add(new test("Heart5"));queue.add(new test("King"));
		test t1 = new test("kenny");
		test t2 = new test("clod","123");
		if(t1.getSize()!=null){
		if(t1.getSize().equals("123")) {
			System.out.println("11111111");
		}
		}
		
	}
}

class test {
	private String name;
	private String size;
	
	
	public test(String name, String size) {
		this.name = name;
		this.size = size;
	}
	public test(String name) {
		this.name = name;
		
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "test [name=" + name + "]";
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
}
