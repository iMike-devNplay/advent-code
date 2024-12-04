package fr.home.mikedev.days;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main04 {

	private final String dataFileName = "data-day04.txt";
	
	public static void main(String[] args) throws Exception 
	{
		Main04 m = new Main04();
		m.doFirst();
		m.doSecond();
	}
	
	public void doFirst() throws Exception
	{
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		String line = null;
		StringBuffer puzzle = new StringBuffer();
		char[][] puzzleMatrix = new char[140][140];
		int j = 0;
		while ((line = reader.readLine()) != null) 
		{
			puzzle.append(line + "\n");
			puzzleMatrix[j++] = line.toCharArray();
		}
		reader.close();
		Long count = Long.valueOf(0);
		
		// 1 a: search for horizontal and horizontal reverse
		Long interm = Long.valueOf(puzzle.toString().split("XMAS", -1).length-1);
		count += interm;
		System.out.println("  Horizontal          : " + count.toString() + " (" + interm + ")");
		interm = Long.valueOf(puzzle.toString().split("SAMX", -1).length-1);
		count += interm;
		System.out.println("+ Horizontal reversed : " + count.toString() + " (" + interm + ")");
	    
		// 1 b: Diagonal search
		interm = checkDiagonalTopBottomLeftRight(puzzleMatrix, "XMAS");
		count += interm;
		System.out.println("+ Diagonal 1          : " + count.toString() + " (" + interm + ")");

		interm = checkDiagonalTopBottomRightLeft(puzzleMatrix, "XMAS");
		count += interm;
		System.out.println("+ Diagonal 2          : " + count.toString() + " (" + interm + ")");
		
		interm = checkDiagonalBottomTopLeftRight(puzzleMatrix, "XMAS");
		count += interm;
		System.out.println("+ Diagonal 3          : " + count.toString() + " (" + interm + ")");
		
		interm = checkDiagonalBottomTopRightLeft(puzzleMatrix, "XMAS");
		count += interm;
		System.out.println("+ Diagonal 4          : " + count.toString() + " (" + interm + ")");
		
		// 2 b: Search vertical and vertical reversed
		rotate90(puzzleMatrix);
		String rotated90 = matrixToString(puzzleMatrix);
		count += rotated90.toString().split("XMAS", -1).length-1;
		System.out.println("+ Vertical            : " + count.toString());
		count += rotated90.toString().split("SAMX", -1).length-1;
		System.out.println("+ Vertical reversed   : " + count.toString());
		/*interm = checkVerticalTopBottom(puzzleMatrix, "XMAS");
		count += interm;
		System.out.println("+ Vertical 1          : " + count.toString() + " (" + interm + ")");
		
		interm = checkVerticalBottomTop(puzzleMatrix, "XMAS");
		count += interm;
		System.out.println("+ Vertical 2          : " + count.toString() + " (" + interm + ")");*/

	}
		
	public void doSecond() throws Exception
	{
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		String line = null;
		char[][] puzzleMatrix = new char[140][140];
		int j = 0;
		while ((line = reader.readLine()) != null) 
		{
			puzzleMatrix[j++] = line.toCharArray();
		}
		reader.close();
		Long count = Long.valueOf(0);
		
		Long interm = checkPatternXMAS(puzzleMatrix, 'M', 'M', 'S', 'S');
		count += interm;
		System.out.println("  MMSS          : " + count.toString() + " (" + interm + ")");
		
		interm = checkPatternXMAS(puzzleMatrix, 'S', 'M', 'M', 'S');
		count += interm;
		System.out.println("  SMMS          : " + count.toString() + " (" + interm + ")");
		
		interm = checkPatternXMAS(puzzleMatrix, 'S', 'S', 'M', 'M');
		count += interm;
		System.out.println("  SSMM          : " + count.toString() + " (" + interm + ")");
		
		interm = checkPatternXMAS(puzzleMatrix, 'M', 'S', 'S', 'M');
		count += interm;
		System.out.println("  MSSM          : " + count.toString() + " (" + interm + ")");
		
		System.out.println("Count   : " + count.toString());
	}
	
	public String matrixToString(char[][] matrix)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < matrix.length; i++)
		{
			for (int j = 0; j < matrix[i].length; j++)
				sb.append(matrix[i][j]);
			sb.append("\n");
		}
		return sb.toString();
	}
	
	static void rotate90 (char arr[][])
	{
		transpose (arr);
		reverseRows (arr);
	}
  
	static void reverseRows (char mat[][])
	{
		int n = mat.length;
		for (int i = 0; i < mat.length; i++){
			for (int j = 0; j <  mat.length/ 2; j++)
			{
				char temp = mat[i][j];
				mat[i][j] = mat[i][n - j - 1];
				mat[i][n - j - 1] = temp;
			}
		}    
	}

	static void transpose (char arr[][])
	{
		for (int i = 0; i < arr.length; i++)
			for (int j = i; j < arr[0].length; j++)
			{
				char temp = arr[j][i];
				arr[j][i] = arr[i][j];
				arr[i][j] = temp;
			}
	}
	
    public Long checkVerticalTopBottom(char[][] matrix, String word) 
    {
    	long count = 0;
		for (int l = 0; l < matrix.length-3; l++)
		{
			for (int c = 0; c < matrix[l].length; c++)
			{
				if (word.charAt(0) == matrix[l][c])
					if (word.charAt(1) == matrix[l+1][c])
						if (word.charAt(2) == matrix[l+2][c])
							if (word.charAt(3) == matrix[l+3][c])
								count++;
			}
		}
    	return Long.valueOf(count);
    }
    
    public Long checkVerticalBottomTop(char[][] matrix, String word) 
    {
    	long count = 0;
    	for (int l = matrix.length-1; l > 2; l--)
		{
			for (int c = 0; c < matrix[l].length; c++)
			{
				if (word.charAt(0) == matrix[l][c])
					if (word.charAt(1) == matrix[l-1][c])
						if (word.charAt(2) == matrix[l-2][c])
							if (word.charAt(3) == matrix[l-3][c])
								count++;
			}
		}
    	return Long.valueOf(count);
    }
    
    public Long checkDiagonalTopBottomLeftRight(char[][] matrix, String word) 
    {
    	long count = 0;
		for (int l = 0; l < matrix.length-3; l++)
		{
			for (int c = 0; c < matrix[l].length-3; c++)
			{
				if (word.charAt(0) == matrix[l][c])
					if (word.charAt(1) == matrix[l+1][c+1])
						if (word.charAt(2) == matrix[l+2][c+2])
							if (word.charAt(3) == matrix[l+3][c+3])
								count++;
			}
		}
    	return Long.valueOf(count);
    }
    
    public Long checkDiagonalTopBottomRightLeft(char[][] matrix, String word) 
    {
    	long count = 0;
		for (int l = 0; l < matrix.length-3; l++)
		{
			for (int c = matrix[l].length-1; c > 2; c--)
			{
				if (word.charAt(0) == matrix[l][c])
					if (word.charAt(1) == matrix[l+1][c-1])
						if (word.charAt(2) == matrix[l+2][c-2])
							if (word.charAt(3) == matrix[l+3][c-3])
								count++;
			}
		}
    	return Long.valueOf(count);
    }
    
    public Long checkDiagonalBottomTopLeftRight(char[][] matrix, String word) 
    {
    	long count = 0;
		for (int l = matrix.length-1; l > 2; l--)
		{
			for (int c = 0; c < matrix[l].length-3; c++)
			{
				if (word.charAt(0) == matrix[l][c])
					if (word.charAt(1) == matrix[l-1][c+1])
						if (word.charAt(2) == matrix[l-2][c+2])
							if (word.charAt(3) == matrix[l-3][c+3])
								count++;
			}
		}
    	return Long.valueOf(count);
    }
    
    public Long checkDiagonalBottomTopRightLeft(char[][] matrix, String word) 
    {
    	long count = 0;
    	for (int l = matrix.length-1; l > 2; l--)
		{
    		for (int c = matrix[l].length-1; c > 2; c--)
			{
				if (word.charAt(0) == matrix[l][c])
					if (word.charAt(1) == matrix[l-1][c-1])
						if (word.charAt(2) == matrix[l-2][c-2])
							if (word.charAt(3) == matrix[l-3][c-3])
								count++;
			}
		}
    	return Long.valueOf(count);
    }
    
    public Long checkPatternXMAS(char[][] matrix, char c1, char c2, char c3, char c4)
    {
    	long count = 0;
    	for (int l = 1; l < matrix.length-1; l++)
		{
    		for (int c = 1; c < matrix[l].length-1; c++)
			{
				if (matrix[l][c] == 'A')
					if (matrix[l-1][c-1] == c1)
						if (matrix[l-1][c+1] == c2)
							if (matrix[l+1][c+1] == c3)
								if (matrix[l+1][c-1] == c4)
									count++;
			}
		}
    	return Long.valueOf(count);
    }
}