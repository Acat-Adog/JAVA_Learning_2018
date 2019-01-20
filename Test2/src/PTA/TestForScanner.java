package PTA;

import java.util.Scanner;

public class TestForScanner {
	 public static void main(String[] args) {
		 Scanner s = new Scanner(System.in);
		 String judge=null;
		 while(s.hasNext()) {
			 if(s.hasNext()) {
				 judge = s.next(); 
				 System.out.println(judge); 
			 }
			 else {
				 break;
			 }
		 }
		 System.out.println(judge); 
		 
	 }
	
	
	
	
	
}
