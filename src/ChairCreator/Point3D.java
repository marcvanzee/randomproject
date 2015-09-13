/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ChairCreator;

public class Point3D {

    public double x, y, z;
    public int[] attachedTo;

	public Point3D() {}
	public Point3D(double intx, double inty, double intz) {
		this.x = intx;
		this.y = inty;
		this.z = intz;
	}
	public Point3D(Point3D point) {
		this.x = point.x;
		this.y = point.y;
		this.z = point.z;
	}

        public Point3D(Point3D point, Vector vector) {
		this.x = point.x + vector.dX;
		this.y = point.y + vector.dY;
		this.z = point.z + vector.dZ;
	}

        public Point3D(Vector vector) {
		this.x = vector.dX;
		this.y = vector.dY;
		this.z = vector.dZ;
	}

        public Point3D(Vector vector, double num) {
                double tempNum = vector.length();

                vector.resize(num);

		this.x = vector.dX;
		this.y = vector.dY;
		this.z = vector.dZ;

                vector.resize(tempNum);
	}

        //TODO hier een functie maken die een punt 2D teruggeeft.

        public void isAttachedTo(int[] nums){
            attachedTo = nums;
        }

	public String print() {
		return Double.toString(this.x) + "," + Double.toString(this.y) + "," + Double.toString(this.z) + " ";
	}

        public String STLPrint() {
		int xLen = Double.toString(this.x).length();
		int yLen = Double.toString(this.y).length();
		int zLen = Double.toString(this.z).length();

		int x = (xLen > 5) ? 5 : xLen;
		int y = (xLen > 5) ? 5 : yLen;
		int z = (xLen > 5) ? 5 : zLen;

		return Double.toString(this.x).substring(0, x) + " " + Double.toString(this.y).substring(0, y) + " " + Double.toString(this.z).substring(0, z);
	}

	public void fill(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

        public void addPoint(Point3D point) {
		this.x = point.x + this.x;
		this.y = point.y + this.y;
		this.z = point.z + this.z;
	}

        public void scalePoint(double scale) {
		this.x = scale * this.x;
		this.y = scale * this.y;
		this.z = scale * this.z;
	}

}

