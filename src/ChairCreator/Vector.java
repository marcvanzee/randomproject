/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ChairCreator;

public class Vector extends ChairEngine{
    public double dX, dY, dZ;

    public Vector (){
        dX = 0;
        dY = 0;
        dZ = 0;
    }

    public Vector (double x, double y, double z){
        dX = x;
        dY = y;
        dZ = z;
    }

    public Vector (Point3D point1, Point3D point2){
        dX = point2.x - point1.x;
        dY = point2.y - point1.y;
        dZ = point2.z - point1.z;
    }

    public Vector (int a, int b){
        dX = points[b].x - points[a].x;
        dY = points[b].y - points[a].y;
        dZ = points[b].z - points[a].z;
    }

    public Vector (Point3D point1){
        dX = point1.x;
        dY = point1.y;
        dZ = point1.z;
    }

    public Vector (int a){
        dX = points[a].x;
        dY = points[a].y;
        dZ = points[a].z;
    }

    public Vector (Edge edge){

        dX = points[edge.b].x - points[edge.a].x;
        dY = points[edge.b].y - points[edge.a].y;
        dZ = points[edge.b].z - points[edge.a].z;
    }

    public Vector (Vector v1){

        dX = v1.dX;
        dY = v1.dY;
        dZ = v1.dZ;
    }

    public Vector (Vector v1, Vector v2){

        dX = v1.dX + v2.dX;
        dY = v1.dY + v2.dY;
        dZ = v1.dZ + v2.dZ;
    }

    public Vector (Vector[] vecs){
        dX = 0;
        dY = 0;
        dZ = 0;

        for(int i = 0; i < vecs.length; i++){
            addVector(vecs[i]);
        }
    }

    public Vector (Vector v1, double num){

        dX = v1.dX;
        dY = v1.dY;
        dZ = v1.dZ;

        scale(num);

    }

    public void addVector (Vector vector){
        this.dX = this.dX + vector.dX;
        this.dY = this.dY + vector.dY;
        this.dZ = this.dZ + vector.dZ;
    }

    public void addVectors (Vector[] vecs){
        for(int i = 0; i < vecs.length; i++){
            addVector(vecs[i]);
        }
    }

    public void scale (double num){
        this.dX = this.dX * num;
        this.dY = this.dY * num;
        this.dZ = this.dZ * num;
    }

    public void resize (double num) {

        double fac = num / length();
        scale(fac);

    }

    public double length () {
        double x, y, z ;

        x = dX;
        y = dY;
        z = dZ;

        return  Math.sqrt(x*x + y*y + z*z);
    }

    public String print() {
		return Double.toString(this.dX) + "," + Double.toString(this.dY) + "," + Double.toString(this.dZ) + " ";
	}
}
