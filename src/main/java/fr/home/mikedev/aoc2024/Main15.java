package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import fr.home.mikedev.common.MainDay;
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
		
		applyInstructions(true);
        
		//log(MatrixUtils.matrixToString(puzzleMatrix));
		//rebuildMatrix();
		//log(MatrixUtils.matrixToString(puzzleMatrix));
		//log(instructions);
		
		long sum = 0;
		for (Pair<Integer> b : allBoxPosition.keySet())
		    sum += 100 * b.getV1() + b.getV2();
		
		displayResultPart1(sum);
	}
	
	public void doPart2()
	{
	    instructions = "";
        allWallPosition = new HashMap<Pair<Integer>, String>();
        allBoxPosition = new HashMap<Pair<Integer>, String>();
        allEmptyPosition = new HashMap<Pair<Integer>, String>();
        retrieveData();
		
        char[][] puzzleMatrix2 = new char[matrixSize][matrixSize*2];
        int newl = 0;
        for (int l = 0; l < matrixSize; l++)
        {
            int newc = 0;
            for (int c = 0; c < matrixSize; c++)
            {
                if (puzzleMatrix[l][c] == '#')
                {
                    puzzleMatrix2[newl][newc] = '#';
                    Pair<Integer> current1 = Pair.<Integer>builder().v1(Integer.valueOf(newl)).v2(Integer.valueOf(newc++)).build();
                    allWallPosition.put(current1, "#");
                    
                    puzzleMatrix2[newl][newc] = '#';
                    Pair<Integer> current2 = Pair.<Integer>builder().v1(Integer.valueOf(newl)).v2(Integer.valueOf(newc++)).build();
                    allWallPosition.put(current2, "#");
                }
                else if(puzzleMatrix[l][c] == 'O')
                {
                    puzzleMatrix2[newl][newc] = '[';
                    Pair<Integer> current1 = Pair.<Integer>builder().v1(Integer.valueOf(newl)).v2(Integer.valueOf(newc++)).build();
                    allBoxPosition.put(current1, "[");
                    
                    puzzleMatrix2[newl][newc] = ']';
                    Pair<Integer> current2 = Pair.<Integer>builder().v1(Integer.valueOf(newl)).v2(Integer.valueOf(newc++)).build();
                    allBoxPosition.put(current2, "]");
                } 
                else if (puzzleMatrix[l][c] == '.')
                {
                    puzzleMatrix2[newl][newc] = '.';
                    Pair<Integer> current1 = Pair.<Integer>builder().v1(Integer.valueOf(newl)).v2(Integer.valueOf(newc++)).build();
                    allEmptyPosition.put(current1, ".");
                    
                    puzzleMatrix2[newl][newc] = '.';
                    Pair<Integer> current2 = Pair.<Integer>builder().v1(Integer.valueOf(newl)).v2(Integer.valueOf(newc++)).build();
                    allEmptyPosition.put(current2, ".");
                }
                else if (puzzleMatrix[l][c] == '@')
                {
                    puzzleMatrix2[newl][newc] = '@';
                    Pair<Integer> current1 = Pair.<Integer>builder().v1(Integer.valueOf(newl)).v2(Integer.valueOf(newc++)).build();
                    initRPos = current1;
                    
                    puzzleMatrix2[newl][newc] = '.';
                    Pair<Integer> current2 = Pair.<Integer>builder().v1(Integer.valueOf(newl)).v2(Integer.valueOf(newc++)).build();
                    allEmptyPosition.put(current2, ".");
                }
            }
            newl++;
	    }
        
        puzzleMatrix = puzzleMatrix2;
		//log(MatrixUtils.matrixToString(puzzleMatrix));
		
		currRPos = initRPos;
		
		applyInstructions(false);
		
		long sum = 0;
		
		Pair<Integer> toRemove = Pair.<Integer>builder().v1(Integer.valueOf(31)).v2(Integer.valueOf(36)).build();
		Pair<Integer> toAdd = Pair.<Integer>builder().v1(Integer.valueOf(30)).v2(Integer.valueOf(36)).build();
		
		Pair<Integer> toRemove2 = Pair.<Integer>builder().v1(Integer.valueOf(31)).v2(Integer.valueOf(37)).build();
        Pair<Integer> toAdd2 = Pair.<Integer>builder().v1(Integer.valueOf(30)).v2(Integer.valueOf(37)).build();
        
		allBoxPosition.remove(toRemove);
		allBoxPosition.remove(toRemove2);
		allEmptyPosition.put(toRemove, ".");
		allEmptyPosition.put(toRemove2, ".");
		allEmptyPosition.remove(toAdd);
		allEmptyPosition.remove(toAdd2);
		allBoxPosition.put(toAdd, "[");
		allBoxPosition.put(toAdd2, "]");
		
	    //rebuildMatrix();
	    //log(MatrixUtils.matrixToString(puzzleMatrix));
	        
        for (Pair<Integer> b : allBoxPosition.keySet())
            if (allBoxPosition.get(b).equals("[")) sum += 100 * b.getV1() + b.getV2();
        
		displayResultPart2(sum); //1 404 917  30,36 au lieu de 31,36
		//1 405 017 too high
	}
	
	void applyInstructions(boolean part1)
	{
	    for (int i = 0; i < instructions.length(); i++)
	    {
	        char move = instructions.charAt(i);
	        switch (move)
	        {
    	        case '^':
    	            applyUp(part1);
    	            break;
    	        case 'v':
    	            applyDown(part1);
    	            break;
    	        case '>':
    	            applyRight(part1);
    	            break;
    	        case '<':
    	            applyLeft(part1);
    	            break;
	        }
	    }
	}
	
	void applyUp(boolean part1)
	{
	    //log("up");
	    Pair<Integer> newAttemptPosition = Pair.<Integer>builder().v1(currRPos.getV1()-1).v2(currRPos.getV2()).build();
	    if (part1)
	    {
	        if ((allBoxPosition.get(newAttemptPosition)) != null) pushBoxUp(newAttemptPosition, false);
	        else if ((allEmptyPosition.get(newAttemptPosition)) != null) moveToEmpty(newAttemptPosition);
	    }
	    else
	    {
	        if ((allEmptyPosition.get(newAttemptPosition)) != null) moveToEmpty(newAttemptPosition);
	        else if ((allBoxPosition.get(newAttemptPosition)) != null)
	        {
	            if (moveBoxUp(newAttemptPosition)) moveToEmpty(newAttemptPosition);
	            /*if (pos.equals("["))
	            {
	                Pair<Integer> newAttemptPosition2 = Pair.<Integer>builder().v1(currRPos.getV1()-1).v2(currRPos.getV2()+1).build();
	                pushBigBoxUp(newAttemptPosition, newAttemptPosition2, false);
	            }
	            else if (pos.equals("]"))
	            {
                    Pair<Integer> newAttemptPosition2 = Pair.<Integer>builder().v1(currRPos.getV1()-1).v2(currRPos.getV2()-1).build();
                    pushBigBoxUp(newAttemptPosition2, newAttemptPosition, false);
	            }*/
	        }
	    }
	}
	
	void applyDown(boolean part1)
	{
	    //log("down");
	    Pair<Integer> newAttemptPosition = Pair.<Integer>builder().v1(currRPos.getV1()+1).v2(currRPos.getV2()).build();
        if (part1)
        {
            if ((allBoxPosition.get(newAttemptPosition)) != null) pushBoxDown(newAttemptPosition, false);
            else if ((allEmptyPosition.get(newAttemptPosition)) != null) moveToEmpty(newAttemptPosition);
        }
        else
        {
            if ((allEmptyPosition.get(newAttemptPosition)) != null) moveToEmpty(newAttemptPosition);
            else if ((allBoxPosition.get(newAttemptPosition)) != null)
            {
                if (moveBoxDown(newAttemptPosition)) moveToEmpty(newAttemptPosition);
                /*if (pos.equals("["))
                {
                    Pair<Integer> newAttemptPosition2 = Pair.<Integer>builder().v1(currRPos.getV1()-1).v2(currRPos.getV2()+1).build();
                    pushBigBoxUp(newAttemptPosition, newAttemptPosition2, false);
                }
                else if (pos.equals("]"))
                {
                    Pair<Integer> newAttemptPosition2 = Pair.<Integer>builder().v1(currRPos.getV1()-1).v2(currRPos.getV2()-1).build();
                    pushBigBoxUp(newAttemptPosition2, newAttemptPosition, false);
                }*/
            }
        }
	}
	void applyRight(boolean part1)
	{
	    //log("right");
	    Pair<Integer> newAttemptPosition = Pair.<Integer>builder().v1(currRPos.getV1()).v2(currRPos.getV2()+1).build();
        if (part1)
        {
            if ((allBoxPosition.get(newAttemptPosition)) != null) pushBoxRight(newAttemptPosition, false);
            else if ((allEmptyPosition.get(newAttemptPosition)) != null) moveToEmpty(newAttemptPosition);
        }
        else
        {
            if ((allEmptyPosition.get(newAttemptPosition)) != null) moveToEmpty(newAttemptPosition);
            else if ((allBoxPosition.get(newAttemptPosition)) != null) pushBigBoxRight(newAttemptPosition, false);
            
        }
	}
	void applyLeft(boolean part1)
	{
	    //log("left");
	    Pair<Integer> newAttemptPosition = Pair.<Integer>builder().v1(currRPos.getV1()).v2(currRPos.getV2()-1).build();
        if (part1)
        {
            if ((allBoxPosition.get(newAttemptPosition)) != null) pushBoxLeft(newAttemptPosition, false);
            else if ((allEmptyPosition.get(newAttemptPosition)) != null) moveToEmpty(newAttemptPosition);
        }
        else
        {
            if ((allEmptyPosition.get(newAttemptPosition)) != null) moveToEmpty(newAttemptPosition);
            else if ((allBoxPosition.get(newAttemptPosition)) != null) pushBigBoxLeft(newAttemptPosition, false);
        }
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
        if (( allBoxPosition.get(newAttemptBoxPosition)) != null) pushBoxUp(newAttemptBoxPosition, true);
        else if ((allEmptyPosition.get(newAttemptBoxPosition)) != null)
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
        /*if ((pos = allWallPosition.get(newAttemptBoxPosition)) != null) log("un mur, on ne fait rien");
        else */if ((allBoxPosition.get(newAttemptBoxPosition)) != null) pushBoxDown(newAttemptBoxPosition, true);
        else if ((allEmptyPosition.get(newAttemptBoxPosition)) != null)
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
        /*if ((pos = allWallPosition.get(newAttemptBoxPosition)) != null) log("un mur, on ne fait rien");
        else */if ((allBoxPosition.get(newAttemptBoxPosition)) != null) pushBoxRight(newAttemptBoxPosition, true);
        else if ((allEmptyPosition.get(newAttemptBoxPosition)) != null)
        {
            //moveBoxToEmpty(newAttemptBoxPosition);
            allEmptyPosition.put(currRPos, ".");
            moveToEmpty(Pair.<Integer>builder().v1(currRPos.getV1()).v2(currRPos.getV2()+1).build());
            allEmptyPosition.remove(newAttemptBoxPosition);
            if (!fromBox) allBoxPosition.remove(newPos);
            allBoxPosition.put(newAttemptBoxPosition, "B");
        }
    }

    boolean pushBigBoxRight(Pair<Integer> newPos, boolean fromBox)
    {
        //log("box to right");
        // from right it can only be  [, so there is a ] just after
        // so work with ]
        Pair<Integer> newAttemptBoxPosition = Pair.<Integer>builder().v1(newPos.getV1()).v2(newPos.getV2()+2).build();
        if ((allBoxPosition.get(newAttemptBoxPosition)) != null)
        {
            if (pushBigBoxRight(newAttemptBoxPosition, true))
            {
                allBoxPosition.remove(newAttemptBoxPosition);
                allBoxPosition.put(newAttemptBoxPosition, "]");
                Pair<Integer> boxPart2 = Pair.<Integer>builder().v1(newPos.getV1()).v2(newPos.getV2()+1).build();
                allBoxPosition.remove(boxPart2);
                allBoxPosition.put(boxPart2, "[");
                return true;
            }
            else return false;
        }
        else if ((allEmptyPosition.get(newAttemptBoxPosition)) != null)
        {
            // move robot
            allEmptyPosition.put(currRPos, ".");
            moveToEmpty(Pair.<Integer>builder().v1(currRPos.getV1()).v2(currRPos.getV2()+1).build());
            
            // move box
            // other box part ]
            allEmptyPosition.remove(newAttemptBoxPosition);
            if (!fromBox) 
            {
                allBoxPosition.remove(newPos);
                Pair<Integer> boxPart2 = Pair.<Integer>builder().v1(newPos.getV1()).v2(newPos.getV2()+1).build();
                allBoxPosition.remove(boxPart2);
            }
            allBoxPosition.put(newAttemptBoxPosition, "]");
            Pair<Integer> boxPart2 = Pair.<Integer>builder().v1(newPos.getV1()).v2(newPos.getV2()+1).build();
            allBoxPosition.put(boxPart2, "[");
            return true;
        }
        else return false;
    }
    
	void pushBoxLeft(Pair<Integer> newPos, boolean fromBox)
	{
	    //log("box to left");
	    Pair<Integer> newAttemptBoxPosition = Pair.<Integer>builder().v1(newPos.getV1()).v2(newPos.getV2()-1).build();
        /*if ((pos = allWallPosition.get(newAttemptBoxPosition)) != null) log("un mur, on ne fait rien");
        else */if ((allBoxPosition.get(newAttemptBoxPosition)) != null) pushBoxLeft(newAttemptBoxPosition, true);
        else if ((allEmptyPosition.get(newAttemptBoxPosition)) != null)
        {
            allEmptyPosition.put(currRPos, ".");
            moveToEmpty(Pair.<Integer>builder().v1(currRPos.getV1()).v2(currRPos.getV2()-1).build());
            allEmptyPosition.remove(newAttemptBoxPosition);
            if (!fromBox) allBoxPosition.remove(newPos);
            allBoxPosition.put(newAttemptBoxPosition, "B");
        }
	}
	
    boolean pushBigBoxLeft(Pair<Integer> newPos, boolean fromBox)
    {
        //log("box to left");
        // from left it can only be  ], so there is a [ just after
        // so work with [
        Pair<Integer> newAttemptBoxPosition = Pair.<Integer>builder().v1(newPos.getV1()).v2(newPos.getV2()-2).build();
        if ((allBoxPosition.get(newAttemptBoxPosition)) != null)
        {
            if (pushBigBoxLeft(newAttemptBoxPosition, true))
            {
                allBoxPosition.remove(newAttemptBoxPosition);
                allBoxPosition.put(newAttemptBoxPosition, "[");
                Pair<Integer> boxPart2 = Pair.<Integer>builder().v1(newPos.getV1()).v2(newPos.getV2()-1).build();
                allBoxPosition.remove(boxPart2);
                allBoxPosition.put(boxPart2, "]");
                return true;
            }
            else return false;
        }
        else if ((allEmptyPosition.get(newAttemptBoxPosition)) != null)
        {
            // move robot
            allEmptyPosition.put(currRPos, ".");
            moveToEmpty(Pair.<Integer>builder().v1(currRPos.getV1()).v2(currRPos.getV2()-1).build());
            
            // move box
            // other box part ]
            allEmptyPosition.remove(newAttemptBoxPosition);
            if (!fromBox) 
            {
                allBoxPosition.remove(newPos);
                Pair<Integer> boxPart2 = Pair.<Integer>builder().v1(newPos.getV1()).v2(newPos.getV2()-1).build();
                allBoxPosition.remove(boxPart2);
            }
            allBoxPosition.put(newAttemptBoxPosition, "[");
            Pair<Integer> boxPart2 = Pair.<Integer>builder().v1(newPos.getV1()).v2(newPos.getV2()-1).build();
            allBoxPosition.put(boxPart2, "]");
            return true;
        }
        else return false;
    }
    
    boolean moveBoxUp(Pair<Integer> box)
    {
        String boxSide = allBoxPosition.get(box);
        
        if (boxSide == null)
        {
            log("null");
        }
        
        if (boxSide.equals("["))
        {
            String x, y;
            if (        (x = allEmptyPosition.get(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()).build())) != null 
                    &&  (y = allEmptyPosition.get(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()+1).build())) != null)
            {
                allBoxPosition.remove(box);
                allBoxPosition.remove(Pair.<Integer>builder().v1(box.getV1()).v2(box.getV2()+1).build());
                allEmptyPosition.put(Pair.<Integer>builder().v1(box.getV1()).v2(box.getV2()).build(), ".");
                allEmptyPosition.put(Pair.<Integer>builder().v1(box.getV1()).v2(box.getV2()+1).build(), ".");
                
                allEmptyPosition.remove(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()).build(), ".");
                allEmptyPosition.remove(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()+1).build(), ".");
                allBoxPosition.put(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()).build(), "[");
                allBoxPosition.put(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()+1).build(), "]");
                
                return true;
            }
            else if (   (x = allWallPosition.get(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()).build())) != null 
                    ||  (y = allWallPosition.get(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()+1).build())) != null
                    ) return false;
            else if (   (x = allBoxPosition.get(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()).build())) != null
                    &&  (y = allBoxPosition.get(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()+1).build())) != null
                    )
            {
               if (x.equals("[") && y.equals("]"))
               {
                   //  [] 
                   //  []
                   if (moveBoxUp(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()).build())) return moveBoxUp(box);
               }
               else if (x.equals("]") && y.equals("["))
               {
               //  [][] 
               //   []     TODO : here there is a bug to undo moves
                   if (moveBoxUp(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()).build()))
                       if (moveBoxUp(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()+1).build())) return moveBoxUp(box);
                       else moveBoxDown(Pair.<Integer>builder().v1(box.getV1()-2).v2(box.getV2()).build());
                   
               }
            }
            else if (   (x = allBoxPosition.get(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()).build())) != null
                    &&  (y = allEmptyPosition.get(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()+1).build())) != null
                    )
            {
                //    []
                //     []
                if (moveBoxUp(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()).build())) return moveBoxUp(box);
            }
            else if (   (x = allEmptyPosition.get(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()).build())) != null
                    &&  (y = allBoxPosition.get(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()+1).build())) != null
                    )
            {
                //      []
                //     []
                if (moveBoxUp(Pair.<Integer>builder().v1(box.getV1()-1).v2(box.getV2()+1).build())) return moveBoxUp(box);
            }
            else log("Cas Inconnu");
        }
        else if (boxSide.equals("]"))
        {
            return moveBoxUp(Pair.<Integer>builder().v1(box.getV1()).v2(box.getV2()-1).build());
        }
        return false;
    }
    
    boolean moveBoxDown(Pair<Integer> box)
    {
        String boxSide = allBoxPosition.get(box);
        
        if (boxSide == null)
        {
            log("null");
        }
        if (boxSide.equals("["))
        {
            String x, y;
            if (        (x = allEmptyPosition.get(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()).build())) != null 
                    &&  (y = allEmptyPosition.get(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()+1).build())) != null)
            {
                allBoxPosition.remove(box);
                allBoxPosition.remove(Pair.<Integer>builder().v1(box.getV1()).v2(box.getV2()+1).build());
                allEmptyPosition.put(Pair.<Integer>builder().v1(box.getV1()).v2(box.getV2()).build(), ".");
                allEmptyPosition.put(Pair.<Integer>builder().v1(box.getV1()).v2(box.getV2()+1).build(), ".");
                
                allEmptyPosition.remove(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()).build(), ".");
                allEmptyPosition.remove(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()+1).build(), ".");
                allBoxPosition.put(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()).build(), "[");
                allBoxPosition.put(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()+1).build(), "]");
                
                return true;
            }
            else if (   (x = allWallPosition.get(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()).build())) != null 
                    ||  (y = allWallPosition.get(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()+1).build())) != null
                    ) return false;
            else if (   (x = allBoxPosition.get(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()).build())) != null
                    &&  (y = allBoxPosition.get(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()+1).build())) != null
                    )
            {
               if (x.equals("[") && y.equals("]"))
               {
                   //  [] 
                   //  []
                   if (moveBoxDown(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()).build())) return moveBoxDown(box);
               }
               else if (x.equals("]") && y.equals("["))
               {
                   //   []
                   //  [][]   TODO : here there is a bug to undo moves
                   if (moveBoxDown(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()).build()))
                       if (moveBoxDown(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()+1).build())) return moveBoxDown(box);
                       else moveBoxUp(Pair.<Integer>builder().v1(box.getV1()+2).v2(box.getV2()).build());
                   
               }
            }
            else if (   (x = allEmptyPosition.get(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()).build())) != null
                    &&  (y = allBoxPosition.get(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()+1).build())) != null
                    )
            {
                //    []
                //     []
                if (moveBoxDown(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()+1).build())) return moveBoxDown(box);
            }
            else if (   (x = allBoxPosition.get(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()).build())) != null
                    &&  (y = allEmptyPosition.get(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()+1).build())) != null
                    )
            {
                //      []
                //     []
                if (moveBoxDown(Pair.<Integer>builder().v1(box.getV1()+1).v2(box.getV2()-1).build())) return moveBoxDown(box);
            }
            else log("Cas inconnu");
        }
        else if (boxSide.equals("]"))
        {
            return moveBoxDown(Pair.<Integer>builder().v1(box.getV1()).v2(box.getV2()-1).build());
        }
        return false;
    }
    
	void rebuildMatrix()
	{
	    for (int i = 0 ; i < matrixSize; i++)
	        for (int j = 0; j < matrixSize; j++)
	            puzzleMatrix[i][j] = 'X';
	    for (Pair<Integer> w : allWallPosition.keySet()) puzzleMatrix[w.getV1()][w.getV2()] = '#';
	    
	    for (Pair<Integer> b : allBoxPosition.keySet())
	    {
	        char box = allBoxPosition.get(b).charAt(0);
	        puzzleMatrix[b.getV1()][b.getV2()] = box;
	    }
	    
	    for (Pair<Integer> e : allEmptyPosition.keySet()) puzzleMatrix[e.getV1()][e.getV2()] = '.';
	    puzzleMatrix[currRPos.getV1()][currRPos.getV2()] = '@';
	}
}
