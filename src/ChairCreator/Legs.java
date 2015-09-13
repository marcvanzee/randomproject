package ChairCreator;

public class Legs extends ChairEngine {

	private static final long serialVersionUID = 1L;

	private int[] legPoints;

	public Legs() {
	}

	public Legs(int[] p) {
		legPoints = p;
	}

	public int getPoint(int num) {
		return legPoints[num];
	}

	public int[] getPoints() {
		return legPoints;
	}

	public void setPoint(int num, int p) {
		legPoints[num] = p;
		System.out.println(p);
	}
}
