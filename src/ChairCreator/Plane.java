/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ChairCreator;

/**
 *
 * @author Home
 */

public class Plane extends ChairEngine{
    public double a, b, c, d;
    public Vector normalVector;

    public Plane (Point3D point1, Point3D point2, Point3D point3){

        Vector vec1 = new Vector(point2, point1);
        Vector vec2 = new Vector(point3, point1);

        normalVector = Calculator.crossProduct(vec1, vec2);

        a = normalVector.dX;
        b = normalVector.dY;
        c = normalVector.dZ;
        d = ( (-point1.x * a) + (-point1.y * b) + (-point1.z * c) );
    }

    public Plane (Edge edge1, Edge edge2){

        Point3D point1 = (edge1.a == edge2.a)? points[edge1.a]: points[edge1.b];

        Vector vec1 = new Vector(edge1);
        Vector vec2 = new Vector(edge2);

        normalVector = Calculator.crossProduct(vec1, vec2);

        a = normalVector.dX;
        b = normalVector.dY;
        c = normalVector.dZ;
        d = ( (-point1.x * a) + (-point1.y * b) + (-point1.z * c) );
    }

    public Plane (Point3D point1, Point3D point2, Vector vec1){

        Vector vec2 = new Vector(point2, point1);

        normalVector = Calculator.crossProduct(vec1, vec2);

        a = normalVector.dX;
        b = normalVector.dY;
        c = normalVector.dZ;
        d = ( (-point1.x * a) + (-point1.y * b) + (-point1.z * c) );
    }

    public Plane (Point3D point1, Vector vec1, Vector vec2){

        normalVector = Calculator.crossProduct(vec1, vec2);

        a = normalVector.dX;
        b = normalVector.dY;
        c = normalVector.dZ;
        d = ( (-point1.x * a) + (-point1.y * b) + (-point1.z * c) );
    }
}
