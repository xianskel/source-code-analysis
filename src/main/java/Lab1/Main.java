package Lab1;

import Jama.Matrix;

public class Main {

	public static void main(String[] args) {

		double[][] array = {{1.,2.,3.},{4.,5.,6.},{7.,8.,10.}};
		Matrix A = new Matrix(array);
		Matrix b = Matrix.random(3,3);
		
		System.out.println(A.times(b));
		System.out.println(A.plus(b));
	}

}
