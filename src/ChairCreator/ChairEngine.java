/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ChairCreator;

public class ChairEngine {

    public static String[] messages = new String[3];

	static int NUM_POINTS = 10;
	int DIMX = 2;
	int DIMY = 2;
	int DIMZ = 2;

	static double SEAT_STABILITY = 0.5;
        static double SEAT_STEEP = 30;
        static double SEAT_MIN_HEIGHT = 0.5;
        static double SEAT_MAX_HEIGHT = 1.2;

        static double CURRENT_SEAT_MIN_HEIGHT;
        static double CURRENT_SEAT_MAX_HEIGHT;

        static double BACK_MIN_ANGLE = 60;
        static double BACK_MAX_ANGLE = 120;

        static double CURRENT_BACK_MIN_ANGLE;
        static double CURRENT_BACK_MAX_ANGLE;

	static double LEGS_STABILITY = 0.2;
        static double LEGS_MIN_ANGLE = 30;
        static double LEGS_MAX_ANGLE = 90;

        static double CURRENT_LEGS_MIN_ANGLE;
        static double CURRENT_LEGS_MAX_ANGLE;

	static double CHAIR_HEIGHT = 1.8;

	int NUMBER_OF_CHAIRS = 1;

	static int AXES_XY = 0;
	static int AXES_XZ = 1;
	static int AXES_YZ = 2;

        static Point3D[] points;
        static Triangle[] triangles;
        static Edge[] edges;

        int crossPointSeat;
        int crossPointBack;
        
        public static final double THICKNESS = 0.1;
        public static final double FACTOR = 0.2;

        static boolean mass;

        static int thicknessMode = 2;
        static int backMode = 1;
        static int backSeatMode = 1;
        
	public boolean go () {
            points = new Point3D[NUM_POINTS];
            triangles = null;
            edges = null;

            generateRandomPoints();
            Chair chair = generateChair();

            if (chair != null) {
                return true;
            } else {
                return false;
            }
	}
	public void 	generateRandomPoints() {

            for (int i=0; i<points.length; i++) {
                points[i] = new Point3D(Calculator.randomDouble(DIMX), Calculator.randomDouble(DIMY), Calculator.randomDouble(DIMZ));
            }
	}

	public Chair generateChair() {
            Chair chair = new Chair();
            for (int a=0; a<points.length-3; a++) {
                for (int b=(a+1); b<points.length-2; b++) {
                    for (int c=(b+1); c<points.length-1; c++) {
                        for (int d=(c+1); d<points.length; d++) {

                            //eerst in zijn totaal controlleren of de random punten tot een stoel kunnen komen.
                            chair.build(a, b, c, d);
                            if (chair.isValid()) return chair;
                        }
                    }
                }
            }
            return null;
	}
}
