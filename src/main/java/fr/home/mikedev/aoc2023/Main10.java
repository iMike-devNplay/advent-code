package fr.home.mikedev.aoc2023;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.Pair;

public class Main10 extends MainDay 
{
    int matrixHeight = 140;
    int matrixWidth  = 140;
    char[][] puzzleMatrix = new char[matrixHeight][matrixWidth];
    Map<Pair<Long>, String> browse;
    
    List<Pair<Long>> path;
    
	public Main10(String title, String year) {super(title, year, "10");}
	
	public void initData()
	{
	    browse = new HashMap<Pair<Long>, String>();
	    path = new ArrayList<Pair<Long>>();
	}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
		    int i = 0;
			while((line = reader.readLine()) != null)
			{
			    puzzleMatrix[i] = line.toCharArray();
			    for (int j = 0; j < matrixWidth; j++)
			        browse.put(Pair.<Long>builder().v1(Long.valueOf(i)).v2(Long.valueOf(j)).build(), String.valueOf(puzzleMatrix[i][j]));
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
	    initData();
	    retrieveData();
	    
	    Pair<Long> nextPoint = Pair.<Long>builder().v1(Long.valueOf(25)).v2(Long.valueOf(93)).o("D").build();
	    //Pair<Long> nextPoint = Pair.<Long>builder().v1(Long.valueOf(8)).v2(Long.valueOf(13)).o("D").build();
	    while(!path.contains(nextPoint))
	    {
	        path.add(nextPoint);
	        nextPoint = getNext(nextPoint, nextPoint.getO());
	    }
		setResultPart1(path.size()/2); // 6956
	}
	
	public void doPart2()
	{
		//retrieveData();
	    //first : set all item not in path to ground
	    for (int l = 0; l < matrixHeight; l++)
        {
            for (int c = 0; c < matrixWidth; c++)
            {
                Pair<Long> p = Pair.<Long>builder().v1(Long.valueOf(l)).v2(Long.valueOf(c)).o(String.valueOf(puzzleMatrix[l][c])).build();
                if (!path.contains(p)) puzzleMatrix[l][c] = '.';
            }
        }
	    
	    int innerCount = 0;
	    for (int l = 0; l < matrixHeight; l++)
	    {
	        for (int c = 0; c < matrixWidth; c++)
	        {
	            Pair<Long> p = Pair.<Long>builder().v1(Long.valueOf(l)).v2(Long.valueOf(c)).o(String.valueOf(puzzleMatrix[l][c])).build();
	            if (!path.contains(p))
	            {
	                int edgeCount = 0;
	                for (int c2 = c+1; c2 < matrixWidth; c2++)
	                {
	                    if (puzzleMatrix[l][c2] == 'S' || puzzleMatrix[l][c2] == 'F' || puzzleMatrix[l][c2] == '7' || puzzleMatrix[l][c2] == '|') edgeCount++;
	                }
	                if (edgeCount%2 != 0) innerCount++;
	            }
	        }
	    }
	    
	    setResultPart2(innerCount);  // 455
	}
	
	Pair<Long> getNext(Pair<Long> pos, String direction)
    {
        Pair<Long> currentPos = Pair.<Long>builder().v1(Long.valueOf(pos.getV1())).v2(Long.valueOf(pos.getV2())).build();
        String currentPosDir = browse.get(currentPos);
        
        switch (currentPosDir.toCharArray()[0])
        {
            case '|':
                if (direction.equals("D")) 
                {   
                    Pair<Long> nextPos = Pair.<Long>builder().v1(Long.valueOf(pos.getV1())+1).v2(Long.valueOf(pos.getV2())).build();
                    String nextPosDir = browse.get(nextPos);
                    if (nextPosDir != null)
                    {
                        if (nextPosDir.equals("|") || nextPosDir.equals("J") || nextPosDir.equals("L"))
                            return Pair.<Long>builder().v1(nextPos.getV1()).v2(nextPos.getV2()).o("D").build();
                    }
                }
                else if (direction.equals("U"))
                {
                    Pair<Long> nextPos = Pair.<Long>builder().v1(Long.valueOf(pos.getV1())-1).v2(Long.valueOf(pos.getV2())).build();
                    String nextPosDir = browse.get(nextPos);
                    if (nextPosDir != null)
                    {
                        if (nextPosDir.equals("|") || nextPosDir.equals("7") || nextPosDir.equals("F")) 
                            return Pair.<Long>builder().v1(nextPos.getV1()).v2(nextPos.getV2()).o("U").build();
                    }                   
                }
                break;
            case '-':
                if (direction.equals("R")) 
                {   
                    Pair<Long> nextPos = Pair.<Long>builder().v1(Long.valueOf(pos.getV1())).v2(Long.valueOf(pos.getV2()+1)).build();
                    String nextPosDir = browse.get(nextPos);
                    if (nextPosDir != null)
                    {
                        if (nextPosDir.equals("-") || nextPosDir.equals("J") || nextPosDir.equals("7")  || nextPosDir.equals("S"))
                            return Pair.<Long>builder().v1(nextPos.getV1()).v2(nextPos.getV2()).o("R").build();
                    }
                }
                else if (direction.equals("L"))
                {
                    Pair<Long> nextPos = Pair.<Long>builder().v1(Long.valueOf(pos.getV1())).v2(Long.valueOf(pos.getV2()-1)).build();
                    String nextPosDir = browse.get(nextPos);
                    if (nextPosDir != null)
                    {
                        if (nextPosDir.equals("-") || nextPosDir.equals("L") || nextPosDir.equals("F"))
                            return Pair.<Long>builder().v1(nextPos.getV1()).v2(nextPos.getV2()).o("L").build();
                    }                   
                }
                break;
            case 'L':
                if (direction.equals("D")) 
                {   
                    Pair<Long> nextPos = Pair.<Long>builder().v1(Long.valueOf(pos.getV1())).v2(Long.valueOf(pos.getV2()+1)).build();
                    String nextPosDir = browse.get(nextPos);
                    if (nextPosDir != null)
                    {
                        if (nextPosDir.equals("-") || nextPosDir.equals("J") || nextPosDir.equals("7")  || nextPosDir.equals("S")) // I know where it starts and where it ends
                            return Pair.<Long>builder().v1(nextPos.getV1()).v2(nextPos.getV2()).o("R").build();
                    }
                }
                else if (direction.equals("L"))
                {
                    Pair<Long> nextPos = Pair.<Long>builder().v1(Long.valueOf(pos.getV1()-1)).v2(Long.valueOf(pos.getV2())).build();
                    String nextPosDir = browse.get(nextPos);
                    if (nextPosDir != null)
                    {
                        if (nextPosDir.equals("|") || nextPosDir.equals("7") || nextPosDir.equals("F"))
                            return Pair.<Long>builder().v1(nextPos.getV1()).v2(nextPos.getV2()).o("U").build();
                    }                   
                }
                break;
            case 'J':
                if (direction.equals("R")) 
                {   
                    Pair<Long> nextPos = Pair.<Long>builder().v1(Long.valueOf(pos.getV1()-1)).v2(Long.valueOf(pos.getV2())).build();
                    String nextPosDir = browse.get(nextPos);
                    if (nextPosDir != null)
                    {
                        if (nextPosDir.equals("|") || nextPosDir.equals("F") || nextPosDir.equals("7"))
                            return Pair.<Long>builder().v1(nextPos.getV1()).v2(nextPos.getV2()).o("U").build();
                    }
                }
                else if (direction.equals("D"))
                {
                    Pair<Long> nextPos = Pair.<Long>builder().v1(Long.valueOf(pos.getV1())).v2(Long.valueOf(pos.getV2()-1)).build();
                    String nextPosDir = browse.get(nextPos);
                    if (nextPosDir != null)
                    {
                        if (nextPosDir.equals("-") || nextPosDir.equals("L") || nextPosDir.equals("F"))
                            return Pair.<Long>builder().v1(nextPos.getV1()).v2(nextPos.getV2()).o("L").build();
                    }                   
                }
                break;
            case '7','S':
                if (direction.equals("R")) 
                {   
                    Pair<Long> nextPos = Pair.<Long>builder().v1(Long.valueOf(pos.getV1()+1)).v2(Long.valueOf(pos.getV2())).build();
                    String nextPosDir = browse.get(nextPos);
                    if (nextPosDir != null)
                    {
                        if (nextPosDir.equals("|") || nextPosDir.equals("L") || nextPosDir.equals("J"))
                            return Pair.<Long>builder().v1(nextPos.getV1()).v2(nextPos.getV2()).o("D").build();
                    }
                }
                else if (direction.equals("U"))
                {
                    Pair<Long> nextPos = Pair.<Long>builder().v1(Long.valueOf(pos.getV1())).v2(Long.valueOf(pos.getV2()-1)).build();
                    String nextPosDir = browse.get(nextPos);
                    if (nextPosDir != null)
                    {
                        if (nextPosDir.equals("-") || nextPosDir.equals("L") || nextPosDir.equals("F"))
                            return Pair.<Long>builder().v1(nextPos.getV1()).v2(nextPos.getV2()).o("L").build();
                    }                   
                }
                break;
            case 'F':
                if (direction.equals("L")) 
                {   
                    Pair<Long> nextPos = Pair.<Long>builder().v1(Long.valueOf(pos.getV1()+1)).v2(Long.valueOf(pos.getV2())).build();
                    String nextPosDir = browse.get(nextPos);
                    if (nextPosDir != null)
                    {
                        if (nextPosDir.equals("|") || nextPosDir.equals("L") || nextPosDir.equals("J"))
                            return Pair.<Long>builder().v1(nextPos.getV1()).v2(nextPos.getV2()).o("D").build();
                    }
                }
                else if (direction.equals("U"))
                {
                    Pair<Long> nextPos = Pair.<Long>builder().v1(Long.valueOf(pos.getV1())).v2(Long.valueOf(pos.getV2()+1)).build();
                    String nextPosDir = browse.get(nextPos);
                    if (nextPosDir != null)
                    {
                        if (nextPosDir.equals("-") || nextPosDir.equals("J") || nextPosDir.equals("7"))
                            return Pair.<Long>builder().v1(nextPos.getV1()).v2(nextPos.getV2()).o("R").build();
                    }                   
                }
                break;
        }
        return null; //Pair.<Long>builder().o("S").build();
    }
}
