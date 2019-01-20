import java.util.Comparator;
import java.util.LinkedList;

public class Student implements Comparator<Student>{
	private String id;
	public Student(String id){
		this.id = id;
	}
	public Student(){
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int compare(Student s1, Student s2) {
		int id1 = Integer.parseInt(s1.getId());
		int id2 = Integer.parseInt(s2.getId());
		return (id1<id2) ? 1 : (id1==id2) ? 0 : -1;
	}
	public static void main(String[] args) {
		LinkedList<String> l = new LinkedList<String>();
		l.offer("1");l.offer("3");l.offer("2");
		//for(String a:l)
		System.out.println(l.peek());
	}
}
