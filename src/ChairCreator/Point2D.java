/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ChairCreator;

public class Point2D {

	public double x, y;

	public Point2D() {
	}

	public Point2D(double intx, double inty) {
		this.x = intx;
		this.y = inty;
	}

	public Point2D(Point3D point) {
		this.x = point.x;
		this.y = point.y;
	}

	public Point2D(Point3D point, Vector vector) {
		this.x = point.x + vector.dX;
		this.y = point.y + vector.dY;
	}

	public String print() {
		return Double.toString(this.x * 100) + ","
				+ Double.toString(this.y * 100);
	}

	public Point2D copy() {
		return new Point2D(this.x, this.y);
	}

	public void fill(int x, int y) {
		this.x = x;
		this.y = y;
	}
}
