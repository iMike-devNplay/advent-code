package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.MatrixUtils;

public class Main04 extends MainDay 
{
	StringBuffer puzzle = new StringBuffer();
	char[][] puzzleMatrix = new char[140][140];
	
	public Main04(String title, String year) {super(title, year, "04");}
	
	public void retrieveData()
	{
		String line = null;
		int j = 0;
		try(BufferedReader reader = this.getReader())
		{
			while ((line = reader.readLine()) != null) 
			{
				puzzle.append(line + "\n");
				puzzleMatrix[j++] = line.toCharArray();
			}
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
		}
	}
	
	public void doPart1()
	{
		retrieveData();
		
		Long count = Long.valueOf(0);
		// 1 a: search for horizontal and horizontal reverse
		Long interm = Long.valueOf(puzzle.toString().split("XMAS", -1).length-1);
		count += interm;
		//System.out.println("  Horizontal          : " + count.toString() + " (" + interm + ")");
		interm = Long.valueOf(puzzle.toString().split("SAMX", -1).length-1);
		count += interm;
		//System.out.println("+ Horizontal reversed : " + count.toString() + " (" + interm + ")");
	    
		// 1 b: Diagonal search
		interm = checkDiagonalTopBottomLeftRight(puzzleMatrix, "XMAS");
		count += interm;
		//System.out.println("+ Diagonal 1          : " + count.toString() + " (" + interm + ")");

		interm = checkDiagonalTopBottomRightLeft(puzzleMatrix, "XMAS");
		count += interm;
		//System.out.println("+ Diagonal 2          : " + count.toString() + " (" + interm + ")");
		
		interm = checkDiagonalBottomTopLeftRight(puzzleMatrix, "XMAS");
		count += interm;
		//System.out.println("+ Diagonal 3          : " + count.toString() + " (" + interm + ")");
		
		interm = checkDiagonalBottomTopRightLeft(puzzleMatrix, "XMAS");
		count += interm;
		//System.out.println("+ Diagonal 4          : " + count.toString() + " (" + interm + ")");
		
		// 2 b: Search vertical and vertical reversed
		MatrixUtils.rotate90(puzzleMatrix);
		String rotated90 = MatrixUtils.matrixToString(puzzleMatrix);
		count += rotated90.toString().split("XMAS", -1).length-1;
		//System.out.println("+ Vertical            : " + count.toString());
		
		count += rotated90.toString().split("SAMX", -1).length-1;
		//System.out.println("+ Vertical reversed   : " + count.toString());
		
		setResultPart1(count);
	}
		
	public void doPart2()
	{

		Long count = Long.valueOf(0);
		
		Long interm = checkPatternXMAS(puzzleMatrix, 'M', 'M', 'S', 'S');
		count += interm;
		//System.out.println("  MMSS          : " + count.toString() + " (" + interm + ")");
		
		interm = checkPatternXMAS(puzzleMatrix, 'S', 'M', 'M', 'S');
		count += interm;
		//System.out.println("  SMMS          : " + count.toString() + " (" + interm + ")");
		
		interm = checkPatternXMAS(puzzleMatrix, 'S', 'S', 'M', 'M');
		count += interm;
		//System.out.println("  SSMM          : " + count.toString() + " (" + interm + ")");
		
		interm = checkPatternXMAS(puzzleMatrix, 'M', 'S', 'S', 'M');
		count += interm;
		//System.out.println("  MSSM          : " + count.toString() + " (" + interm + ")");
		
		setResultPart2(count);
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