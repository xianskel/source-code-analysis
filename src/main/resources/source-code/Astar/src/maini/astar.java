package maini;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class astar {
	
	static Map<String, puzzle> mS  = new HashMap<String, puzzle> (); 
	static Map<String, puzzle> OS11 = new HashMap<String, puzzle> (); 
	
	
public static void PuzzleSolvingObj(puzzle newState, int [][] desired) {
	
	//String hh = newState.getCode();
	
	mS.put(newState.getCode(), newState);

	int [][] new_puzzle1 = move(1, newState.getState());
	int [][] new_puzzle2 = move(2, newState.getState());
	int [][] new_puzzle3 = move(3, newState.getState());
	int [][] new_puzzle4 = move(4, newState.getState());
	
	boolean stop = false;
	if(new_puzzle1.length >2) {
		int cost1 = fitness(desired, new_puzzle1);
		puzzle p1 = new puzzle(converStateToString(new_puzzle1),	newState.getCode(), new_puzzle1, newState.getLevel()+1, cost1);
		mS.put(p1.getCode(), p1);
		OS11.put(p1.getCode(), p1);
		if(cost1 == 0) {
			stop = true;
			tracBack(converStateToString(new_puzzle1));
		}
	}
	
	if(new_puzzle2.length >2) {
		int cost2 = fitness(desired, new_puzzle2);
		puzzle p2 = new puzzle(converStateToString(new_puzzle2),	newState.getCode(), new_puzzle2, newState.getLevel()+1, cost2);
		mS.put(p2.getCode(), p2);
		OS11.put(p2.getCode(), p2);
		if(cost2 == 0) {
			stop = true;
			tracBack(converStateToString(new_puzzle2));
		}
	}
	
	if(new_puzzle3.length >2) {
		int cost3 = fitness(desired, new_puzzle3);
		puzzle p3 = new puzzle(converStateToString(new_puzzle3),	newState.getCode(), new_puzzle3, newState.getLevel()+1, cost3);
		mS.put(p3.getCode(), p3);
		OS11.put(p3.getCode(), p3);
		if(cost3 == 0) {
			stop = true;
			tracBack(converStateToString(new_puzzle3));
		}
	}
	if(new_puzzle4.length >2) {
		int cost4 = fitness(desired, new_puzzle4);
		puzzle p4 = new puzzle(converStateToString(new_puzzle4),	newState.getCode(), new_puzzle4, newState.getLevel()+1, cost4);
		mS.put(p4.getCode(), p4);
		OS11.put(p4.getCode(), p4);
		if(cost4 == 0) {
			stop = true;
			tracBack(converStateToString(new_puzzle4));
		}
	}
	
	
	
	int tr = 0;
	while(!stop) {
		tr++;
		
		int h = mS.size();
		//if(h % 150 ==0)
		//System.out.println(h+" "+openStates.size()+" "+itr++);
		
		/*openStates.forEach((a, b) -> {
			System.out.println(a+"  "+b.getCode());
		});
		*/
		
		String code = bestToMoveOn(OS11);
		//System.out.println(openStates.get(code).getCost()+"\tcode");
		//System.out.println(h+" "+openStates.size()+" "+openStates.get(code).getCost());
		
		//System.out.println(code+"\tcode");
		//PrintPuzzle(openStates.get(code).getState());
		//System.out.println("++++++++++++++");
		
		
		
		
		int [][] n_puzzle1 = move(1, OS11.get(code).getState());
		int [][] n_puzzle2 = move(2, OS11.get(code).getState());
		int [][] n_puzzle3 = move(3, OS11.get(code).getState());
		int [][] n_puzzle4 = move(4, OS11.get(code).getState());
		
		if(tr==1) {
			//System.out.println(costInWhile1+"  1");
			//PrintPuzzle(openStates.get(code).getState());
			//PrintPuzzle(newState.getState());
		}
		
		if(n_puzzle1.length >2) {
			int costInWhile1 = fitness(desired, n_puzzle1);
			/*if(itr==1) {
				System.out.println(costInWhile1+"  1");
				PrintPuzzle(n_puzzle1);
			}*/
			puzzle newP1 = new puzzle(converStateToString(n_puzzle1), code, n_puzzle1, OS11.get(code).getLevel()+1,  costInWhile1);
			if(!mS.containsKey(converStateToString(n_puzzle1))) {
				OS11.put(converStateToString(n_puzzle1), newP1);
				mS.put(converStateToString(n_puzzle1), newP1);
				
				if(fitness(desired, n_puzzle1) == 0) {
					/*PrintPuzzle(desired);*/
					//PrintPuzzle(n_puzzle1);
					tracBack(converStateToString(n_puzzle1));
					break;
				}
			}
		}
		
		if(n_puzzle2.length >2) {
			int costInWhile2 = fitness(desired, n_puzzle2);
			/*if(itr==1) {
				System.out.println(costInWhile2+"  2");
				PrintPuzzle(n_puzzle2);
			}*/
			puzzle newP2 = new puzzle(converStateToString(n_puzzle2), code, n_puzzle2, OS11.get(code).getLevel()+1,  costInWhile2);
			if(!mS.containsKey(converStateToString(n_puzzle2))) {
				OS11.put(converStateToString(n_puzzle2), newP2);
				mS.put(converStateToString(n_puzzle2), newP2);
				
				if(fitness(desired, n_puzzle2) == 0) {
					/*PrintPuzzle(desired);*/
					//PrintPuzzle(n_puzzle2);
					tracBack(converStateToString(n_puzzle2));
					break;
				}
			}
		}
		
		if(n_puzzle3.length >2) {
			int costInWhile3 = fitness(desired, n_puzzle3);
			/*if(itr==1) {
				System.out.println(costInWhile3+"  3");
				PrintPuzzle(n_puzzle3);
			}*/
			puzzle newP3 = new puzzle(converStateToString(n_puzzle3), code, n_puzzle3, OS11.get(code).getLevel()+1,  costInWhile3);
			if(!mS.containsKey(converStateToString(n_puzzle3))) {
				OS11.put(converStateToString(n_puzzle3), newP3);
				mS.put(converStateToString(n_puzzle3), newP3);
				
				if(fitness(desired, n_puzzle3) == 0){
					/*PrintPuzzle(desired);*/
					//PrintPuzzle(n_puzzle3);
					tracBack(converStateToString(n_puzzle3));
					break;
				}
			}
		}
		
		if(n_puzzle4.length >2) {
			int costInWhile4 = fitness(desired, n_puzzle4);
			/*if(itr==1) {
				System.out.println(costInWhile4+"  4");
				PrintPuzzle(n_puzzle4);
			}*/
			puzzle newP4 = new puzzle(converStateToString(n_puzzle4), code, n_puzzle4, OS11.get(code).getLevel()+1,  costInWhile4);
			if(!mS.containsKey(converStateToString(n_puzzle4))) {
				OS11.put(converStateToString(n_puzzle4), newP4);
				mS.put(converStateToString(n_puzzle4), newP4);
				
				if(fitness(desired, n_puzzle4) == 0){
					/*PrintPuzzle(desired);*/
					//PrintPuzzle(n_puzzle4);
					tracBack(converStateToString(n_puzzle4));
					break;
				}
			}
		}
		
		OS11.remove(code);
		
	}
	
	
}


	public static void tracBack(String code) {
		System.out.println("Trace back ...");
		String father = mS.get(code).getParentCode();
		boolean doUntil = false;
		//String coder = metStates.get(code).getCode();
		//String father = metStates.get(code).getParentCode();
		//String grandfather = metStates.get(father).getParentCode();
		//String grand2father = metStates.get(grandfather).getParentCode();
		//String grand3father = metStates.get(grand2father).getParentCode();
		PrintPuzzle(mS.get(code).getState());
		
		while(!father.equals("root")) {
			PrintPuzzle(mS.get(father).getState());
			father = mS.get(father).getParentCode();
			//System.out.println(father);
		}
		
	}


	public static String bestToMoveOn(Map<String, puzzle> openSt) {
		
		ArrayList <puzzle> q = new ArrayList <puzzle>();
		
		
		
		int cost = 2147480647;
		String code = "";
		for(puzzle p : openSt.values()) {
			if((p.getCost()+p.getLevel()) < cost ) {
				cost = p.getCost()+p.getLevel();
				
			}
		}
		
		for(puzzle p : openSt.values()) {
			if((p.getCost()+p.getLevel()) == cost ) {
				q.add(p);
			}
		}
		
		int dp = 2147480647;
		for(puzzle p : q) {
			if(dp>p.getLevel()) {
				dp = p.getLevel();
				code = p.getCode();
			}
		}
		
		return code;
		
	}



	public static String converStateToString(int [][] puzzle) {
		String State = "";
		for (int i=0;i<puzzle.length;i++) {
			for (int j=0;j<puzzle.length;j++) {
				State = State + puzzle[i][j]+".";
			}
		}
		return State;
	}
	
	
	public static void PrintPuzzle(int [][] puzzle) {
		for (int i=0;i<puzzle.length;i++) {
			for (int j=0;j<puzzle.length;j++) {
				if(puzzle[i][j]!=-1)
					System.out.print(puzzle[i][j]+"\t");
				else
					System.out.print("X\t");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	
	public static boolean hasChanged(int [][] puzzle1, int [][] puzzle2) {
		for (int i=0;i<puzzle1.length;i++) {
			for (int j=0;j<puzzle1.length;j++) {
				if(puzzle1[i][j] != puzzle2[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static int [] X_location(int [][] puzzle) {
		for (int i=0;i<puzzle.length;i++) {
			for (int j=0;j<puzzle.length;j++) {
				if(puzzle[i][j] == -1) {
					return new int [] {i, j};
				}
			}
		}
		return null;
	}
	
	
	public static int [][] move(int direction, int [][] puzzle) {
		
		int empty_x = X_location(puzzle)[0];
		int empty_y = X_location(puzzle)[1];
		int [][] new_puzzle = new int [puzzle.length][puzzle.length];
		int [][] nullArray = {{1,1},{1,1}} ;
		for (int i=0;i<puzzle.length;i++) {
			for (int j=0;j<puzzle.length;j++) {
				new_puzzle[i][j] = puzzle[i][j];
			}
		}
		if(direction == 1) {
			if(empty_x>0) {
				int temp = new_puzzle[empty_x][empty_y];
				new_puzzle[empty_x][empty_y] = new_puzzle[empty_x-1][empty_y];
				new_puzzle[empty_x-1][empty_y] = temp;
				return new_puzzle;
			}
		}
		if(direction == 2) {
			if(empty_y>0) {
				int temp = new_puzzle[empty_x][empty_y];
				new_puzzle[empty_x][empty_y] = new_puzzle[empty_x][empty_y-1];
				new_puzzle[empty_x][empty_y-1] = temp;
				return new_puzzle;
			}
		}
		if(direction == 3) {
			if(empty_x<new_puzzle.length-1) {
				int temp = new_puzzle[empty_x][empty_y];
				new_puzzle[empty_x][empty_y] = new_puzzle[empty_x+1][empty_y];
				new_puzzle[empty_x+1][empty_y] = temp;
				return new_puzzle;
			}
		}
		if(direction == 4) {
			if(empty_y<new_puzzle.length-1) {
				int temp = new_puzzle[empty_x][empty_y];
				new_puzzle[empty_x][empty_y] = new_puzzle[empty_x][empty_y+1];
				new_puzzle[empty_x][empty_y+1] = temp;
				return new_puzzle;
			}
		}
		
		
		return nullArray;
	}
	
	
	
	public static int fitness(int [][] desired, int [][] puzzle) {
		int cost = 0;
		for(int t = 1; t<puzzle.length*puzzle.length;t++) {
			int x1 = -1;int y1 = -1;int x2 = -1;int y2 = -1;
			for (int i=0;i<puzzle.length;i++) {
				for (int j=0;j<puzzle.length;j++) {
					if(desired[i][j] == t) {
						x1 = i;
						y1 = j;
					}
					if(puzzle[i][j] == t) {
						x2 = i;
						y2 = j;
					}
					//System.out.println(puzzle[i][j]+"  "+desired[i][j]);
				}
			}
			
			//System.out.println(x1+" "+x2+" "+y1+" "+y2);
			cost += Math.abs(x1-x2)+Math.abs(y1-y2);
		}
		return cost;
	}
	
	public static int fitnessless(int [][] desired, int [][] puzzle) {
		int cost = puzzle.length*puzzle.length;
		
			for (int i=0;i<puzzle.length;i++) {
				for (int j=0;j<puzzle.length;j++) {
					if(desired[i][j] == puzzle[i][j]) {
						cost--;
					}
				}
			}
		
		return cost;
	}
}
