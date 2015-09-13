package ChairCreator;

import java.applet.Applet;

public class GUIflash extends Applet {
	private static final long serialVersionUID = 1L;

	Point3D vertices[];
	Triangle triangles[];

	private String DELIM = "<br>";

	ChairEngine tryChair;

	static double seatMinHeight = 0.1;
	static double seatMaxHeight = 1.2;
	static double backMinAngle = 70;
	static double backMaxAngle = 150;
	static double legsMinAngle = 40;
	static double legsMaxAngle = 90;

	public String createChair() {
		tryChair = new ChairEngine();
		Boolean done = false;

		setVariables();

		int ronde = 0;
		while (!done) {
			done = tryChair.go();
			ronde++;
		}

		MassEngine massEngine = new MassEngine();
		massEngine.createMass();

		vertices = tryChair.points;
		triangles = tryChair.triangles;

		/*
		 * dit printen: NEWPOINTS [num]<br> point [i] x y z<br> NEWTRIANGLES
		 * [num]<br> triangle [i] 1 2 3<br> FINISHED
		 */

		String str = "NEWPOINTS " + vertices.length + DELIM;
		for (int i = 0; i < vertices.length; i++) {
			str = str.concat("point " + i + " " + vertices[i].print() + DELIM);
		}

		str = str.concat("NEWTRIANGLES " + triangles.length + DELIM);

		for (int i = 0; i < triangles.length; i++) {
			str = str.concat("triangle " + i + " " + triangles[i].print()
					+ DELIM);
		}

		str = str.concat("FINISHED");

		System.out.println(str);

		return str;
	}

	public void setVariables() {

		tryChair.mass = true;

		tryChair.SEAT_MIN_HEIGHT = seatMinHeight;
		tryChair.SEAT_MAX_HEIGHT = seatMaxHeight;

		tryChair.BACK_MIN_ANGLE = backMinAngle;
		tryChair.BACK_MAX_ANGLE = backMaxAngle;

		tryChair.LEGS_MIN_ANGLE = legsMinAngle;
		tryChair.LEGS_MAX_ANGLE = legsMaxAngle;

	}

	public void setLegsMinAngle(double legsMinAngle) {

		this.legsMinAngle = legsMinAngle;
	}

	public void setBackMinAngle(double backMinAngle) {

		this.backMinAngle = backMinAngle;
	}

	public void setSeatMinHeight(double seatMinHeight) {

		this.seatMinHeight = seatMinHeight;
	}

	public void setLegsMaxAngle(double legsMaxAngle) {

		this.legsMaxAngle = legsMaxAngle;
	}

	public void setBackMaxAngle(double backMaxAngle) {

		this.backMaxAngle = backMaxAngle;
	}

	public void setSeatMaxHeight(double seatMaxHeight) {

		this.seatMaxHeight = seatMaxHeight;
	}

	public String getLegsAngle() {

		double value1 = tryChair.CURRENT_LEGS_MIN_ANGLE;
		double value2 = tryChair.CURRENT_LEGS_MAX_ANGLE;

		return ("<" + value1 + ">|<" + value2 + ">");
	}

	public String getBackAngle() {

		double value1 = tryChair.CURRENT_BACK_MIN_ANGLE;
		double value2 = tryChair.CURRENT_BACK_MAX_ANGLE;

		return ("<" + value1 + ">|<" + value2 + ">");

	}

	public String getSeatHeight() {

		double value1 = tryChair.CURRENT_SEAT_MIN_HEIGHT;
		double value2 = tryChair.CURRENT_SEAT_MAX_HEIGHT;

		return ("<" + value1 + ">|<" + value2 + ">");

	}
}