import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;
class Square {
	double x;
	double y;
	
	Square(double x,double y){
		this.x= x;
		this.y= y;
	}
}
class Line implements Comparable<Line>{
	double x1;
	double x2;
	double y1;
	double y2;
	char VorH;
	
	public int compareTo(Line b) {
		if(this.x1 > b.x1) return 1;
		if(this.x1 < b.x1) return -1;
		return 0;
	}
	
	@Override
	public boolean equals(Object that) {
		if(this == that) return true;
		if(that == null) return false;
		
		if(this.x1 != ((Line)that).x1) return false;
		if(this.x2 != ((Line)that).x2) return false;
		if(this.y1 != ((Line)that).y1) return false;
		if(this.y2 != ((Line)that).y2) return false;
		
		return true;
	} 
	
	Line(double x1,double x2,double y1,double y2, char VorH){
		
		if(x2 < x1) {
			x2 = x2+x1;
			x1 = x2 - x1;
			x2 = x2 - x1;
		}
		
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.VorH = VorH;
	}
}

class Point {
	double x;
	double y;
	
	Point(double x, double y){
		this.x = x;
		this.y = y;
	}
}

public class Animation {

	public static void main(String[] args) {
		BST ycoordinatesTree = new BST();
		
		// Generate random lines
		ArrayList<Line> lines = new ArrayList<>();
		PriorityQueue<Line> X1PQ = new PriorityQueue<>();
		PriorityQueue<Double> X2PQ = new PriorityQueue<>();
		
		int N = 50;
		// Horizontal Lines.
		for(int i = 0 ; i<N;i++) {
			double y = Math.random();	
			double x1 = Math.random();
			double x2 = Math.random();
			Line line = new Line(x1,x2,y,y,'H');
			if(!lines.contains(line)){
				lines.add(line);
				X1PQ.add(line);
				X2PQ.add(line.x2);
			}
			else {
				i--;
			}
			}
		// Vertical Lines.
		for(int i = 0 ; i<N;i++) {
			double x = Math.random();
			double y1 = Math.random();
			double y2 = Math.random();
			Line line = new Line(x,x,y1,y2, 'V');
			if(!lines.contains(line)){
				lines.add(line);
				X1PQ.add(line);
			}
			else {
				i--;
			}
		}
		ArrayList<Point> marks = new ArrayList<>();
		ArrayList<Square> square = new ArrayList<>();
		double x = 0;
		while(x<1){
			for(int i = 0 ; i<1;i++) {
				StdDraw.clear();
				for(Line l : lines) {
					StdDraw.line(l.x1,l.y1,l.x2,l.y2);
					StdDraw.filledCircle(l.x1,l.y1,0.005);
					StdDraw.filledCircle(l.x2,l.y2,0.005);
				}
				for(Point p : marks) {
					StdDraw.filledCircle(p.x,p.y,0.005);
				}
				for(Square a : square) {
					StdDraw.setPenColor(Color.RED);
					StdDraw.square(a.x, a.y, 0.01);
				}
				StdDraw.setPenColor(Color.BLACK);
				StdDraw.line(x,0,x,1);
				//Check for empty Priority Queue.
				Line X1 = X1PQ.poll();
				double X2 = X2PQ.poll();
				
				//System.out.println("Looking for " + Math.round((X1.x1*1000))/1000.0 + " at " + x);
				
				if(Math.round(x*1000)/1000.0 == Math.round(X1.x1 * 1000)/1000.0){
					if(X1.VorH == 'V') { // Found a vertical line	
					// do range search
						ArrayList<Double> y = ycoordinatesTree.rangeSearch(X1.y1,X1.y2);
						if( y != null && !y.isEmpty() ) {
							System.out.println("RANGE -----");
							for(double ycoordinate : y) {
								System.out.print(ycoordinate + ", ");
								square.add(new Square(x,ycoordinate));
							}
						}
						else {
							System.out.print("Empty");
						}
						StdDraw.setPenColor(Color.BLACK);
					}
				else {
						ycoordinatesTree.add(X1.y1);	
					}
				}
				
				else {
					X1PQ.add(X1);
				}
				// Find if x is at an end point of a horizontal line.
				if(Math.round(x*1000)/1000.0 == Math.round(X2 * 1000)/1000.0){
					for(Line p : lines) {
						if(p.x2 == X2) {
							ycoordinatesTree.remove(p.y1);
							break;
						}
					}
				}
				else {
					// Put back on Priority queue
					X2PQ.add(X2);
				}
				
				x+=0.0001;
			StdDraw.show(1);
		}
	}
	}
}
