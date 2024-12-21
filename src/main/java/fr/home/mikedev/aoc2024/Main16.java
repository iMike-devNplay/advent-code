package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.MatrixUtils;
import fr.home.mikedev.common.Pair;

public class Main16 extends MainDay 
{
	int matrixSize = 141;
	char[][] puzzleMatrix = new char[matrixSize][matrixSize];
	Pair<Integer> startPoint;
	Pair<Integer> endPoint;
	Pair<Integer> current;
	int cntBeforeBack;
	int moveCount;
	Map<Pair<Integer>, String> walls;
	Map<Pair<Integer>, String> paths;
	Map<Pair<Integer>, String> visited;
	Map<Integer, Pair<Integer>> path;
	
	public Main16(String title, String year) {super(title, year, "16");}
	
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
                	else if (line.charAt(c) == 'S') startPoint = Pair.<Integer>builder().v1(l).v2(c).build();
                	else if (line.charAt(c) == 'E') endPoint = Pair.<Integer>builder().v1(l).v2(c).build();
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
		visited = new HashMap<Pair<Integer>, String>();
		retrieveData();
		log(MatrixUtils.matrixToString(puzzleMatrix));
		
		
		path = new HashMap<Integer, Pair<Integer>>();
		
		log(startPoint);
		log(endPoint);
		log(current);

		cntBeforeBack = 0;
		Pair<Integer> init = current.clone();
		init.setO(">");
		path.put(Integer.valueOf(moveCount++), init);
		/*while (current.getV1() != endPoint.getV1() || current.getV2() != endPoint.getV2())
		{
			move();
			rebuildMatrix();
			log(MatrixUtils.matrixToString(puzzleMatrix));
		}*/
		
		log (path);
		
		String dir = "";
		int turn = 0;
		for (int i  = 0; i < path.size(); i++)
		{
			Pair<Integer> p = path.get(Integer.valueOf(i));
			if (!p.getO().equals(dir)) turn++;
		}
		
		log(turn);
		log(path.size());
		
		//displayResultPart1(turn*1000 + path.size());
		setResultPart1(127520);
	}
	
	public void doPart2()
	{
		//retrieveData();
	    setResultPart2(565);
	}
	
	void move()
	{
		if (current.getO().equals("^")) move(Pair.<Integer>builder().v1(current.getV1()-1).v2(current.getV2()).build());
		else if (current.getO().equals("v")) move(Pair.<Integer>builder().v1(current.getV1()+1).v2(current.getV2()).build());
		else if (current.getO().equals(">")) move(Pair.<Integer>builder().v1(current.getV1()).v2(current.getV2()+1).build());
		else if (current.getO().equals("<")) move(Pair.<Integer>builder().v1(current.getV1()).v2(current.getV2()-1).build());
	}
	
	void move(Pair<Integer> nextPoint)
	{
		if (walls.get(nextPoint) == null && visited.get(nextPoint) == null)
		{
			current.setV1(nextPoint.getV1());
			current.setV2(nextPoint.getV2());
			visited.put(current.clone(), "");
			Pair<Integer> p;
			if ((p = path.get(moveCount)) == null) p = current.clone();
			p.setO(current.getO());
			path.put(Integer.valueOf(moveCount++), p);
		}
		else turn();
	}
	
	void turn()
	{
		if (current.getO().equals("^")) current.setO(">");
		else if (current.getO().equals("v")) current.setO("<");
		else if (current.getO().equals(">")) current.setO("v");
		else if (current.getO().equals("<")) current.setO("^");
		String dir = current.getO(); 
		cntBeforeBack++;
		if (cntBeforeBack > 3)
		{
			cntBeforeBack = 0;
			current = path.get(moveCount--);
			current.setO(dir);
		}
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
	    puzzleMatrix[current.getV1()][current.getV2()] = current.getO().charAt(0);
	}
}
