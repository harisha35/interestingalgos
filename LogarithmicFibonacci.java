/*
Reference : http://kukuruku.co/hub/algorithms/the-nth-fibonacci-number-in-olog-n
*/
import java.util.HashMap;
import java.util.ArrayList;

public class LogarithmicFibonacci {
	
	private static int[][] Q = {{1,1},{1,0}};
	private static HashMap<Integer,int[][]> map = 
			new HashMap<Integer,int[][]>();
	
	/*
	Multiplies two matrices of size 2 * 2 and returns the resulting matrix
	*/
	private int[][] matrixMultiplication(int[][] M1, int[][] M2) {
		int M[][] = new int[2][2];
		M[0][0] = M1[0][0]*M2[0][0] + M1[0][1]*M2[1][0];
		M[0][1] = M1[0][0]*M2[0][1] + M1[0][1]*M2[1][1];
		M[1][0] = M1[1][0]*M2[1][1] + M1[0][0]*M2[1][0];
		M[1][1] = M1[1][0]*M2[0][1] + M1[1][1]*M2[1][1];
		
		return M;
	}
	
	/*
	Recursive and Memoized method to calculate the power of the given matrix
	*/
	private int[][] matrixPower(int[][] M, int power) {
		if(power == 1) {
			return M;
		}
		
		if(map.containsKey(power)) {
			return map.get(power);
		}
		
		int[][] temp = matrixPower(M,power/2);
		int[][] res = matrixMultiplication(temp, temp);
		map.put(power, res);
		return res;
	}
	
	public int fibonacci(int n) {
		
		if(n <= 0)
			return 0;
		else if (n <= 1)
			return 1;
		
		String s = Integer.toBinaryString(n-1);
		ArrayList<Integer> powers = new ArrayList<Integer>();
		ArrayList<int[][]> matrices = new ArrayList<int[][]>();
		
		int i, j;
		for(i = s.length()-1, j = 0; i >=0 ; i--, j++) {
			if(s.charAt(i) == '1') {
				powers.add((int) Math.pow(2, j));
			}
		}
		
		for(int k : powers)
			matrices.add(matrixPower(Q,k));
		
		while(matrices.size() > 1) {
			int[][] M1 = matrices.remove(matrices.size()-1);
			int[][] M2 = matrices.remove(matrices.size()-1);
			int[][] M = matrixMultiplication(M1,M2);
			matrices.add(M);
		}
		return matrices.get(0)[0][0];
	}
	
	public static void main(String[] args) {
		LogarithmicFibonacci lf = new LogarithmicFibonacci();
		System.out.println(lf.fibonacci(10));
	}
}