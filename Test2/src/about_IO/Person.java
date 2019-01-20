package about_IO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

public class Person implements Serializable{
	private String name;
	private int age;
	private boolean gender;
	private int id;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	Person(){
		System.out.println("This is constructor: ");
		//System.out.println(this.toString());
	}
	Person(String name, int age, Boolean gender, int id){
		this.name = name;
		this.age = age;
		this.gender = gender;
		this.id = id;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", gender=" + gender + ", id=" + id + "]";
	}
	public static void main(String[] args) throws IOException, ClassNotFoundException{
			Scanner scanner = new Scanner(System.in);
			int n = scanner.nextInt();
			ObjectOutputStream oos = new  ObjectOutputStream(new FileOutputStream("E:/作业/code/Niko.txt"));
			for(int i=0;i<n;i++) {
				Person p = new Person(scanner.next(),scanner.nextInt(),scanner.nextBoolean(), i);
				oos.writeObject(p);
			}
			oos.close();
			
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream("E:/作业/code/Niko.txt"));
			Object obj = null;
			try {
			while((obj=ois.readObject())!=null) {
				Person p = (Person) obj;
				System.out.println(p.toString()); 
			}
			}catch(ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch (java.io.EOFException e) {
				// TODO Auto-generated catch block
				  System.out.println("读到了文件末尾"); 
			}
			ois.close();
	}
}
