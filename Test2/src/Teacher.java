import java.util.Comparator;

public class Teacher implements Comparator<Teacher> {
	private String id;
	public Teacher(String id){
		this.id = id;
	}
	public Teacher(){
		
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int compare(Teacher s1, Teacher s2) {
		int id1 = Integer.parseInt(s1.getId());
		int id2 = Integer.parseInt(s2.getId());
		return (id1<id2) ? -1 : (id1==id2) ? 0 : 1;
	}
}
