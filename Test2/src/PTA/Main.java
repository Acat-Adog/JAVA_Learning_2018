package PTA;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<Person> personList = new ArrayList<Person>();
		Scanner in = new Scanner(System.in);
		String type = in.next();
		while(type.equals("s")||type.equals("e")) {
			if(type.equals("s")) {
				String name = in.next();
				int age = in.nextInt();
				boolean gender = in.nextBoolean();
				String stuNo = in.next();
				String clazz = in.next();
				Student s = new Student(name, age, gender, stuNo, clazz);
				personList.add(s);
			}
			else {
				String name = in.next();  //String
				int age = in.nextInt();
				boolean gender = in.nextBoolean();
				double salary = in.nextDouble();
				String companyName = in.next();
				if(name.equals("null")) {
					name = null;
				}
				Company company = null;
				if(!companyName.equals("null")) {
					company = new Company(companyName);
				}
				personList.add(new Employee(name, age, gender, salary, company));
			}
		type = in.next();
		}
		
		Collections.sort(personList);
		for(Person p:personList) {
			System.out.println(p);
		}
		String next  = in.next();
		in.close();
		if(!next.equals("exit")) {
			List<Student> stuList = new ArrayList<Student>();
			List<Employee> empList = new ArrayList<Employee>();
			for(Person p:personList) {
				if(p instanceof Student) {
					int flag = 1;
					for(Student s:stuList) {
						if(s.equals(p)) {
							flag = 0;
							break;
						}
					}
					if(flag == 1) {
						stuList.add((Student) p);
					}
				}else if(p instanceof Employee) {
					int flag = 1;
					for(Employee e:empList) {
						if(e.equals(p)) {
							flag = 0;
							break;
						}
					}
					if(flag == 1) {
						empList.add((Employee) p);
					}
				}
			}
			System.out.println("stuList");
			for(Student s:stuList) {
				System.out.println(s);
			}
			System.out.println("empList");
			for(Employee e:empList) {
				System.out.println(e);
			}	
		}
	}
	

}
abstract class Person implements Comparable<Person>{
	private String name;
	private int age;
	private boolean gender;
	public Person(String name, int age, boolean gender) {
		this.name = name;
		this.age = age;
		this.gender = gender;
	}
	@Override
	public int compareTo(Person o) {
		int res = this.name.compareTo(o.name);
		return res == 0?Integer.compare(this.age, o.age):res;
	}
	@Override
	public String toString() {
		return name + "-" + age + "-" + gender;
	}

	public boolean equals(Object obj) {
		Person p = (Person) obj;
		return this.name.equals(p.getName())&&this.age == p.getAge()&&this.gender == p.isGender();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}
}

class Student extends Person{
	private String stuNo;
	private String clazz;
	public Student(String name, int age, boolean gender, String stuNo, String clazz){
		super(name, age, gender);
		this.stuNo = stuNo;
		this.clazz = clazz;
	}
	public String toString(){
		return "Student: " + super.toString() + "-"+ stuNo + "-" + clazz;
	}
	public boolean equals(Object obj){
		Student s = (Student)obj;
		if(super.equals(obj)){
			return this.stuNo.equals(s.getStuNo())&&this.clazz.equals(s.getClazz());
		}
		return false;
	}
	public String getStuNo() {
		return stuNo;
	}
	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
}
class Employee extends Person{
	private Company company;
	private double salary;
	public Employee(String name, int age, boolean gender, double salary, Company company){
		super(name, age, gender);
		this.salary = salary;
		this.company = company;
	}
	@Override
	public String toString() {
		return "Employee: " + super.toString() + "-" + company + "-" + salary;
	}
	public boolean equals(Object obj){
		Employee e = (Employee) obj;
		if(super.equals(obj)){
			DecimalFormat df = new DecimalFormat("#.#");
			return this.company == e.getCompany() && df.format(this.salary) == df.format(e.getSalary());
		}
		return false;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	
}


class Company{
	private String name;
	public Company(String name){
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
	public boolean equals(Object obj){
		Company c = (Company) obj;
		return this.name.equals(c.getName());
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
}