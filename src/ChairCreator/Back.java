/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ChairCreator;

public class Back extends ChairEngine {
    private static final long serialVersionUID = 1L;
    private int[] backPoints;
    private static int[] backToSeat = new int[2];
    public int[] backCross;
    

    public Back() {
    }

    public Back(int[] p) {
        backPoints = p;
    }

    public int getPoint(int num) {
        return backPoints[num];
    }

    public void switchBackPoints(){
        int x = backPoints[0];
        backPoints[0] = backPoints[1];
        backPoints[1] = x;
    }

    public void setLengthBackToSeat(int num){
        backToSeat = new int[num];
    }

    public int getBackToSeat(int num) {
        return backToSeat[num];
    }
    public void setBackToSeat(int num, int x) {
        this.backToSeat[num] = x;
    }

    public void setBackToSeat(int[] nums) {
        this.backToSeat[0] = nums[0];
        this.backToSeat[1] = nums[1];
    }
    public int[] getPoints() {
        return backPoints;
    }

     public void orden(Point3D[] square, Plane vectorSquare, int[] bPoints) {

        for (int i = 0; i < 4; i++){

            Plane cut1 = new Plane(square[i], square[(i + 1) %4], vectorSquare.normalVector);
            int min = 0;
            int plus = 0;

            int[] minArray = new int[4];
            int[] plusArray = new int[4];

            for (int j = 0; j < 4; j++){

                double direction = Calculator.pointToPlane(cut1, points[bPoints[j]], false);

                if (direction < 0) {
                    minArray[min] = bPoints[j];
                    min++;
                }
                if (direction > 0) {
                    plusArray[plus] = bPoints[j];
                    plus++;
                }
            }

            if(min == plus) {
                double direction = Calculator.pointToPlane(cut1, square[(i + 2) %4], false);

                if (direction < 0) backCross = new int[]{minArray[0], minArray[1]};
                if (direction > 0) backCross = new int[]{plusArray[0], plusArray[1]};

                return;

            } else {

                backCross = new int[]{bPoints[0], bPoints[2]};
            }
        }
    }

}