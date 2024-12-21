package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.MatrixUtils;
import fr.home.mikedev.common.Pair;

public class Main18 extends MainDay 
{
    int matrixSize = 71; // 71
    int numBytes = 1024; // 1024
    int numMaxBytes = 3450; // 3450
    List<Pair<Integer>> bytes;
    char[][] puzzleMatrix = new char[matrixSize][matrixSize];
    Pair<Integer> startPoint = Pair.<Integer>builder().v1(Integer.valueOf(0)).v2(Integer.valueOf(0)).build();
    Pair<Integer> endPoint = Pair.<Integer>builder().v1(Integer.valueOf(matrixSize-1)).v2(Integer.valueOf(matrixSize-1)).build();
    
    Map<Integer, List<Pair<Integer>>> paths;
    
	public Main18(String title, String year) {super(title, year, "18");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
            while((line = reader.readLine()) != null)
            {
                String[] s = line.split(",");
                bytes.add(Pair.<Integer>builder().v1(Integer.valueOf(s[1])).v2(Integer.valueOf(s[0])).build());
            }
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
		}
	}
	
	public void doPart1()
	{
	    bytes = new ArrayList<Pair<Integer>>();
		retrieveData();
		
		initEmptyMatrix();
		
		for (int i = 0; i < numBytes; i++)
		    puzzleMatrix[bytes.get(i).getV1().intValue()][bytes.get(i).getV2().intValue()] = '#';
		
		paths = new TreeMap<Integer, List<Pair<Integer>>>();
		Map<Pair<Integer>, Integer> distance = bfs();
		
		setResultPart1(distance.get(endPoint));
	}
	
	public void doPart2()
	{
	    String failingByte = "";
	    for (int i = numBytes; i < 3450 && failingByte.equals(""); i++)
	    {
            puzzleMatrix[bytes.get(i).getV1().intValue()][bytes.get(i).getV2().intValue()] = '#';
            Map<Pair<Integer>, Integer> distance = bfs();
            if (distance.get(endPoint) == null) failingByte = bytes.get(i).getV2() + "," + bytes.get(i).getV1();
	    }
	    setResultPart2(failingByte);
	}
	
	Map<Pair<Integer>, Integer> bfs()
	{
	    Queue<Pair<Integer>> q = new LinkedList<Pair<Integer>>();
	    List<Pair<Integer>> visited = new ArrayList<Pair<Integer>>();
	    Map<Pair<Integer>, Integer> distance = new HashMap<Pair<Integer>, Integer>();
	    
	    visited.add(startPoint);
	    distance.put(startPoint, 0);
	    q.add(startPoint);
	    
	    while (!q.isEmpty())
	    {
	        Pair<Integer> currentPoint = q.poll();
	        Integer d = distance.get(currentPoint);
	        
	        Pair<Integer> upPoint = up(currentPoint);
	        if (!MatrixUtils.isOutsideMatrix(upPoint, matrixSize) && puzzleMatrix[upPoint.getV1()][upPoint.getV2()] != '#' && !visited.contains(upPoint))
	        {
	            visited.add(upPoint);
	            q.add(upPoint);
	            
	            Integer existingD;
	            if ((existingD = distance.get(upPoint)) != null && existingD > d+1) distance.put(upPoint, d+1);
	            else distance.put(upPoint, d+1);
	        }
	        
	        Pair<Integer> downPoint = down(currentPoint);
            if (!MatrixUtils.isOutsideMatrix(downPoint, matrixSize) && puzzleMatrix[downPoint.getV1()][downPoint.getV2()] != '#' && !visited.contains(downPoint))
            {
                visited.add(downPoint);
                q.add(downPoint);
                
                Integer existingD;
                if ((existingD = distance.get(downPoint)) != null && existingD > d+1) distance.put(downPoint, d+1);
                else distance.put(downPoint, d+1);
            }
            
            Pair<Integer> leftPoint = left(currentPoint);
            if (!MatrixUtils.isOutsideMatrix(leftPoint, matrixSize) && puzzleMatrix[leftPoint.getV1()][leftPoint.getV2()] != '#' && !visited.contains(leftPoint))
            {
                visited.add(leftPoint);
                q.add(leftPoint);
                
                Integer existingD;
                if ((existingD = distance.get(leftPoint)) != null && existingD > d+1) distance.put(leftPoint, d+1);
                else distance.put(leftPoint, d+1);
            }
            
            Pair<Integer> rightPoint = right(currentPoint);
            if (!MatrixUtils.isOutsideMatrix(rightPoint, matrixSize) && puzzleMatrix[rightPoint.getV1()][rightPoint.getV2()] != '#' && !visited.contains(rightPoint))
            {
                visited.add(rightPoint);
                q.add(rightPoint);
                
                Integer existingD;
                if ((existingD = distance.get(rightPoint)) != null && existingD > d+1) distance.put(rightPoint, d+1);
                else distance.put(rightPoint, d+1);
            }
	    }
	    return distance;
	}
	
	Pair<Integer> up(Pair<Integer> p)
	{
	    return Pair.<Integer>builder().v1(p.getV1()-1).v2(p.getV2()).build();
	}
	
	Pair<Integer> down(Pair<Integer> p)
    {
        return Pair.<Integer>builder().v1(p.getV1()+1).v2(p.getV2()).build();
    }
	
	Pair<Integer> right(Pair<Integer> p)
    {
        return Pair.<Integer>builder().v1(p.getV1()).v2(p.getV2()+1).build();
    }
	
	Pair<Integer> left(Pair<Integer> p)
    {
        return Pair.<Integer>builder().v1(p.getV1()).v2(p.getV2()-1).build();
    }
	
	void initEmptyMatrix()
	{
        for (int l = 0 ; l < matrixSize; l++)
            for (int c = 0 ; c < matrixSize; c++)
                puzzleMatrix[l][c] = '.';
        puzzleMatrix[startPoint.getV1()][startPoint.getV2()] = 'S';
        puzzleMatrix[endPoint.getV1()][endPoint.getV2()] = 'E';
	}
}
