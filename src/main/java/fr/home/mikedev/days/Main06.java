package fr.home.mikedev.days;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import fr.home.mikedev.days.utils.MatrixCoordinate;
import fr.home.mikedev.days.utils.MatrixUtils;

public class Main06 {

	private final String dataFileName = "data-day06.txt";
	MatrixCoordinate currentCursor;
	char[][] puzzleObstacle = new char[140][140];
	List<MatrixCoordinate> guardPath;
	int obstacle = 0;
	
	public static void main(String[] args) throws Exception 
	{
		Main06 m = new Main06();
		m.doFirst();
		m.doSecond();
	}
	
	public void doFirst() throws Exception
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
		
		currentCursor = getCursorPosition(puzzleMatrix);
		puzzleMatrix[currentCursor.getL()][currentCursor.getC()] = 'X';
		
		System.out.println(currentCursor);
		System.out.println(MatrixUtils.matrixToString(puzzleMatrix));
		guardPath = new ArrayList<MatrixCoordinate>();
		while (currentCursor.getDirection() != 'X')
		{
			if (currentCursor.getDirection() == '^') moveUpAndTurn(puzzleMatrix, true);
			else if (currentCursor.getDirection() == 'v') moveDownAndTurn(puzzleMatrix, true);
			else if (currentCursor.getDirection() == '>') moveRightAndTurn(puzzleMatrix, true);
			else if (currentCursor.getDirection() == '<') moveLeftAndTurn(puzzleMatrix, true);
			System.out.println(currentCursor);
			System.out.println(MatrixUtils.matrixToString(puzzleMatrix));
		}
		
		String finalMatrix = MatrixUtils.matrixToString(puzzleMatrix);
		Long countX = Long.valueOf(finalMatrix.split("X", -1).length-1) + Long.valueOf(finalMatrix.split("x", -1).length-1);
		System.out.println(countX);
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
		
		currentCursor = getCursorPosition(puzzleMatrix);
		MatrixCoordinate initCursor = getCursorPosition(puzzleMatrix);
		puzzleMatrix[currentCursor.getL()][currentCursor.getC()] = 'X';
		
		System.out.println(currentCursor);
		System.out.println(MatrixUtils.matrixToString(puzzleMatrix));
		guardPath = new ArrayList<MatrixCoordinate>();
		while (currentCursor.getDirection() != 'X')
		{
			if (currentCursor.getDirection() == '^') moveUpAndTurn(puzzleMatrix, true);
			else if (currentCursor.getDirection() == 'v') moveDownAndTurn(puzzleMatrix, true);
			else if (currentCursor.getDirection() == '>') moveRightAndTurn(puzzleMatrix, true);
			else if (currentCursor.getDirection() == '<') moveLeftAndTurn(puzzleMatrix, true);
			//System.out.println(currentCursor);
			//System.out.println(matrixToString(puzzleMatrix));
		}

		System.out.println(guardPath);
		System.out.println(guardPath.size());

		// Test all obstacle on each guard path point, it it is infinite, count it
		int loopObstacle = 0;
		for (int i = 0; i < guardPath.size(); i++)
		{
			System.out.println(i + " / " + guardPath.size() + " (loop=" + loopObstacle + ")");
			currentCursor = initCursor;
			MatrixCoordinate d6c = guardPath.get(i);
			puzzleMatrix[initCursor.getL()][initCursor.getC()] = '^';
			// add obstacle
			puzzleMatrix[d6c.getL()][d6c.getC()] = '#';
			//System.out.println(matrixToString(puzzleMatrix));
			
			// check if infinite
			if (isInfiniteLoop(puzzleMatrix)) loopObstacle++;
			
			// revert obstacle
			puzzleMatrix[d6c.getL()][d6c.getC()] = '.';
		}
		System.out.println(loopObstacle);
	}
	
	public boolean isInfiniteLoop(char[][] matrix)
	{
		long nbLoop = 0;
		while (currentCursor.getDirection() != 'X' && nbLoop < 500)
		{
			if (currentCursor.getDirection() == '^') moveUpAndTurn(matrix, false);
			else if (currentCursor.getDirection() == 'v') moveDownAndTurn(matrix, false);
			else if (currentCursor.getDirection() == '>') moveRightAndTurn(matrix, false);
			else if (currentCursor.getDirection() == '<') moveLeftAndTurn(matrix, false);
			//System.out.println(currentCursor);
			//System.out.println(matrixToString(matrix));
			nbLoop++;
		}
		if (nbLoop > 499) return true;
		else return false;
	}
	
	public void moveUpAndTurn(char[][] matrix, boolean registerPath)
	{
		for (int l = currentCursor.getL()-1; l >= 0 && currentCursor.getDirection() == '^'; l--)
		{
			char nextPosition = matrix[l][currentCursor.getC()];
			if (nextPosition == '#') currentCursor = MatrixCoordinate.builder().c(currentCursor.getC()).l(l+1).direction('>').build();
			else 
			{
				if (nextPosition == 'X' && l >= 1) puzzleObstacle[l-1][currentCursor.getC()] = 'x';
				if (matrix[l][currentCursor.getC()] != 'x') matrix[l][currentCursor.getC()] = 'X';
				if (l == 0) currentCursor = MatrixCoordinate.builder().c(currentCursor.getC()).l(l-1).direction('X').build();
				MatrixCoordinate d6c = MatrixCoordinate.builder().c(currentCursor.getC()).l(l).direction('X').build();
				if (!guardPath.contains(d6c) && registerPath) guardPath.add(d6c);
			}
		}
	}
	
	public void moveDownAndTurn(char[][] matrix, boolean registerPath)
	{
		for (int l = currentCursor.getL()+1; l < matrix.length && currentCursor.getDirection() == 'v'; l++)
		{
			char nextPosition = matrix[l][currentCursor.getC()];
			if (nextPosition == '#') currentCursor = MatrixCoordinate.builder().c(currentCursor.getC()).l(l-1).direction('<').build();
			else 
			{
				if (nextPosition == 'X' && l < matrix.length-1) puzzleObstacle[l+1][currentCursor.getC()] = 'x';
				if (matrix[l][currentCursor.getC()] != 'x') matrix[l][currentCursor.getC()] = 'X';
				if (l == matrix.length-1) currentCursor = MatrixCoordinate.builder().c(currentCursor.getC()).l(l-1).direction('X').build();
				MatrixCoordinate d6c = MatrixCoordinate.builder().c(currentCursor.getC()).l(l).direction('X').build();
				if (!guardPath.contains(d6c) && registerPath) guardPath.add(d6c);
			}
		}
	}
	
	public void moveLeftAndTurn(char[][] matrix, boolean registerPath)
	{
		for (int c = currentCursor.getC()-1; c >= 0 && currentCursor.getDirection() == '<'; c--)
		{
			char nextPosition = matrix[currentCursor.getL()][c];
			if (nextPosition == '#') currentCursor = MatrixCoordinate.builder().c(c+1).l(currentCursor.getL()).direction('^').build();
			else 
			{
				if (nextPosition == 'X' && c >=1) puzzleObstacle[currentCursor.getL()][c-1] = 'x';
				if (matrix[currentCursor.getL()][c] != 'x') matrix[currentCursor.getL()][c] = 'X';
				if (c == 0) currentCursor = MatrixCoordinate.builder().c(c).l(currentCursor.getL()).direction('X').build();
				MatrixCoordinate d6c = MatrixCoordinate.builder().l(currentCursor.getL()).c(c).direction('X').build();
				if (!guardPath.contains(d6c) && registerPath) guardPath.add(d6c);
			}
		}
	}
	
	public void moveRightAndTurn(char[][] matrix, boolean registerPath)
	{
		for (int c = currentCursor.getC()+1; c < matrix[currentCursor.getL()].length && currentCursor.getDirection() == '>'; c++)
		{
			char nextPosition = matrix[currentCursor.getL()][c];
			if (nextPosition == '#') currentCursor = MatrixCoordinate.builder().c(c-1).l(currentCursor.getL()).direction('v').build();
			else 
			{
				if (nextPosition == 'X' && c < matrix[currentCursor.getL()].length-1) puzzleObstacle[currentCursor.getL()][c+1] = 'x';
				if (matrix[currentCursor.getL()][c] != 'x') matrix[currentCursor.getL()][c] = 'X';
				if (c == matrix[currentCursor.getL()].length-1) currentCursor = MatrixCoordinate.builder().c(c).l(currentCursor.getL()).direction('X').build();
				MatrixCoordinate d6c = MatrixCoordinate.builder().l(currentCursor.getL()).c(c).direction('X').build();
				if (!guardPath.contains(d6c) && registerPath) guardPath.add(d6c);
			}
		}
	}
	
	public MatrixCoordinate getCursorPosition(char[][] matrix) 
	{
		MatrixCoordinate cursor = null;
		for (int i = 0; i < matrix.length && cursor == null; i++)
		{
			int j = MatrixUtils.matrixToString(matrix[i]).indexOf('v');
			if (j < 0)
			{
				j = MatrixUtils.matrixToString(matrix[i]).indexOf('^');
				if (j < 0)
				{
					j = MatrixUtils.matrixToString(matrix[i]).indexOf('>');
					if (j < 0)
					{
						j = MatrixUtils.matrixToString(matrix[i]).indexOf('<');
						if (j >= 0) cursor = MatrixCoordinate.builder().c(j).l(i).direction('<').build();
					}
					else cursor = MatrixCoordinate.builder().c(j).l(i).direction('>').build(); 
				}
				else cursor = MatrixCoordinate.builder().c(j).l(i).direction('^').build();
			}
			else cursor = MatrixCoordinate.builder().c(j).l(i).direction('v').build();
		}
		return cursor;
	}
}
