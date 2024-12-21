package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.Pair;

public class Main12 extends MainDay 
{
	private int matrixSize = 140;
	private char[][] puzzleMatrix = new char[matrixSize][matrixSize];
	
	private Map<String, List<Pair<Integer>>> plots;
	private Map<String, Long> fences;
	private Map<Pair<Integer>, String> plotGroups;
	private long nbFences;
	private long groupNumber;
	
	public Main12(String title, String year) {super(title, year, "12");}
	
	public void retrieveData()
	{
		String line = null;	
		int i = 0;
		try(BufferedReader reader = this.getReader())
		{
			while ((line = reader.readLine()) != null)
			{
				puzzleMatrix[i++] = line.toCharArray();
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
		
		plots = new HashMap<String, List<Pair<Integer>>>();
		fences = new HashMap<String, Long>();
		plotGroups = new HashMap<Pair<Integer>, String>();
		
		buildFences(false);
		
		long sum = 0;
		for (String g : plots.keySet())
			sum += fences.get(g) * plots.get(g).size();			
		
		setResultPart1(sum);
	}
	
	public void doPart2()
	{
		//retrieveData();
		long sum = 0;
		for (String group : plots.keySet())
		{
			int size = plots.get(group).size();
			int sides = 0;
			for (Pair<Integer> plot : plots.get(group))
			{
				if (checkAngleTL(group, plot, false)) 	sides++;
				if (checkAngleTL(group, plot, true)) 	sides++;
				if (checkAngleBL(group, plot, false)) 	sides++;
				if (checkAngleBL(group, plot, true)) 	sides++;
				if (checkAngleTR(group, plot, false)) 	sides++;
				if (checkAngleTR(group, plot, true)) 	sides++;
				if (checkAngleBR(group, plot, false)) 	sides++;
				if (checkAngleBR(group, plot, true)) 	sides++;
			}
			sum += size*sides;
		}
		
		setResultPart2(sum);
	}
	
	void buildFences(boolean withDiscount)
	{
		for (int l = 0 ; l < puzzleMatrix.length; l++)
			for (int c = 0 ; c < puzzleMatrix[l].length; c++)
			{
				String plot = String.valueOf(puzzleMatrix[l][c]);
				Pair<Integer> currentPlot = Pair.<Integer>builder().v1(Integer.valueOf(l)).v2(Integer.valueOf(c)).o(plot).build();
				nbFences = 0;
				String groupName = checkAll(null, currentPlot);
				fences.put(groupName, Long.valueOf(nbFences));
			}
	}
	
	String checkAll(String groupName, Pair<Integer> plot)
	{
		String currentGroup = plotGroups.get(plot);
		if (currentGroup == null)
		{
			if (groupName == null)
			{
				groupName = plot.getO() + groupNumber++;
				plotGroups.put(plot, groupName);
			}
			else plotGroups.put(plot, groupName);
			
			List<Pair<Integer>> list = plots.get(groupName);
			if (list == null) list = new ArrayList<Pair<Integer>>();
			list.add(plot);
			plots.put(groupName, list);	
			
			Pair<Integer> p;
			if ((p = checkUp(plot)) != null) checkAll(groupName, p); else nbFences++;
			if ((p = checkDown(plot)) != null) checkAll(groupName, p); else nbFences++;
			if ((p = checkLeft(plot)) != null) checkAll(groupName, p); else nbFences++;
			if ((p = checkRight(plot)) != null) checkAll(groupName, p); else nbFences++;
		}
		return groupName;
	}
	
	Pair<Integer> checkUp(Pair<Integer> plot)
	{
		if (plot.getV1()-1 < 0) return null;
		else
		{
			int l = plot.getV1()-1 < 0 ? 0 : plot.getV1()-1;
			int c = plot.getV2();
			char up = puzzleMatrix[l][c];
			return plot.getO().equals(String.valueOf(up)) ? Pair.<Integer>builder().v1(Integer.valueOf(l)).v2(Integer.valueOf(c)).o(plot.getO()).build() : null;
		}
	}
	Pair<Integer> checkDown(Pair<Integer> plot)
	{

		if (plot.getV1()+1 > matrixSize-1) return null;
		else
		{
			int l = plot.getV1()+1 > matrixSize-1 ? 0 : plot.getV1()+1;
			int c = plot.getV2();
			char down = puzzleMatrix[l][c];
			return plot.getO().equals(String.valueOf(down)) ? Pair.<Integer>builder().v1(Integer.valueOf(l)).v2(Integer.valueOf(c)).o(plot.getO()).build() : null;
		}
	}
	Pair<Integer> checkLeft(Pair<Integer> plot)
	{
		if (plot.getV2()-1 < 0) return null;
		else
		{
			int l = plot.getV1();
			int c = plot.getV2()-1 < 0 ? 0 : plot.getV2()-1;
			char left = puzzleMatrix[l][c];
			return plot.getO().equals(String.valueOf(left)) ? Pair.<Integer>builder().v1(Integer.valueOf(l)).v2(Integer.valueOf(c)).o(plot.getO()).build() : null;
		}
	}
	Pair<Integer> checkRight(Pair<Integer> plot)
	{
		if (plot.getV2()+1 > matrixSize-1) return null;
		else
		{
			int l = plot.getV1();
			int c = plot.getV2()+1 > matrixSize-1 ? 0 : plot.getV2()+1;
			char right = puzzleMatrix[l][c];
			return plot.getO().equals(String.valueOf(right)) ? Pair.<Integer>builder().v1(Integer.valueOf(l)).v2(Integer.valueOf(c)).o(plot.getO()).build() : null;
		}
	}
		
	boolean checkAngleTL(String group, Pair<Integer> plot, boolean interior)
	{
		/* l-1,c-1    l-1,c
		 * l,c-1      l,c 
		 */
		int p1l = plot.getV1()-1; 
		int p1c = plot.getV2()-1;
		
		int p2l = plot.getV1()-1; 
		int p2c = plot.getV2();
		
		int p3l = plot.getV1(); 
		int p3c = plot.getV2()-1;
		
		if (interior) return checkAngleInt(group, p1l, p1c, p2l, p2c, p3l, p3c, plot.getO());
		else return checkAngleExt(group, p1l, p1c, p2l, p2c, p3l, p3c, plot.getO());
	}
	
	boolean checkAngleTR(String group, Pair<Integer> plot, boolean interior)
	{
		/* l-1,c    l-1,c+1
		 * l,c        l,c+1 
		 */
		int p1l = plot.getV1()-1; 
		int p1c = plot.getV2()+1;
		
		int p2l = plot.getV1(); 
		int p2c = plot.getV2()+1;
		
		int p3l = plot.getV1()-1; 
		int p3c = plot.getV2();
		
		if (interior) return checkAngleInt(group, p1l, p1c, p2l, p2c, p3l, p3c, plot.getO());
		else return checkAngleExt(group, p1l, p1c, p2l, p2c, p3l, p3c, plot.getO());
	}
	
	boolean checkAngleBL(String group, Pair<Integer> plot, boolean interior)
	{
		/* l,c-1    l,c
		 * l+1,c-1    l+1,c 
		 */
		int p1l = plot.getV1()+1; 
		int p1c = plot.getV2()-1;
		
		int p2l = plot.getV1(); 
		int p2c = plot.getV2()-1;
		
		int p3l = plot.getV1()+1; 
		int p3c = plot.getV2();
		
		if (interior) return checkAngleInt(group, p1l, p1c, p2l, p2c, p3l, p3c, plot.getO());
		else return checkAngleExt(group, p1l, p1c, p2l, p2c, p3l, p3c, plot.getO());
	}
	
	boolean checkAngleBR(String group, Pair<Integer> plot, boolean interior)
	{
		/* l,c        l,c+1
		 * l+1,c      l+1,c+1 
		 */
		int p1l = plot.getV1()+1; 
		int p1c = plot.getV2()+1;
		
		int p2l = plot.getV1()+1; 
		int p2c = plot.getV2();
		
		int p3l = plot.getV1(); 
		int p3c = plot.getV2()+1;
		
		if (interior) return checkAngleInt(group, p1l, p1c, p2l, p2c, p3l, p3c, plot.getO());
		else return checkAngleExt(group, p1l, p1c, p2l, p2c, p3l, p3c, plot.getO());
	}
	
	boolean checkAngleInt(String group, int p1l , int p1c, int p2l, int p2c, int p3l, int p3c, String value)
	{
		String /*groupP1 = "", */groupP2 = "", groupP3 = "";
		
		//if (!isOutsideMatrix(p1l, p1c)) groupP1 = plotGroups.get(Pair.<Integer>builder().v1(p1l).v2(p1c).o(String.valueOf(puzzleMatrix[p1l][p1c])).build());
		if (!isOutsideMatrix(p2l, p2c)) groupP2 = plotGroups.get(Pair.<Integer>builder().v1(p2l).v2(p2c).o(String.valueOf(puzzleMatrix[p2l][p2c])).build());
		if (!isOutsideMatrix(p3l, p3c)) groupP3 = plotGroups.get(Pair.<Integer>builder().v1(p3l).v2(p3c).o(String.valueOf(puzzleMatrix[p3l][p3c])).build());
		
		if (
				/*(isOutsideMatrix(p1l, p1c) || !String.valueOf(puzzleMatrix[p1l][p1c]).equals(value) || !group.equals(groupP1))
			&&	*/(isOutsideMatrix(p2l, p2c) || !String.valueOf(puzzleMatrix[p2l][p2c]).equals(value) || !group.equals(groupP2))
			&&	(isOutsideMatrix(p3l, p3c) || !String.valueOf(puzzleMatrix[p3l][p3c]).equals(value) || !group.equals(groupP3))
			) return true;
		else return false;
	}
	
	boolean checkAngleExt(String group, int p1l , int p1c, int p2l, int p2c, int p3l, int p3c, String value)
	{
		String groupP1 = "", groupP2 = "", groupP3 = "";
		
		if (!isOutsideMatrix(p1l, p1c)) groupP1 = plotGroups.get(Pair.<Integer>builder().v1(p1l).v2(p1c).o(String.valueOf(puzzleMatrix[p1l][p1c])).build());
		if (!isOutsideMatrix(p2l, p2c)) groupP2 = plotGroups.get(Pair.<Integer>builder().v1(p2l).v2(p2c).o(String.valueOf(puzzleMatrix[p2l][p2c])).build());
		if (!isOutsideMatrix(p3l, p3c)) groupP3 = plotGroups.get(Pair.<Integer>builder().v1(p3l).v2(p3c).o(String.valueOf(puzzleMatrix[p3l][p3c])).build());
		
		if (
				(isOutsideMatrix(p1l, p1c) || !String.valueOf(puzzleMatrix[p1l][p1c]).equals(value) || !group.equals(groupP1))
			&&	(!isOutsideMatrix(p2l, p2c) && String.valueOf(puzzleMatrix[p2l][p2c]).equals(value) && group.equals(groupP2))
			&&	(!isOutsideMatrix(p3l, p3c) && String.valueOf(puzzleMatrix[p3l][p3c]).equals(value) && group.equals(groupP3))
			) return true;
		else return false;
	}
	
	boolean isOutsideMatrix(Pair<Integer> plot)
	{
		if (plot.getV1() < 0 || plot.getV1() > matrixSize-1 || plot.getV2() < 0 || plot.getV2() > matrixSize-1) return true;
		else return false;
	}
	
	boolean isOutsideMatrix(int l, int c)
	{
		if (l < 0 || l > matrixSize-1 || c < 0 || c > matrixSize-1) return true;
		else return false;
	}
}
