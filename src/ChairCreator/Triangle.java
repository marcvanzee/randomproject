package ChairCreator;

import ChairCreator.Calculator;

public class Triangle extends ChairEngine{

    public int[] point = new int[3];
    public Edge[] edge = new Edge[3];
    public Vector normalVector;

    public Vector perpVector;
	
    public Triangle(int point1, int point2, int point3){
        point[0] = point1;
        point[1] = point2;
        point[2] = point3;


	edge[0] = new Edge(point1, point2);
	edge[1] = new Edge(point2, point3);
	edge[2] = new Edge(point3, point1);

        normalVector = Calculator.perpVector(edge[0], edge[1],1);
    }

    public Triangle(Edge edge1, int point1){
        point[0] = edge1.a;
        point[1] = edge1.b;
        point[2] = point1;


	edge[0] = edge1;
	edge[1] = new Edge(edge1.b, point1);
	edge[2] = new Edge(point1, edge1.a);

        normalVector = Calculator.perpVector(edge[0], edge[1],1);
    }

    public String print(){
        String s = new String("_SrfPt " + points[point[0]].print() +" "+ points[point[1]].print() +" "+ points[point[2]].print() +" "+ points[point[0]].print());

	return s;
    }

    public String print(int i){
        String s = new String(points[point[i]].STLPrint());
        return s;
    }
}