import java.util.ArrayList;

public class GenericSort {
	public static void main(String[] args) {
		Integer[] intArray = {new Integer(2), new Integer(4), new Integer(3)};
		int[] list = {new Integer(1),new Integer(2),new Integer(3)};
		ArrayList stack = new ArrayList();
		sort(intArray);
		//sort(list);
		for(int i:intArray) {
			System.out.print(i+" ");
		}
	}
	public static <E extends Comparable<E>> void sort(E[] list) {
		E currentMin;
		int currentIndex;
		for(int i=0;i<list.length-1;i++) {
			currentMin = list[i];
			currentIndex = i;
			for(int j=i+1;j<list.length;j++) {
				if(currentMin.compareTo(list[j])>0) {
					currentMin = list[j];
					currentIndex =j;
				}
			}
			if(currentIndex!=i) {
				list[currentIndex] = list[i];
				list[i] = currentMin;
			}
		}
	}
}
