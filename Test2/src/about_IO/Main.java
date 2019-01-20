package about_IO;


import java.io.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub
		ObjectOutputStream  output =new ObjectOutputStream(new FileOutputStream("E:/作业/code/Niko.txt"));
		  Scanner   scanner=new Scanner(System.in);
		  int  count=scanner.nextInt() ;
		 // Person[] pList=new Person[count];
		  for(int i=0;i<count;i++)
		  {
			  Person1 person=new Person1(scanner.next(),scanner.nextInt(),scanner.nextBoolean());
			  output.writeObject(person);
		  }
		  output.close();
		  
		  ObjectInputStream  input =new ObjectInputStream(new FileInputStream("E:/作业/code/Niko.txt"));
		  
		  Object obj=null; 
		  try
		  {
           while((obj=input.readObject())!=null) { 
              Person1 s = (Person1)obj; 
              System.out.println(s.toString()); 
          } 
		  
		  } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		  } catch (java.io.EOFException e) {
			// TODO Auto-generated catch block
			  System.out.println("读到了文件末尾"); 
		}
		  input.close();
	}

}

class Person1   implements Serializable  {
   private String name;
   private int age;
   private boolean gender;
   private  int id;
public Person1() {
  System.out.println("Constructor");
}
public Person1(String name, int age, boolean gender) {
	super();
	this.name = name;
	this.age = age;
	this.gender = gender;
}
@Override
public String toString() {
	return "Person [name=" + name + ", age=" + age + ", gender=" + gender + ", id=" + id + "]";
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
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}

   
}