package about_IO;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MyCopy {
	public static void main(String[] args) {
		try {
			FileReader fr = new FileReader("E:/作业/code/kenny.txt");
			BufferedReader frB = new BufferedReader(fr);
			FileWriter fw = new FileWriter("E:/作业/code/Niko.txt");
			BufferedWriter fwB = new BufferedWriter(fw);
			String s = null;
			while((s=frB.readLine())!=null) {
				fwB.write(s);
				fwB.newLine();
			}
			fwB.flush();
			fwB.close();
			fw.close();
			fr.close();
			frB.close();
		}catch(IOException e) {
			System.out.println(e.toString());
		}
	}
}
