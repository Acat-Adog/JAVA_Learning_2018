import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class SortUtils {
	public static void main(String[] args) {
		Random random = new Random();
		TreeSet<Student> t = new TreeSet<>(new Student());
		TreeMap<Teacher,String> tm = new TreeMap<>(new Teacher());
		HashSet<Integer> has = new HashSet<Integer>();
		for(int i=0;i<20;i++) {
			int m = random.nextInt(50);
			while(has.contains(m)){
				   m = random.nextInt(20);
			   }
			has.add(m);
			t.add(new Student(String.valueOf(m)));
			tm.put(new Teacher(String.valueOf(m)) , "��"+m+"��");
		}
		for(Student s:t) {
			System.out.printf(s.getId()+" ");
		}
		//System.out.println();
		//System.out.println(tm);
	}
}
