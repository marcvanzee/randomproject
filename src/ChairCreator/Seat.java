/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ChairCreator;

public class Seat extends ChairEngine {
	private int[] seatPoints;
	public int[] seatCross;
	public boolean innerPoint;

	public Seat() {
	}

	public Seat(int[] p) {
		seatPoints = p;
	}

	public int getPoint(int num) {
		return seatPoints[num];
	}

	public int[] getPoints() {
		return seatPoints;
	}

	public void orden(Point3D[] square, Plane vectorSquare) {

		for (int i = 0; i < 4; i++) {

			Plane cut1 = new Plane(square[i], square[(i + 1) % 4],
					vectorSquare.normalVector);
			int min = 0;
			int plus = 0;

			int[] minArray = new int[4];
			int[] plusArray = new int[4];

			for (int j = 0; j < 4; j++) {

				double direction = Calculator.pointToPlane(cut1,
						points[seatPoints[j]], false);

				if (direction < 0) {
					minArray[min] = seatPoints[j];
					min++;
				}
				if (direction > 0) {
					plusArray[plus] = seatPoints[j];
					plus++;
				}
			}

			if (min == plus) {
				double direction = Calculator.pointToPlane(cut1,
						square[(i + 2) % 4], false);

				if (direction < 0)
					seatCross = new int[] { minArray[0], minArray[1] };
				if (direction > 0)
					seatCross = new int[] { plusArray[0], plusArray[1] };

				innerPoint = true;

				return;

			} else {

				innerPoint = false;

				seatCross = new int[] { seatPoints[0], seatPoints[2] };
			}
		}
	}
}
