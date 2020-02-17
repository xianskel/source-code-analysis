package maini;

import java.util.PriorityQueue;

public class runstar {

	
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		int [][] desired = {{1,2,3}, {4,5,6}, {7,8,-1}};
		//int [][] puzzle  = {{6,2,3}, {1,4,8}, {5,7,-1}};
		int [][] puzzle  = {{8,4,3}, {1,7,8}, {5,7,-1}};
		
		int lev = 0;
		puzzle p = new puzzle(astar.converStateToString(puzzle),"root", puzzle, 0, astar.fitness(desired, puzzle));
		
		astar.PuzzleSolvingObj(p, desired);
		
		
		PriorityQueue <String> q = new PriorityQueue<String> ((a, b) -> a.length()-b.length());
		
		q.add("fars4had");
		q.add("fa2rsha32d");
		
		q.add("farshad");
		q.add("farshad2");
		q.add("frad");
		q.add("far2shad");
		
		System.out.println(q.poll());
		System.out.println(q.poll());
		System.out.println(q.poll());
		System.out.println(q.poll());
		System.out.println(q.poll());
		
		
	}*/
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int [][] desired = {{1,2,3}, {4,5,6}, {7,8,-1}};
		int [][] puzzle  = {{-1,1,3}, {4,2,5}, {7,8,6}};
		//int [][] desired = {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}, {13,14,15,-1}};
		//int [][] puzzle  = {{-1, 1, 2,3}, {5,6,7,4}, {9,10,11,8}, {13,14,15,12}};
		//int [][] desired = {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}, {13,14,15,-1}};
		//int [][] desired = {{1,2,3}, {4,5,6}, {7,8,-1}};
		
		//int [][] puzzle  = {{2,9,3,4}, {5,6,7,8}, {-1,10,11,12}, {1,13,14,15}};
		//int [][] puzzle  = {{6,2,3}, {1,4,8}, {7,5,-1}};
		int lv = 0;
		
		puzzle p = new puzzle(astar.converStateToString(puzzle),	"root", puzzle, 0, astar.fitness(desired, puzzle));
		astar.PuzzleSolvingObj(p, desired);
	}
	
	

}
