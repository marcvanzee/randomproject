package ChairCreator;

class Edge extends ChairEngine {
	public int a, b;

	public Edge() {
		a = 0;
		b = 0;
	}

	public Edge(int A, int B) {
		if (A < B) {
			a = A;
			b = B;
		} else {
			a = B;
			b = A;
		}
	}

	public double length() {
		return Calculator.lineLength(points[a], points[b]);
	}

	public String print() {
		return Double.toString(points[a].x) + ","
				+ Double.toString(points[a].y) + ","
				+ Double.toString(points[a].z) + " "
				+ Double.toString(points[b].x) + ","
				+ Double.toString(points[b].y) + ","
				+ Double.toString(points[b].z) + " ";
	}
}
