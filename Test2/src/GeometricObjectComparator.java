import java.util.Comparator;

public class GeometricObjectComparator implements Comparator<GeometricObjectComparator>{
	private int g;
	public int getG() {
		return g;
	}
	public void setG(int g) {
		this.g = g;
	}
	public int compare(GeometricObjectComparator go1, GeometricObjectComparator go2) {
		int g1 = go1.getG();
		int g2 = go2.getG();
		return (g1<g2) ? -1:(g1==g2)?0:1;
	}
	
}
