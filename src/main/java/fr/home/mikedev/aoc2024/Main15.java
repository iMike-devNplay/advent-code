package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.MatrixUtils;
import fr.home.mikedev.common.Pair;

public class Main15 extends MainDay 
{
    int matrixSize = 50;
    char[][] puzzleMatrix = new char[matrixSize][matrixSize];
    String instructions;
    
    Pair<Integer> initRPos;
    Pair<Integer> currRPos;
    
    Map<Pair<Integer>, String> allWallPosition;
    Map<Pair<Integer>, String> allBoxPosition;
    Map<Pair<Integer>, String> allEmptyPosition;
    
	public Main15(String title, String year) {super(title, year, "15");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
		    int i = 0;
            while((line = reader.readLine()) != null)
            {
                if (i < matrixSize) puzzleMatrix[i] = line.toCharArray();
                else if (i > matrixSize) instructions += line;
                i++;
            }
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
			ex.printStackTrace();
		}
	}
		
	public void doPart1()
	{
	    instructions = "";
	    allWallPosition = new HashMap<Pair<Integer>, String>();
	    allBoxPosition = new HashMap<Pair<Integer>, String>();
	    allEmptyPosition = new HashMap<Pair<Integer>, String>();
		retrieveData();
		
		for (int l = 0; l < matrixSize; l++)
		    for (int c = 0; c < matrixSize; c++)
		    {
		        String tileStr = String.valueOf(puzzleMatrix[l][c]);
		        Pair<Integer> current = Pair.<Integer>builder().v1(Integer.valueOf(l)).v2(Integer.valueOf(c)).build();
		        if (tileStr.equals("#")) allWallPosition.put(current, tileStr);
		        else if (tileStr.equals("O")) allBoxPosition.put(current, tileStr);
		        else if (tileStr.equals("@")) initRPos = current;
		        else if (tileStr.equals(".")) allEmptyPosition.put(current, tileStr);
		    }
		currRPos = initRPos;
		
		applyInstructions();
        
		log(MatrixUtils.matrixToString(puzzleMatrix));
		rebuildMatrix();
		log(MatrixUtils.matrixToString(puzzleMatrix));
		log(instructions);
		
		long sum = 0;
		for (Pair<Integer> b : allBoxPosition.keySet())
		    sum += 100 * b.getV1() + b.getV2();
		
		displayResultPart1(sum);
	}
	
	public void doPart2()
	{
		//retrieveData();
		displayResultPart2(0);
	}
	
	void applyInstructions()
	{
	    for (int i = 0; i < instructions.length(); i++)
	    {
	        char move = instructions.charAt(i);
	        switch (move)
	        {
    	        case '^':
    	            applyUp();
    	            break;
    	        case 'v':
    	            applyDown();
    	            break;
    	        case '>':
    	            applyRight();
    	            break;
    	        case '<':
    	            applyLeft();
    	            break;
	        }
	        //rebuildMatrix();
	    }
	}
	
	void applyUp()
	{
	    //log("up");
	    Pair<Integer> newAttemptPosition = Pair.<Integer>builder().v1(currRPos.getV1()-1).v2(currRPos.getV2()).build();
	    String pos = "";
	    /*if ((pos = allWallPosition.get(newAttemptPosition)) != null) log("un mur, on ne fait rien");
	    else */if ((pos = allBoxPosition.get(newAttemptPosition)) != null) pushBoxUp(newAttemptPosition, false);
	    else if ((pos = allEmptyPosition.get(newAttemptPosition)) != null) moveToEmpty(newAttemptPosition);
	}
	
	void applyDown()
	{
	    //log("down");
        Pair<Integer> newAttemptPosition = Pair.<Integer>builder().v1(currRPos.getV1()+1).v2(currRPos.getV2()).build();
        String pos = "";
        /*if ((pos = allWallPosition.get(newAttemptPosition)) != null) log("un mur, on ne fait rien");
        else */if ((pos = allBoxPosition.get(newAttemptPosition)) != null) pushBoxDown(newAttemptPosition, false);
        else if ((pos = allEmptyPosition.get(newAttemptPosition)) != null) moveToEmpty(newAttemptPosition);
	}
	void applyRight()
	{
	    //log("right");
        Pair<Integer> newAttemptPosition = Pair.<Integer>builder().v1(currRPos.getV1()).v2(currRPos.getV2()+1).build();
        String pos = "";
        /*if ((pos = allWallPosition.get(newAttemptPosition)) != null) log("un mur, on ne fait rien");
        else */if ((pos = allBoxPosition.get(newAttemptPosition)) != null) pushBoxRight(newAttemptPosition, false);
        else if ((pos = allEmptyPosition.get(newAttemptPosition)) != null) moveToEmpty(newAttemptPosition);
	}
	void applyLeft()
	{
	    //log("left");
        Pair<Integer> newAttemptPosition = Pair.<Integer>builder().v1(currRPos.getV1()).v2(currRPos.getV2()-1).build();
        String pos = "";
        /*if ((pos = allWallPosition.get(newAttemptPosition)) != null) log("un mur, on ne fait rien");
        else */if ((pos = allBoxPosition.get(newAttemptPosition)) != null) pushBoxLeft(newAttemptPosition, false);
        else if ((pos = allEmptyPosition.get(newAttemptPosition)) != null) moveToEmpty(newAttemptPosition);
	}
	
	void moveToEmpty(Pair<Integer> newPos)
	{
	    allEmptyPosition.put(currRPos, ".");
	    currRPos = newPos;
	    allEmptyPosition.remove(newPos);
	    allBoxPosition.remove(newPos);
	}
	
	void pushBoxUp(Pair<Integer> newPos, boolean fromBox)
    {
        //log("box to up");
        Pair<Integer> newAttemptBoxPosition = Pair.<Integer>builder().v1(newPos.getV1()-1).v2(newPos.getV2()).build();
        String pos = "";
        /*if ((pos = allWallPosition.get(newAttemptBoxPosition)) != null) log("un mur, on ne fait rien");
        else */if ((pos = allBoxPosition.get(newAttemptBoxPosition)) != null) pushBoxUp(newAttemptBoxPosition, true);
        else if ((pos = allEmptyPosition.get(newAttemptBoxPosition)) != null)
        {
            //moveBoxToEmpty(newAttemptBoxPosition);
            allEmptyPosition.put(currRPos, ".");
            moveToEmpty(Pair.<Integer>builder().v1(currRPos.getV1()-1).v2(currRPos.getV2()).build());
            allEmptyPosition.remove(newAttemptBoxPosition);
            if (!fromBox) allBoxPosition.remove(newPos);
            allBoxPosition.put(newAttemptBoxPosition, "B");
        }
    }
    
	void pushBoxDown(Pair<Integer> newPos, boolean fromBox)
    {
        //log("box to down");
        Pair<Integer> newAttemptBoxPosition = Pair.<Integer>builder().v1(newPos.getV1()+1).v2(newPos.getV2()).build();
        String pos = "";
        /*if ((pos = allWallPosition.get(newAttemptBoxPosition)) != null) log("un mur, on ne fait rien");
        else */if ((pos = allBoxPosition.get(newAttemptBoxPosition)) != null) pushBoxDown(newAttemptBoxPosition, true);
        else if ((pos = allEmptyPosition.get(newAttemptBoxPosition)) != null)
        {
            //moveBoxToEmpty(newAttemptBoxPosition);
            allEmptyPosition.put(currRPos, ".");
            moveToEmpty(Pair.<Integer>builder().v1(currRPos.getV1()+1).v2(currRPos.getV2()).build());
            allEmptyPosition.remove(newAttemptBoxPosition);
            if (!fromBox) allBoxPosition.remove(newPos);
            allBoxPosition.put(newAttemptBoxPosition, "B");
        }
    }
	void pushBoxRight(Pair<Integer> newPos, boolean fromBox)
    {
        //log("box to right");
        Pair<Integer> newAttemptBoxPosition = Pair.<Integer>builder().v1(newPos.getV1()).v2(newPos.getV2()+1).build();
        String pos = "";
        /*if ((pos = allWallPosition.get(newAttemptBoxPosition)) != null) log("un mur, on ne fait rien");
        else */if ((pos = allBoxPosition.get(newAttemptBoxPosition)) != null) pushBoxRight(newAttemptBoxPosition, true);
        else if ((pos = allEmptyPosition.get(newAttemptBoxPosition)) != null)
        {
            //moveBoxToEmpty(newAttemptBoxPosition);
            allEmptyPosition.put(currRPos, ".");
            moveToEmpty(Pair.<Integer>builder().v1(currRPos.getV1()).v2(currRPos.getV2()+1).build());
            allEmptyPosition.remove(newAttemptBoxPosition);
            if (!fromBox) allBoxPosition.remove(newPos);
            allBoxPosition.put(newAttemptBoxPosition, "B");
        }
    }
     
	void pushBoxLeft(Pair<Integer> newPos, boolean fromBox)
	{
	    //log("box to left");
	    Pair<Integer> newAttemptBoxPosition = Pair.<Integer>builder().v1(newPos.getV1()).v2(newPos.getV2()-1).build();
        String pos = "";
        /*if ((pos = allWallPosition.get(newAttemptBoxPosition)) != null) log("un mur, on ne fait rien");
        else */if ((pos = allBoxPosition.get(newAttemptBoxPosition)) != null) pushBoxLeft(newAttemptBoxPosition, true);
        else if ((pos = allEmptyPosition.get(newAttemptBoxPosition)) != null)
        {
            allEmptyPosition.put(currRPos, ".");
            moveToEmpty(Pair.<Integer>builder().v1(currRPos.getV1()).v2(currRPos.getV2()-1).build());
            allEmptyPosition.remove(newAttemptBoxPosition);
            if (!fromBox) allBoxPosition.remove(newPos);
            allBoxPosition.put(newAttemptBoxPosition, "B");
        }
	}
	
	void rebuildMatrix()
	{
	    for (int i = 0 ; i < matrixSize; i++)
	        for (int j = 0; j < matrixSize; j++)
	            puzzleMatrix[i][j] = 'X';
	    for (Pair<Integer> w : allWallPosition.keySet()) puzzleMatrix[w.getV1()][w.getV2()] = '#';
	    for (Pair<Integer> b : allBoxPosition.keySet()) puzzleMatrix[b.getV1()][b.getV2()] = 'O';
	    for (Pair<Integer> e : allEmptyPosition.keySet()) puzzleMatrix[e.getV1()][e.getV2()] = '.';
	    puzzleMatrix[currRPos.getV1()][currRPos.getV2()] = '@';
	}
}
