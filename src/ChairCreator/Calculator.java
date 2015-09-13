/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ChairCreator;

public class Calculator extends ChairEngine {

	private static final long serialVersionUID = 1L;

	public static void transformation(Point3D transformPoints[],
			Point3D vectorPlaneSeat[]) {
		// zet het snijvlak (square) horizontaal
		// transformeer alle overige punten en sla ze op in transpoints

		double rValue;
		translation(transformPoints, vectorPlaneSeat[0]);
		translation(vectorPlaneSeat, vectorPlaneSeat[0]);

		// vectorPlaneSeat0 = (0,0,0)
		// nu moet vectorPlaneSeat1.y = 0 en de rest meeroteren
		// de z-as laten we buiten beschouwing omdat die niet meedraait
		// eerst de rotatiehoek berekenen
		rValue = angle(new Point3D(vectorPlaneSeat[1].x, vectorPlaneSeat[1].y,
				0), new Point3D(0, 0, 0), new Point3D(vectorPlaneSeat[1].x, 0,
				0));
		rValue = rotationAngle(vectorPlaneSeat[1].x, vectorPlaneSeat[1].y,
				rValue);

		// alle punten om deze hoek roteren vanaf 0,0
		rotation(vectorPlaneSeat, rValue, AXES_XY);
		rotation(transformPoints, rValue, AXES_XY);

		// vectorPlaneSeat0 = (0,0,0)
		// vectorPlaneSeat1.y = (?,0,?)
		// nu moet vectorPlaneSeat1.z = 0 en de rest meeroteren en y buiten
		// beschouwing gelaten worden
		rValue = angle(new Point3D(vectorPlaneSeat[1].x, 0,
				vectorPlaneSeat[1].z), new Point3D(0, 0, 0), new Point3D(
				vectorPlaneSeat[1].x, 0, 0));
		rValue = rotationAngle(vectorPlaneSeat[1].z, vectorPlaneSeat[1].x,
				rValue);

		// alle punten om deze hoek roteren vanaf 0,0
		rotation(vectorPlaneSeat, rValue, AXES_XZ);
		rotation(transformPoints, rValue, AXES_XZ);

		// vectorPlaneSeat0 = (0,0,0)
		// vectorPlaneSeat1 = (0,0,?)
		// nu moet vectorPlaneSeat2.z = 0 en de rest meeroteren en x buiten
		// beschouwing
		rValue = angle(new Point3D(0, vectorPlaneSeat[2].y,
				vectorPlaneSeat[2].z), new Point3D(0, 0, 0), new Point3D(0,
				vectorPlaneSeat[2].y, 0));
		rValue = rotationAngle(vectorPlaneSeat[2].z, vectorPlaneSeat[2].y,
				rValue);

		// alle punten om deze hoek roteren vanaf 0,0
		rotation(vectorPlaneSeat, rValue, AXES_YZ);
		rotation(transformPoints, rValue, AXES_YZ);

		Point3D center = new Point3D();
		center.x = (vectorPlaneSeat[0].x + vectorPlaneSeat[1].x
				+ vectorPlaneSeat[2].x + vectorPlaneSeat[3].x) / 4;
		center.y = (vectorPlaneSeat[0].y + vectorPlaneSeat[1].y
				+ vectorPlaneSeat[2].y + vectorPlaneSeat[3].y) / 4;
		center.z = (vectorPlaneSeat[0].z + vectorPlaneSeat[1].z
				+ vectorPlaneSeat[2].z + vectorPlaneSeat[3].z) / 4;

		translation(transformPoints, center);
		rotation(transformPoints, 45, AXES_XY);
	}

	public static void translation(Point3D[] translatePoints,
			Point3D translation) {
		double x, y, z;

		x = translation.x;
		y = translation.y;
		z = translation.z;

		for (int i = 0; i < translatePoints.length; i++) {
			translatePoints[i].x = translatePoints[i].x - x;
			translatePoints[i].y = translatePoints[i].y - y;
			translatePoints[i].z = translatePoints[i].z - z;
		}
	}

	public static void rotation(Point3D[] rotationPoints, double rValue,
			int Axes) {

		rValue = Math.toRadians(rValue);

		switch (Axes) {
		case 0: // front view

			for (int i = 0; i < rotationPoints.length; i++) {
				double x = rotationPoints[i].x;
				double y = rotationPoints[i].y;
				rotationPoints[i].x = (double) (x * Math.cos(rValue) - y
						* Math.sin(rValue));
				rotationPoints[i].y = (double) (x * Math.sin(rValue) + y
						* Math.cos(rValue));
			}
			break;
		case 1: // top view

			for (int i = 0; i < rotationPoints.length; i++) {
				double x = rotationPoints[i].x;
				double z = rotationPoints[i].z;
				rotationPoints[i].x = (double) (x * Math.cos(rValue) - z
						* Math.sin(rValue));
				rotationPoints[i].z = (double) (x * Math.sin(rValue) + z
						* Math.cos(rValue));
			}
			break;
		case 2: // side view

			for (int i = 0; i < rotationPoints.length; i++) {
				double y = rotationPoints[i].y;
				double z = rotationPoints[i].z;
				rotationPoints[i].y = (double) (y * Math.cos(rValue) - z
						* Math.sin(rValue));
				rotationPoints[i].z = (double) (y * Math.sin(rValue) + z
						* Math.cos(rValue));
			}
			break;
		}
	}

	public static Point3D[] shortestSquare(Point3D[] points1) {

		Point3D tempPoint = new Point3D();

		double test1 = lineLength(points1[0], points1[1])
				+ lineLength(points1[1], points1[2])
				+ lineLength(points1[2], points1[3])
				+ lineLength(points1[3], points1[0]);
		double test2 = lineLength(points1[0], points1[1])
				+ lineLength(points1[1], points1[3])
				+ lineLength(points1[2], points1[3])
				+ lineLength(points1[2], points1[0]);
		double test3 = lineLength(points1[0], points1[3])
				+ lineLength(points1[1], points1[3])
				+ lineLength(points1[1], points1[2])
				+ lineLength(points1[2], points1[0]);

		if (test1 < test2 && test1 < test3) {
			return points1;
		} else if (test3 < test1 && test3 < test2) {
			tempPoint = points1[1];
			points1[1] = points1[2];
			points1[2] = tempPoint;
		} else if (test2 < test1 && test2 < test1) {
			tempPoint = points1[2];
			points1[2] = points1[3];
			points1[3] = tempPoint;
		}

		return points1;
	}

	public static int[] shortestSquare(int[] pointers) {

		Point3D tempPoint = new Point3D();
		int tempPointer;
		Point3D[] points1 = new Point3D[] { points[pointers[0]],
				points[pointers[1]], points[pointers[2]], points[pointers[3]] };

		double test1 = lineLength(points1[0], points1[1])
				+ lineLength(points1[1], points1[2])
				+ lineLength(points1[2], points1[3])
				+ lineLength(points1[3], points1[0]);
		double test2 = lineLength(points1[0], points1[1])
				+ lineLength(points1[1], points1[3])
				+ lineLength(points1[2], points1[3])
				+ lineLength(points1[2], points1[0]);
		double test3 = lineLength(points1[0], points1[3])
				+ lineLength(points1[1], points1[3])
				+ lineLength(points1[1], points1[2])
				+ lineLength(points1[2], points1[0]);

		if (test1 < test2 && test1 < test3) {
			return pointers;
		} else if (test3 < test1 && test3 < test2) {
			tempPointer = pointers[1];
			pointers[1] = pointers[2];
			pointers[2] = tempPointer;
		} else if (test2 < test1 && test2 < test1) {
			tempPointer = pointers[2];
			pointers[2] = pointers[3];
			pointers[3] = tempPointer;
		}

		return pointers;
	}

	public static Point3D[] centerPlane(Point3D[] points) {
		Point3D[] centerPlaneSeat = new Point3D[4];

		for (int i = 0; i < 4; i++) {
			centerPlaneSeat[i] = lineCentre(points[i], points[(i + 1) % 4]);
		}

		return centerPlaneSeat;
	}

	public static Vector perpVector(Vector vec1, Vector vec2, double num) {

		Vector normalVector = crossProduct(vec1, vec2);

		Vector vector = new Vector(normalVector.dX, normalVector.dY,
				normalVector.dZ);

		vector.resize(num);

		return vector;
	}

	public static Vector perpVector(Edge edge1, Edge edge2, double num) {

		Vector vec1 = new Vector(edge1);
		Vector vec2 = new Vector(edge2);

		return perpVector(vec1, vec2, num);
	}

	public static Vector perpVector(Point3D point1, Point3D point2,
			Point3D point3, double num) {

		Vector vec1 = new Vector(point1, point2);
		Vector vec2 = new Vector(point2, point3);

		return perpVector(vec1, vec2, num);
	}

	public static Point3D lineCentre(Point3D point1, Point3D point2) {
		Point3D centrePoint = new Point3D();

		centrePoint.x = (point1.x + point2.x) / 2;
		centrePoint.y = (point1.y + point2.y) / 2;
		centrePoint.z = (point1.z + point2.z) / 2;

		return centrePoint;
	}

	public static Point3D offsetPoint(Point3D p1, Point3D p2, Point3D p3,
			double length) {

		Vector leadVector = getLeadVector(p1, p2, p3);

		// DIKTE GEDEELD DOOR SCHUINE ZIJDE OM VERMENIGVULDIGINGSFACTOR TE
		// BEPALEN
		double fac = length / leadVector.length();

		// VERPLAATSING DELTA X EN Y BEPALEN AAN DE HAND VAN FAC. EN DELTA
		// LOODLIJN
		double pX = p1.x + fac * leadVector.dX;
		double pY = p1.y + fac * leadVector.dY;
		double pZ = p1.z + fac * leadVector.dZ;

		Point3D point = new Point3D(pX, pY, pZ);

		return point;
	}

	public static Point3D intersectPoint(Point3D point1, Vector vector1,
			Point3D point2, Vector vector2) {

		// a (V1 X V2) = (P2 - P1) X V2

		Point3D point = new Point3D((point2.x - point1.x),
				(point2.y - point1.y), (point2.z - point1.z));

		Vector pointCrossVec = crossProduct(point, vector2);
		Vector vecCrossVec = crossProduct(vector1, vector2);

		double a = pointCrossVec.dX / vecCrossVec.dX;

		point = new Point3D((point1.x - point2.x), (point1.y - point2.y),
				(point1.z - point2.z));

		pointCrossVec = crossProduct(point, vector1);
		vecCrossVec = crossProduct(vector2, vector1);

		double b = pointCrossVec.dX / vecCrossVec.dX;

		Point3D intersectPoint1 = new Point3D((point1.x + (a * vector1.dX)),
				(point1.y + (a * vector1.dY)), (point1.z + (a * vector1.dZ)));
		Point3D intersectPoint2 = new Point3D((point2.x + (b * vector2.dX)),
				(point2.y + (b * vector2.dY)), (point2.z + (b * vector2.dZ)));

		double roundedp1x = (Math.round(intersectPoint1.x * 1000000.0)) / 1000000.0;
		double roundedp1y = (Math.round(intersectPoint1.y * 1000000.0)) / 1000000.0;
		double roundedp1z = (Math.round(intersectPoint1.z * 1000000.0)) / 1000000.0;
		double roundedp2x = (Math.round(intersectPoint2.x * 1000000.0)) / 1000000.0;
		double roundedp2y = (Math.round(intersectPoint2.y * 1000000.0)) / 1000000.0;
		double roundedp2z = (Math.round(intersectPoint2.z * 1000000.0)) / 1000000.0;

		if ((roundedp1x != roundedp2x || roundedp1y != roundedp2y || roundedp1z != roundedp2z)) {
			return null;
		}

		return intersectPoint1;
	}

	public static Point3D intersectPoint(Point3D point1, Vector vec1,
			Plane plane1) {

		double top = ((plane1.a * point1.x) + (plane1.b * point1.y)
				+ (plane1.c * point1.z) + plane1.d);
		double bottom = ((plane1.a * vec1.dX) + (plane1.b * vec1.dY) + (plane1.c * vec1.dZ));

		double r = top / bottom;

		Vector vec2 = new Vector(vec1, -r);

		Point3D intersectPoint = new Point3D(point1, vec2);

		return intersectPoint;
	}

	public static Point3D intersectPoint(Point3D point1, Plane plane1) {

		Vector vec1 = plane1.normalVector;

		return intersectPoint(point1, vec1, plane1);

	}

	public static Point3D centrePoint(Point3D[] pointArray) {

		Point3D centrePoint = new Point3D(0, 0, 0);

		for (int i = 0; i < pointArray.length; i++) {
			centrePoint.addPoint(pointArray[i]);
		}

		centrePoint.scalePoint((double) 1 / pointArray.length);

		return centrePoint;
	}

	public static Vector crossProduct(Vector vec1, Vector vec2) {

		double dX = (vec1.dZ * vec2.dY) - (vec1.dY * vec2.dZ);
		double dY = (vec1.dZ * vec2.dX) - (vec1.dX * vec2.dZ);
		double dZ = (vec1.dY * vec2.dX) - (vec1.dX * vec2.dY);

		return new Vector(dX, -dY, dZ);
	}

	public static Vector crossProduct(Point3D point, Vector vec) {

		double dX = (point.z * vec.dY) - (point.y * vec.dZ);
		double dY = (point.z * vec.dX) - (point.x * vec.dZ);
		double dZ = (point.y * vec.dX) - (point.x * vec.dY);

		return new Vector(dX, -dY, dZ);
	}

	public static double dotProduct(Vector vec1, Vector vec2) {

		double dot = vec1.dX * vec2.dX + vec1.dY * vec2.dY + vec1.dZ * vec2.dZ;

		return dot;
	}

	public static Vector correctPerpVector(Edge shared, Edge edge1, Edge edge2,
			Vector v1, Vector v2) {
		int p1 = (edge1.a == shared.a || edge1.a == shared.b) ? edge1.b
				: edge1.a;
		int p2 = (edge2.a == shared.a || edge2.a == shared.b) ? edge2.b
				: edge2.a;

		return correctPerpVector(shared, p1, p2, v1, v2);
	}

	public static Vector correctPerpVector(Edge edge1, int p1, int p2,
			Vector perp1, Vector perp2) {

		Vector vec1 = getLeadVector(edge1, points[p1]);
		Vector vec2 = getLeadVector(edge1, points[p2]);

		double vecAngle1 = vecAngle(vec1, vec2);

		Point3D tempPoint1 = new Point3D(perp2, 1);
		Point3D tempPoint2 = new Point3D(perp1, 1);

		Point3D intersVecOnVec = intersectPoint(tempPoint1, vec2, tempPoint2,
				vec1);

		double vecAngle2 = angle(tempPoint1, intersVecOnVec, tempPoint2);

		vecAngle1 = Math.round((vecAngle1 * 10000000.0)) / 10000000.0;
		vecAngle2 = Math.round((vecAngle2 * 10000000.0)) / 10000000.0;

		if (vecAngle1 != vecAngle2) {
			perp2.scale(-1.0);
			// hier is geen nacontrole...
			return perp2;
		} else {
			return perp2;
		}
	}

	public static Point3D getLeadPoint(Point3D p1, Point3D p2, Point3D p3) {

		// de lengte van p2 tot het loodpunt
		double leadDist = (lineLength(p2, p3) * Math.cos(Math.toRadians(angle(
				p1, p2, p3))));

		// ORIGINELE DELTA X EN Y BEPALEN VAN ZIJDE DRIEHOEK.
		Vector original = new Vector(p2, p1);

		// gebruik de verhouding p2-loodpunt / p2-p1 om de delta x en y en z van
		// het loodpunt te weten te komen
		original.scale(leadDist / lineLength(p1, p2));

		// defineer het loodpunt aan de hand van p2
		Point3D leadPoint = new Point3D(p2, original);

		return leadPoint;
	}

	public static double getLeadLength(Point3D p1, Point3D p2, Point3D p3) {

		Point3D leadPoint = getLeadPoint(p1, p2, p3);

		return lineLength(leadPoint, p3);
	}

	public static Vector getLeadVector(Edge edge1, Point3D p3) {

		return getLeadVector(points[edge1.a], points[edge1.b], p3);
	}

	public static Vector getLeadVector(Vector vec1, Vector vec2) {

		return getLeadVector(new Point3D(0, 0, 0), new Point3D(vec1.dX,
				vec1.dY, vec1.dZ), new Point3D(vec2.dX, vec2.dY, vec2.dZ));
	}

	public static Vector getLeadVector(Point3D p1, Point3D p2, Point3D p3) {

		Point3D leadPoint = getLeadPoint(p1, p2, p3);

		Vector leadVector = new Vector(p3, leadPoint);

		return leadVector;
	}

	public static double lineLength(Point3D point1, Point3D point2) {
		double x, y, z;

		x = point1.x - point2.x;
		y = point1.y - point2.y;
		z = point1.z - point2.z;

		return Math.sqrt(x * x + y * y + z * z);
	}

	public static double lineLength(Point2D point1, Point2D point2) {
		double x, y;

		x = point1.x - point2.x;
		y = point1.y - point2.y;

		return Math.sqrt(x * x + y * y);
	}

	public static double lineLength(Point3D[] points1) {
		double x = 0;

		for (int i = 0; i < points1.length; i++) {
			x = x + lineLength(points1[i], points1[(i + 1) % points1.length]);
		}

		return x;
	}

	public static double pointToPlane(Plane plane, Point3D point, boolean abs) {

		double x;

		if (abs == true) {
			x = Math.abs((plane.a * point.x) + (plane.b * point.y)
					+ (plane.c * point.z) + plane.d);
		} else {
			x = (plane.a * point.x) + (plane.b * point.y) + (plane.c * point.z)
					+ plane.d;
		}
		double y = Math.sqrt((plane.a * plane.a) + (plane.b * plane.b)
				+ (plane.c * plane.c));

		return x / y;
	}

	public static double triangleArea(Point3D point1, Point3D point2,
			Point3D point3) {

		double base = lineLength(point1, point2);

		double length = getLeadLength(point1, point2, point3);

		double square = (base * length) / 2;

		return square;
	}

	public static double pyramideVolume(Point3D point1, Point3D point2,
			Point3D point3, Point3D point4) {

		double base = triangleArea(point1, point2, point3);

		Plane plane = new Plane(point1, point2, point3);

		double height = pointToPlane(plane, point4, true);

		return (base * height) / 3;

	}

	public static double angle(Point3D point1, Point3D point2, Point3D point3) {
		double a, b, c;
		double x;

		a = lineLength(point1, point2);
		b = lineLength(point2, point3);
		c = lineLength(point1, point3);

		x = (a * a + b * b - c * c) / (2 * a * b);

		return Math.acos(x) * 180 / Math.PI;

	}

	public static double vecAngle(Vector v1, Vector v2) {

		return angle(new Point3D(v1), new Point3D(0.0, 0.0, 0.0), new Point3D(
				v2));

	}

	public static double clockAngle(Vector v1, Vector v2) {

		double perp_dot_product = -v1.dY * v2.dX + v1.dX * v2.dY;
		double dot = v1.dX * v2.dX + v1.dY * v2.dY;

		return Math.atan2(perp_dot_product, dot) * 180 / Math.PI;
	}

	public static double rotationAngle(double pointA, double pointB,
			double rValue) {
		if (pointA > 0) {
			if (pointB > 0) {
				rValue = 360 - rValue;
			}
		} else if (pointA < 0) {
			if (pointB <= 0) {
				rValue = 180 - rValue;
			} else {
				rValue = 180 + rValue;
			}
		}

		return rValue;
	}

	public static double randomDouble(double num) {
		return Math.random() * num;
	}

	public static int[][] permutation(int offset, int[] input, int[][] output,
			int length) { // by Jesper Nordenberg addapted by OTN
		if (input.length - offset == length) {

			int i = 0;
			while (!(output[i][0] == 0 && output[i][1] == 0)) {
				i++;
			}
			for (int j = 0; j < output[i].length; j++) {
				output[i][j] = input[j];
			}

			return output;
		}

		int a = input[offset];

		for (int i = offset; i < input.length; i++) {
			int b = input[i];
			input[i] = a;
			input[offset] = b;

			output = permutation(offset + 1, input, output, length);

			input[i] = b;
		}

		input[offset] = a;

		return output;
	}

	public static boolean onSquare(Point3D[] square1, Point3D point1) { // niet
																		// doorgetest,
																		// dus
																		// niet
																		// zeker
																		// was
																		// van
																		// zijn
																		// werken.

		LOOP1: for (int i = 0; i < 2; i++) {

			Point3D interPoint = intersectPoint(square1[i], new Vector(
					square1[i], square1[(i + 1) % 4]), square1[(i + 2) % 4],
					new Vector(square1[(i + 2) % 4], square1[(i + 3) % 4]));

			if (interPoint != null
					&& onLine(square1[i], square1[(i + 1) % 4], interPoint)) {
				Point3D tempPoint = square1[i];
				square1[i] = square1[(i + 3) & 4];
				square1[(i + 3) & 4] = tempPoint;

				break LOOP1;
			}
		}

		for (int i = 0; i < 4; i++) {

			Vector vec1 = new Vector(square1[i], square1[(i + 1) % 4]);

			Point3D tempPoint = new Point3D();

			for (int j = 0; j < 4; j++) {

				Point3D interPoint = intersectPoint(point1, vec1, square1[(j)],
						new Vector(square1[j], square1[(j + 1) % 4]));

				if (interPoint != null
						&& onLine(square1[j], square1[(j + 1) % 4], interPoint)) {
					if (tempPoint.x == 0) {
						tempPoint = interPoint;
					} else if (onLine(tempPoint, interPoint, point1)) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public static boolean onLine(Point3D point1, Point3D point2, Point3D point3) {

		double lineLength1 = lineLength(point1, point2);
		double lineLength2 = lineLength(point1, point3);
		double lineLength3 = lineLength(point3, point2);

		if (lineLength3 + lineLength2 == lineLength1) {
			return true;
		}

		return false;
	}
}
