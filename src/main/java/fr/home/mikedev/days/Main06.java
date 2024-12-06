package fr.home.mikedev.days;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import fr.home.mikedev.days.utils.Day6Cursor;

public class Main06 {

	private final String dataFileName = "data-day06.txt";
	Day6Cursor currentCursor;
	char[][] puzzleObstacle = { {'.', '.', '.', '.', '.', '.', '.', '.', '.', '.'}, 
								{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.'},
								{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.'},
								{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.'},
								{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.'},
								{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.'},
								{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.'},
								{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.'},
								{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.'},
								{'.', '.', '.', '.', '.', '.', '.', '.', '.', '.'}};
	int obstacle = 0;
	
	public static void main(String[] args) throws Exception 
	{
		Main06 m = new Main06();
		//m.doFirst();
		m.doSecond();
	}
	
	public void doFirst() throws Exception
	{
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		String line = null;
		char[][] puzzleMatrix = new char[10][10];
		int j = 0;
		while ((line = reader.readLine()) != null) 
		{
			puzzleMatrix[j++] = line.toCharArray();
		}
		reader.close();
		
		currentCursor = getCursorPosition(puzzleMatrix);
		puzzleMatrix[currentCursor.getL()][currentCursor.getC()] = 'X';
		
		System.out.println(currentCursor);
		System.out.println(matrixToString(puzzleMatrix));
		while (currentCursor.getDirection() != 'X')
		{
			if (currentCursor.getDirection() == '^') moveUpAndTurn(puzzleMatrix);
			else if (currentCursor.getDirection() == 'v') moveDownAndTurn(puzzleMatrix);
			else if (currentCursor.getDirection() == '>') moveRightAndTurn(puzzleMatrix);
			else if (currentCursor.getDirection() == '<') moveLeftAndTurn(puzzleMatrix);
			System.out.println(currentCursor);
			System.out.println(matrixToString(puzzleMatrix));
		}
		
		String finalMatrix = matrixToString(puzzleMatrix);
		Long countX = Long.valueOf(finalMatrix.split("X", -1).length-1) + Long.valueOf(finalMatrix.split("x", -1).length-1);
		System.out.println(countX);
	}
		
	public void doSecond() throws Exception
	{
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		String line = null;
		char[][] puzzleMatrix = new char[10][10];
		int j = 0;
		while ((line = reader.readLine()) != null) 
		{
			puzzleMatrix[j++] = line.toCharArray();
		}
		reader.close();
		
		currentCursor = getCursorPosition(puzzleMatrix);
		puzzleMatrix[currentCursor.getL()][currentCursor.getC()] = 'X';
		
		System.out.println(currentCursor);
		System.out.println(matrixToString(puzzleMatrix));
		while (currentCursor.getDirection() != 'X')
		{
			if (currentCursor.getDirection() == '^') moveUpAndTurn(puzzleMatrix);
			else if (currentCursor.getDirection() == 'v') moveDownAndTurn(puzzleMatrix);
			else if (currentCursor.getDirection() == '>') moveRightAndTurn(puzzleMatrix);
			else if (currentCursor.getDirection() == '<') moveLeftAndTurn(puzzleMatrix);
			System.out.println(currentCursor);
			System.out.println(matrixToString(puzzleMatrix));
		}
		
		String finalMatrix = matrixToString(puzzleObstacle);
		Long countX = Long.valueOf(finalMatrix.split("x", -1).length-1);
		System.out.println(matrixToString(puzzleObstacle));
		System.out.println(countX);
	}
	
	public void moveUpAndTurn(char[][] matrix)
	{
		for (int l = currentCursor.getL()-1; l >= 0 && currentCursor.getDirection() == '^'; l--)
		{
			char nextPosition = matrix[l][currentCursor.getC()];
			if (nextPosition == '#') currentCursor = Day6Cursor.builder().c(currentCursor.getC()).l(l+1).direction('>').build();
			else 
			{
				if (nextPosition == 'X' && l >= 1) puzzleObstacle[l-1][currentCursor.getC()] = 'x';
				if (matrix[l][currentCursor.getC()] != 'x') matrix[l][currentCursor.getC()] = 'X';
				if (l == 0) currentCursor = Day6Cursor.builder().c(currentCursor.getC()).l(l-1).direction('X').build();
			}
		}
	}
	
	public void moveDownAndTurn(char[][] matrix)
	{
		for (int l = currentCursor.getL()+1; l < matrix.length && currentCursor.getDirection() == 'v'; l++)
		{
			char nextPosition = matrix[l][currentCursor.getC()];
			if (nextPosition == '#') currentCursor = Day6Cursor.builder().c(currentCursor.getC()).l(l-1).direction('<').build();
			else 
			{
				if (nextPosition == 'X' && l < matrix.length-1) puzzleObstacle[l+1][currentCursor.getC()] = 'x';
				if (matrix[l][currentCursor.getC()] != 'x') matrix[l][currentCursor.getC()] = 'X';
				if (l == matrix.length-1) currentCursor = Day6Cursor.builder().c(currentCursor.getC()).l(l-1).direction('X').build();
			}
		}
	}
	
	public void moveLeftAndTurn(char[][] matrix)
	{
		for (int c = currentCursor.getC()-1; c >= 0 && currentCursor.getDirection() == '<'; c--)
		{
			char nextPosition = matrix[currentCursor.getL()][c];
			if (nextPosition == '#') currentCursor = Day6Cursor.builder().c(c+1).l(currentCursor.getL()).direction('^').build();
			else 
			{
				if (nextPosition == 'X' && c >=1) puzzleObstacle[currentCursor.getL()][c-1] = 'x';
				if (matrix[currentCursor.getL()][c] != 'x') matrix[currentCursor.getL()][c] = 'X';
				if (c == 0) currentCursor = Day6Cursor.builder().c(c).l(currentCursor.getL()).direction('X').build();
			}
		}
	}
	
	public void moveRightAndTurn(char[][] matrix)
	{
		for (int c = currentCursor.getC()+1; c < matrix[currentCursor.getL()].length && currentCursor.getDirection() == '>'; c++)
		{
			char nextPosition = matrix[currentCursor.getL()][c];
			if (nextPosition == '#') currentCursor = Day6Cursor.builder().c(c-1).l(currentCursor.getL()).direction('v').build();
			else 
			{
				if (nextPosition == 'X' && c < matrix[currentCursor.getL()].length-1) puzzleObstacle[currentCursor.getL()][c+1] = 'x';
				if (matrix[currentCursor.getL()][c] != 'x') matrix[currentCursor.getL()][c] = 'X';
				if (c == matrix[currentCursor.getL()].length-1) currentCursor = Day6Cursor.builder().c(c).l(currentCursor.getL()).direction('X').build();
			}
		}
	}
	
	public Day6Cursor getCursorPosition(char[][] matrix) 
	{
		Day6Cursor cursor = null;
		for (int i = 0; i < matrix.length && cursor == null; i++)
		{
			int j = convertToString(matrix[i]).indexOf('v');
			if (j < 0)
			{
				j = convertToString(matrix[i]).indexOf('^');
				if (j < 0)
				{
					j = convertToString(matrix[i]).indexOf('>');
					if (j < 0)
					{
						j = convertToString(matrix[i]).indexOf('<');
						if (j >= 0) cursor = Day6Cursor.builder().c(j).l(i).direction('<').build();
					}
					else cursor = Day6Cursor.builder().c(j).l(i).direction('>').build(); 
				}
				else cursor = Day6Cursor.builder().c(j).l(i).direction('^').build();
			}
			else cursor = Day6Cursor.builder().c(j).l(i).direction('v').build();
		}
		return cursor;
	}
	
	public String convertToString(char[] a)
	{
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < a.length; i++)
			sb.append(a[i]);
		return sb.toString();
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
}
