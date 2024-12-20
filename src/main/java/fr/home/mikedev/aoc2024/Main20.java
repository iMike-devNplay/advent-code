package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.Pair;

public class Main20 extends MainDay 
{
	int matrixSize = 141;
	char[][] puzzleMatrix = new char[matrixSize][matrixSize];
	Pair<Integer> startPoint;
	Pair<Integer> endPoint;
	Pair<Integer> current;
	Map<Pair<Integer>, String> walls;
	Map<Pair<Integer>, String> paths;
	List<Pair<Integer>> uniquePath;
	Map<Integer, List<Pair<Integer>>> path;
	
	public Main20(String title, String year) {super(title, year, "20");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
			int l = 0;
            while((line = reader.readLine()) != null)
            {
            	for (int c = 0; c < line.length(); c++)
                	if (line.charAt(c) == '#') walls.put(Pair.<Integer>builder().v1(l).v2(c).build(), "#");
                	else if (line.charAt(c) == '.') paths.put(Pair.<Integer>builder().v1(l).v2(c).build(), ".");
                	else if (line.charAt(c) == 'S')
                	{
                		startPoint = Pair.<Integer>builder().v1(l).v2(c).build();
                		paths.put(Pair.<Integer>builder().v1(l).v2(c).build(), ".");
                	}
                	else if (line.charAt(c) == 'E')
                	{
                		endPoint = Pair.<Integer>builder().v1(l).v2(c).build();
                		paths.put(Pair.<Integer>builder().v1(l).v2(c).build(), ".");
                	}
                l++;
            }
            current = startPoint.clone();
            current.setO(">");
            rebuildMatrix();
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
			ex.printStackTrace();
		}
	}
	
	public void doPart1()
	{
		walls = new HashMap<Pair<Integer>, String>();
		paths = new HashMap<Pair<Integer>, String>();

		retrieveData();
		
		path = new HashMap<Integer, List<Pair<Integer>>>();
		buildUniquePath();
		//log(MatrixUtils.matrixToString(puzzleMatrix));
		
		int fullDistance = distance(startPoint, endPoint);
		log("FullDistance = " + fullDistance);
		
		int count = 0;
		int minGain = 100;
		for (Pair<Integer> p0 : uniquePath)
		{
			// TOP
			Pair<Integer> p1 = Pair.<Integer>builder().v1(p0.getV1()-1).v2(p0.getV2()).build();
			Pair<Integer> p2 = Pair.<Integer>builder().v1(p0.getV1()-2).v2(p0.getV2()).build();
			if (isValidShortcut(startPoint, p1, p2))if ((distance(p0, p2)-2) >= minGain) count++;

			//RIGHT
			p1 = Pair.<Integer>builder().v1(p0.getV1()).v2(p0.getV2()+1).build();
			p2 = Pair.<Integer>builder().v1(p0.getV1()).v2(p0.getV2()+2).build();
			if (isValidShortcut(startPoint, p1, p2)) if ((distance(p0, p2)-2) >= minGain) count++;
			
			//BOTTOM
			p1 = Pair.<Integer>builder().v1(p0.getV1()+1).v2(p0.getV2()).build();
			p2 = Pair.<Integer>builder().v1(p0.getV1()+2).v2(p0.getV2()).build();
			if (isValidShortcut(startPoint, p1, p2)) if ((distance(p0, p2)-2) >= minGain) count++; 
			
			//LEFT
			p1 = Pair.<Integer>builder().v1(p0.getV1()).v2(p0.getV2()-1).build();
			p2 = Pair.<Integer>builder().v1(p0.getV1()).v2(p0.getV2()-2).build();
			if (isValidShortcut(startPoint, p1, p2)) if ((distance(p0, p2)-2) >= minGain) count++;
		}
				
		displayResultPart1(count); // 1404
	}
	
	public void doPart2()
	{
		//retrieveData();
		
		int fullDistance = distance(startPoint, endPoint);
		log("FullDistance = " + fullDistance);
		int count = 0;
		int minGain = 100;
		for (Pair<Integer> p0 : uniquePath)
		{
			for (Pair<Integer> p2 : uniquePath)
			{
				String pathString = paths.get(p2);
				if (pathString != null)
				{
					int gridDistance = distanceGrid(p0, p2);
					if (gridDistance <= 20)
					{
						int pathDistance = distance(p0, p2);
						if ((pathDistance-gridDistance) >= minGain) count++;
					}
				}
			}
		}
		
		displayResultPart2(count);
	}
	
	void buildUniquePath()
	{
		uniquePath = new ArrayList<Pair<Integer>>();
		uniquePath.add(startPoint);
		
		Pair<Integer> nextP;
		nextP = nextPoint(startPoint);
		while(nextP != null) 
		{
			uniquePath.add(nextP);
			nextP = nextPoint(nextP);
		}
		path.put(0, uniquePath);
	}
	
	Pair<Integer> nextPoint(Pair<Integer> p)
	{
		Pair<Integer> nextP = Pair.<Integer>builder().v1(p.getV1()-1).v2(p.getV2()).build();
		if (paths.get(nextP) != null && paths.get(nextP).equals(".") && !uniquePath.contains(nextP)) return nextP;
		
		nextP = Pair.<Integer>builder().v1(p.getV1()).v2(p.getV2()+1).build();
		if (paths.get(nextP) != null && paths.get(nextP).equals(".") && !uniquePath.contains(nextP)) return nextP;
		
		nextP = Pair.<Integer>builder().v1(p.getV1()+1).v2(p.getV2()).build();
		if (paths.get(nextP) != null && paths.get(nextP).equals(".") && !uniquePath.contains(nextP)) return nextP;
		
		nextP = Pair.<Integer>builder().v1(p.getV1()).v2(p.getV2()-1).build();
		if (paths.get(nextP) != null && paths.get(nextP).equals(".") && !uniquePath.contains(nextP)) return nextP;
		
		return null;
	}
	
	boolean isValidShortcut(Pair<Integer> p0, Pair<Integer> p1, Pair<Integer> p2)
	{
		
		return (distance(p0, p2) > 0 && paths.containsKey(p0) && walls.containsKey(p1) && paths.containsKey(p2));
	}
		
	int distance(Pair<Integer> p1, Pair<Integer> p2)
	{
		int p1Loc = uniquePath.indexOf(p1);
		int p2Loc = uniquePath.indexOf(p2);
		return p2Loc-p1Loc;
	}
	
	int distanceGrid(Pair<Integer> p1, Pair<Integer> p2)
	{
		return Math.abs(p1.getV1()-p2.getV1()) + Math.abs(p1.getV2()-p2.getV2());
	}
	
	void rebuildMatrix()
	{
	    for (int i = 0 ; i < matrixSize; i++)
	        for (int j = 0; j < matrixSize; j++)
	            puzzleMatrix[i][j] = 'X';
	    for (Pair<Integer> w : walls.keySet()) puzzleMatrix[w.getV1()][w.getV2()] = '#';
	    for (Pair<Integer> b : paths.keySet()) puzzleMatrix[b.getV1()][b.getV2()] = '.';
	    
	    puzzleMatrix[startPoint.getV1()][startPoint.getV2()] = 'S';
	    puzzleMatrix[endPoint.getV1()][endPoint.getV2()] = 'E';
	}
}
