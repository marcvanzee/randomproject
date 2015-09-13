/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ChairCreator;

public class Chair extends ChairEngine {
	private static final long serialVersionUID = 1L;
	Back back;
	Legs legs;
	Seat seat;

        static Point3D[] seatSquare;
        static Plane vectorSeatSquare;

        static Point3D[] legsSquare;
        static Plane vectorLegsSquare;

	private Boolean checkChair;

	public Chair() {
        }

        public void build(int a, int b, int c, int d) {


            int[] centerPoints = Calculator.shortestSquare(new int[]{a,b,c,d});

            seatSquare = Calculator.centerPlane(new Point3D[] {points[centerPoints[0]], points[centerPoints[1]], points[centerPoints[2]], points[centerPoints[3]]});
            vectorSeatSquare = new Plane(seatSquare[0], seatSquare[1], seatSquare[2]);

            checkChair = seat(centerPoints);

            if (checkChair) {

                seat.orden(seatSquare, vectorSeatSquare);
                checkChair = legs();
            }

            if (checkChair) {
                
                legsSquare = Calculator.centerPlane(new Point3D [] {points[legs.getPoint(0)],points[legs.getPoint(1)],points[legs.getPoint(2)],points[legs.getPoint(3)]});
                vectorLegsSquare = new Plane(legsSquare[0], legsSquare[1], legsSquare[2]);
             
                checkChair = back();
            }

            if (checkChair) checkChair = stability();

            if (checkChair) checkChair = steepness();

            if (checkChair) checkChair = legAngles();

            if (checkChair) checkChair = seatHeight();

            if (checkChair) checkChair = height();

            //if (checkChair) seats();

            if (checkChair) checkChair = ordenBack();

            if (checkChair) {
                ordenLegs();

                finalTranslate();
                finalRotate();
                rearangePoints();
            }

	}

	public boolean seat(int[] points1) {

            int positive[] = new int[4];
            int negative[] = new int[4];
            int iterP, iterN;

            iterP = iterN = 0;

            for (int i=0; i<NUM_POINTS; i++) {

                if ((i != points1[0]) && (i != points1[1]) && (i != points1[2]) && (i != points1[3])) {

                    double x = Calculator.pointToPlane(vectorSeatSquare, points[i], false);

                    if (x > 0) {
                        if (iterP==4) return false;
                        positive[iterP] = i;
                        iterP++;
                    } else {
                        if (iterN==4) return false;
                        negative[iterN] = i;
                        iterN++;
                    }
                }
            }
            
            if(iterP == 3) return false;

            if (iterP == 4) {
                legs = new Legs ( new int[] {positive[0], positive[1], positive[2], positive[3]} );
                back = new Back ( new int[] {negative[0], negative[1]} );
                seat = new Seat ( points1 );
                
            } else {
                legs = new Legs ( new int[] {negative[0], negative[1], negative[2], negative[3]} );
                back = new Back ( new int[] {positive[0], positive[1]} );
                seat = new Seat ( points1 );
            }

                return true;
	}

	public boolean legs() {

            //nu verdelen we de poten in vier vlakken, drie zou eigenlijk al genoeg zijn... kijken of we dat niet liever willen.

            Plane cut1 = new Plane(seatSquare[0], seatSquare[2], vectorSeatSquare.normalVector);
            Plane cut2 = new Plane(seatSquare[1], seatSquare[3], vectorSeatSquare.normalVector);

            boolean check[] = { false, false, false, false };

            for (int i=0; i<4; i++){

                double x = Calculator.pointToPlane(cut1, points[legs.getPoint(i)], false);
                double y = Calculator.pointToPlane(cut2, points[legs.getPoint(i)], false);

                if (x > 0 && y > 0) {
                    if (check[0] == true) return false;
                    check[0] = true;
                } else if (x > 0 && y < 0) {
                    if (check[1] == true) return false;
                    check[1] = true;
                } else if (x < 0 && y > 0) {
                    if (check[2] == true) return false;
                    check[2] = true;
                } else if (x < 0 && y < 0) {
                    if (check[3] == true) return false;
                    check[3] = true;
		}
            }

            return true;
	}

        public boolean back() {


            Plane cut1 = new Plane(seatSquare[0], seatSquare[2], vectorSeatSquare.normalVector);
            Plane cut2 = new Plane(seatSquare[1], seatSquare[3], vectorSeatSquare.normalVector);

            boolean check[] = { false, false, false, false };

            for (int i=0; i<2; i++){

                double x = Calculator.pointToPlane(cut1, points[back.getPoint(i)], false);
                double y = Calculator.pointToPlane(cut2, points[back.getPoint(i)], false);

                if (x > 0 && y > 0) {
                    check[0] = true;
                } else if (x > 0 && y < 0) {
                    check[1] = true;
                } else if (x < 0 && y > 0) {
                    check[2] = true;
                } else if (x < 0 && y < 0) {
                    check[3] = true;
		}
            }

            if( (check[0] && check[1]) || (check[0] && check[2]) || (check[1] && check[3]) || (check[2] && check[3])){
            return true;
            }

            return false;
	}

	public boolean stability() {

            double seatHeight = 0;
            double legHeight = 0; //termen kloppen eigenlijk niet he...Marc, jij een creatieve inval?

            for (int i=0; i<4; i++) {
                seatHeight = seatHeight + Calculator.pointToPlane(vectorSeatSquare, points[seat.getPoint(i)], true);
                legHeight = legHeight + Calculator.pointToPlane(vectorLegsSquare, points[legs.getPoint(i)], true);
            }

            if ((seatHeight > SEAT_STABILITY) || (legHeight > LEGS_STABILITY)) return false;
            else return true;
            
	}

        public boolean steepness() {

            double angle = Calculator.vecAngle(vectorSeatSquare.normalVector, vectorLegsSquare.normalVector);

            if (angle < SEAT_STEEP) return false;

            return true;
	}

        public boolean legAngles() {

            for (int i = 0; i < 4; i++){
                Double tempAngle = Calculator.vecAngle(vectorLegsSquare.normalVector, new Vector(points[legs.getPoint(i)], points[seat.getPoint(i)]) );

                if (tempAngle > 90) tempAngle = tempAngle - 90;
                else tempAngle = 90 - tempAngle;
                
                if (tempAngle < LEGS_MIN_ANGLE || tempAngle > LEGS_MAX_ANGLE) return false;

                if(i == 0 || tempAngle < CURRENT_LEGS_MIN_ANGLE) CURRENT_LEGS_MIN_ANGLE = tempAngle;
                if(i == 0 || tempAngle > CURRENT_LEGS_MAX_ANGLE) CURRENT_LEGS_MAX_ANGLE = tempAngle;
                    
            }

            return true;
        }

        public boolean seatHeight() {

            double minHeight = 0;
            double maxHeight = 0;

            for (int i=0; i<4; i++) {
                double tempHeight = Calculator.pointToPlane(vectorLegsSquare, seatSquare[i], true);
                if (i == 0 || tempHeight < minHeight) minHeight = tempHeight;
                if (i == 0 || tempHeight > maxHeight) maxHeight = tempHeight;
            }

            if (minHeight < SEAT_MIN_HEIGHT ||maxHeight > SEAT_MAX_HEIGHT) return false;

            CURRENT_SEAT_MIN_HEIGHT = minHeight;
            CURRENT_SEAT_MAX_HEIGHT = maxHeight;

            return true;
	}

	public boolean height() {

            double tempHeight;
            double chairHeight = 0;

            for (int i=0; i<2; i++) {
                tempHeight = Calculator.pointToPlane(vectorLegsSquare, points[back.getPoint(i)], true);
                if (tempHeight > chairHeight) chairHeight = tempHeight;
            }

            if (chairHeight > CHAIR_HEIGHT) return false;

            return true;
	}

        public boolean ordenBack() {

            int x = 0;
            double set = 0;

            int[] seatPoints = seat.getPoints();

            for (int i = 0; i < seatPoints.length; i++){

                double angle1 = 0;
                double angle2 = 0;
                double temp = 0;

                Edge edge1 = new Edge(seatPoints[i], seatPoints[(i+1) %4]);

                Vector vec1 = Calculator.getLeadVector(edge1, points[back.getPoint(0)]);
                Vector vec2 = Calculator.getLeadVector(edge1, points[back.getPoint(1)]);
                Vector vec3 = Calculator.getLeadVector(edge1, Calculator.lineCentre( points[seatPoints[(i+2) %4]], points[seatPoints[(i+3) %4]]) );

                angle1 = Math.abs( Calculator.vecAngle( vec1, vec3 ) );
                angle2 = Math.abs( Calculator.vecAngle( vec2, vec3 ) );

                if (angle1 > BACK_MAX_ANGLE || angle2 > BACK_MAX_ANGLE ||
                    angle1 < BACK_MIN_ANGLE || angle2 < BACK_MIN_ANGLE ){

                    x++;

                    if(x == 4) return false;

                    continue;
                }

                temp = Math.abs(90 - (angle1 + angle2));

                if (set == 0 || temp < set) {
                    set = temp;

                    //hier werken met een kut

                    Plane backPlane = new Plane( Calculator.lineCentre( points[seatPoints[i]], points[seatPoints[(i+1) %4]]),
                                            Calculator.lineCentre( points[back.getPoint(0)], points[back.getPoint(1)]),
                                            Calculator.lineCentre( points[back.getPoint(0)], points[seatPoints[i]]) );


                    Plane cut1 = new Plane(Calculator.lineCentre( points[seatPoints[i]], points[seatPoints[(i+1) %4]]), backPlane.normalVector, vectorSeatSquare.normalVector);


                    double dist1 = Calculator.pointToPlane(cut1, points[back.getPoint(0)], false);
                    double dist2 = Calculator.pointToPlane(cut1, points[back.getPoint(1)], false);

                    double dist3 = Calculator.pointToPlane(cut1, points[seatPoints[i]], false);
                    double dist4 = Calculator.pointToPlane(cut1, points[seatPoints[(i+1) %4]], false);

                    if (dist1 < dist2){

                        if (dist3 > dist4)  back.switchBackPoints();

                    } else if (dist1 > dist2 ) {

                        if (dist3 < dist4)  back.switchBackPoints();

                    }


                    back.setBackToSeat( new int[]{ i , (i+1)%4  } );

                    if(angle1 < angle2){
                        CURRENT_BACK_MIN_ANGLE = angle1;
                        CURRENT_BACK_MAX_ANGLE = angle2;
                    } else {
                        CURRENT_BACK_MIN_ANGLE = angle2;
                        CURRENT_BACK_MAX_ANGLE = angle1;
                    }

                    Point3D[] backSquare = Calculator.centerPlane(new Point3D[] {points[back.getPoint(0)], points[seatPoints[i]], points[seatPoints[(i+1) %4]], points[back.getPoint(1)]});
                    int[] backPoints = new int[] {back.getPoint(0), seatPoints[i], seatPoints[(i+1) %4], back.getPoint(1)};

                    back.orden(backSquare, backPlane, backPoints);

                }
            }


            return true;
        }

        public void ordenLegs() {

            Plane cut1 = new Plane(seatSquare[0], seatSquare[2], vectorSeatSquare.normalVector);
            Plane cut2 = new Plane(seatSquare[1], seatSquare[3], vectorSeatSquare.normalVector);

            int temp[] = new int[4];

            for (int i=0; i<4; i++){

                int location = 0;

                double x = Calculator.pointToPlane(cut1, points[legs.getPoint(i)], false);
                double y = Calculator.pointToPlane(cut2, points[legs.getPoint(i)], false);

                if (x > 0 && y > 0) location = 1;
                else if (x > 0 && y < 0) location = 2;
                else if (x < 0 && y > 0) location = 3;
                else if (x < 0 && y < 0) location = 4;


                int match[] = new int[2];
                int a = 0;
                double cross[] = new double[2];

                for (int j=0; j<4; j++){

                    x = Calculator.pointToPlane(cut1, points[seat.getPoint(j)], false);
                    y = Calculator.pointToPlane(cut2, points[seat.getPoint(j)], false);

                    double z = 0;

                    if(seat.innerPoint && (seat.getPoint(j) == seat.seatCross[0] || seat.getPoint(j) == seat.seatCross[1])){
                        z = Calculator.lineLength(new Point3D(x,0,0), new Point3D(0,y,0));
                        cross[a] = z;
                    }

                    if ( (x > 0 && y > 0) && location == 1 ) {match[a] = seat.getPoint(j); a++;}
                    else if ( (x > 0 && y < 0) && location == 2 ) { match[a] = seat.getPoint(j); a++;}
                    else if ( (x < 0 && y > 0) && location == 3 ) { match[a] = seat.getPoint(j); a++;}
                    else if ( (x < 0 && y < 0) && location == 4 ) { match[a] = seat.getPoint(j); a++;}
                }

                int cornerPoint = -1;

                if (a == 1){
                    temp[i] = match[0];
                    continue;
                } else if (a == 0){
                    if (cornerPoint == -1) temp[i] = -1;
                    else {
                        temp[i] = cornerPoint;
                        continue;
                    }
                } else {
                    if (cross[0] < cross[1]){
                        temp[i] = match[1];
                        cornerPoint = match[0];
                    } else {
                        temp[i] = match[0];
                        cornerPoint = match[1];
                    }
                }

                for (int j = 0; j < 4; j++){
                    if (temp[j] == -1) temp[j] = cornerPoint;
                }
            }

            int[] temp2 = new int[4];
            for(int i = 0; i < 4; i++) temp2[i] = legs.getPoint(i);

            for (int i = 0; i < 4; i++){
                for (int j = 0; j < 4; j++){
                    if (seat.getPoint(i) == temp[j]) legs.setPoint(i, temp2[j]);
                }
            }

        }


	public void seats() {
            boolean check[] = { false, false, false, false };

            for (int j=0; j<4; j++) {
                if (checkChair) break;
                if (points[seat.getPoint(j)].x < 0) {
                    if (points[seat.getPoint(j)].y < 0) {
                        if (check[0] == true) checkChair = false;
                        check[0] = true;
                    } else if (points[seat.getPoint(j)].y > 0) {
                        if (check[1] == true) checkChair = false;
                        check[1] = true;
                        }
                } else if (points[seat.getPoint(j)].x > 0) {
                    if (points[seat.getPoint(j)].y < 0) {
                        if (check[2] == true) checkChair = false;
                        check[2] = true;
                    } else if (points[seat.getPoint(j)].y > 0) {
                        if (check[3] == true) checkChair = false;
                        check[3] = true;
                    }
                }
            }
	}

        public void finalTranslate() {

            Point3D centrePoint = Calculator.centrePoint(points);

            Calculator.translation(points, centrePoint);
            Calculator.translation(seatSquare, centrePoint);
	}

	public void finalRotate() {
            double rValue;
                                                                            // TODO hier ook even naar kijken. Naar die gekke backtoseats en dergelijke. Die kunnen ook direct naar punten wijzen denk ik.
            Point3D[] vectorPlaneBack = Calculator.centerPlane(new Point3D [] { points[back.getPoint(0)] ,points[back.getPoint(1)], points[seat.getPoint(back.getBackToSeat(0))], points[seat.getPoint(back.getBackToSeat(0))] });

            Calculator.translation(vectorPlaneBack, vectorPlaneBack[1]);
            rValue = Calculator.angle(new Point3D(vectorPlaneBack[2].x, vectorPlaneBack[2].y,0), new Point3D(0,0,0), new Point3D(vectorPlaneBack[2].x,0,0));
            rValue = Calculator.rotationAngle(vectorPlaneBack[2].x, vectorPlaneBack[2].y, rValue);

            Calculator.rotation(points, rValue, AXES_XY);
            Calculator.rotation(seatSquare, rValue, AXES_XY);
	}

        public void rearangePoints(){

            Point3D[] oldPoints = points;

            points = new Point3D[oldPoints.length];

            int x = back.getBackToSeat(0);

            points[0] = oldPoints[legs.getPoint(x)];
            points[1] = oldPoints[legs.getPoint((x+1) % 4)];
            points[2] = oldPoints[legs.getPoint((x+2) % 4)];
            points[3] = oldPoints[legs.getPoint((x+3) % 4)];
            points[4] = oldPoints[seat.getPoint(x)];
            points[5] = oldPoints[seat.getPoint((x+1) % 4)];
            points[6] = oldPoints[seat.getPoint((x+2) % 4)];
            points[7] = oldPoints[seat.getPoint((x+3) % 4)];
            points[8] = oldPoints[back.getPoint(0)];
            points[9] = oldPoints[back.getPoint(1)];

            int y = 0;
            x = 0;
            for (int i = 0; i < 4; i++) {
                if (oldPoints[seat.seatCross[0]] == points[4 + i] ){
                    x = 4 + i;
                    crossPointSeat = x;
                }
                if (i < 2 && (oldPoints[back.backCross[0]] == points[8 + i] || oldPoints[back.backCross[1]] == points[8 + i]) ){
                    y = 8 + i;
                    crossPointBack = y;
                }
            }

            points[0].isAttachedTo(new int[]{1, 4, 3});
            points[1].isAttachedTo(new int[]{2, 5, 0});
            points[2].isAttachedTo(new int[]{3, 6, 1});
            points[3].isAttachedTo(new int[]{0, 7, 2});
            points[4].isAttachedTo(new int[]{5, 0, 7, 8});
            points[5].isAttachedTo(new int[]{6, 1, 4, 9});
            points[6].isAttachedTo(new int[]{7, 2, 5});
            points[7].isAttachedTo(new int[]{4, 3, 6});
            points[8].isAttachedTo((backMode == 0)? new int[]{9, 5}:new int[]{9, 5, 4});
            points[9].isAttachedTo((backMode == 0)? new int[]{8, 4}:new int[]{8, 4, 5});


            if (mass == false){
                triangles = new Triangle[4];

                triangles[0] = new Triangle( x, 4 + ((x+1) % 4) , 4 + ((x+2) % 4) );
                triangles[1] = new Triangle( x, 4 + ((x+3) % 4) , 4 + ((x+2) % 4) );
                triangles[2] = new Triangle( 8, 9, (y == 8)? 5 : 4);
                triangles[3] = new Triangle( y , 4, 5 );

                edges = new Edge[4];

                edges[0] = new Edge( 0, 4 );
                edges[1] = new Edge( 1, 5 );
                edges[2] = new Edge( 2, 6 );
                edges[3] = new Edge( 3, 7 );
            }
        }

	public Chair copy () {

		Chair dest = new Chair();

		dest.legs = new Legs(legs.getPoints());
		dest.seat = new Seat(seat.getPoints());
		dest.back = new Back(back.getPoints());

                return dest;
	}

 	public boolean isValid() {
 		return checkChair;
 	}
}