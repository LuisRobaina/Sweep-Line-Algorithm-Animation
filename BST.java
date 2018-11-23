import java.util.ArrayList;
class Node {
	double value;
	Node left;
	Node rigth;
	boolean IN;
	Node(double v) { this.value = v; IN = true;}
}

class BST {
	Node root;
	// Recursive add method
	void add(double y) {
		root = add(root,y);
	}
	
	Node add(Node x, double value) {
		if(x==null) {
			return new Node(value);
		}
		if(value > x.value) {
			x.rigth = add(x.rigth,value);
		}
		else if(value < x.value) {
			x.left = add(x.left,value);
		}
		return x;
	}
	private void iterate(Node x) {
		if(x==null) {
			return;
		}
		iterate(x.left);
		System.out.println(x.value);
		iterate(x.rigth);
		
	}
	ArrayList<Double> rangeSearch(double lo, double hi) {
		if(lo > hi) {
			lo = lo + hi;
			hi = lo - hi;
			lo = lo - hi;
		}
		System.out.println(lo + " and " + hi);
		System.out.println("-------------------------");
		iterate(root);
		System.out.println("-------------------------");
		ArrayList<Double> onedSearch = new ArrayList<>();
		rangeSearch(onedSearch,root,lo,hi);
		return onedSearch;
	}
	
	private void rangeSearch(ArrayList<Double> list ,Node x , double lo , double hi) {
		
		if(x==null) {
			return;
		}
		rangeSearch(list,x.left,lo,hi);
		if(x.IN && x.value <= hi && x.value >= lo) {
			list.add(x.value);
		}
		rangeSearch(list,x.rigth,lo,hi);
		
	}
	// Romoved code fix.
	void remove(double y) {
	Node x = root;
	
	while(x!=null) {
		if(x.value == y) {
			x.IN = false;
			return;
		}
		if(y > x.value) {
			x = x.rigth;
		}
		else if(y < x.value) {
			x  = x.left; 
		}
	}
	}
}
