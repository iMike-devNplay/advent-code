package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.Pair;


public class Main08 extends MainDay 
{
	private char[][] puzzleMatrix = new char[50][50];

	int part1Result;
	private Map<String, List<Pair<Integer>>> antennas;
	int countAntinode;
	int countAntinodeAntenna;
	private Map<Pair<Integer>, String> viewedAntennas;

	public Main08(String title, String year) {super(title, year, "08");}

	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
			int l = 0;
			while ((line = reader.readLine()) != null) 
			{
				puzzleMatrix[l] = line.toCharArray();
				for (int c = 0; c < puzzleMatrix[l].length; c++)
				{
					char currentChar = puzzleMatrix[l][c];
					if (currentChar != '.')
					{
						List<Pair<Integer>> locations = antennas.get(String.valueOf(currentChar));
						if (locations == null) 
						{
							locations = new ArrayList<Pair<Integer>>();
							locations.add(Pair.<Integer>builder().v1(l).v2(c).build());
						}
						else locations.add(Pair.<Integer>builder().v1(l).v2(c).build());
						antennas.put(String.valueOf(currentChar), locations);
						countAntinode++;
					}
				}
				l++;
			}
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
		}
	}
	
	public void doPart1()
	{
		antennas = new HashedMap<String, List<Pair<Integer>>>();
		retrieveData();
		countAntinode = 0;
		countAntinodeAntenna = 0;
		
		viewedAntennas = new HashMap<Pair<Integer>, String>();
		for (String a : antennas.keySet()) 
		{
			List<Pair<Integer>> p = antennas.get(a);
			for (int i = 0; i < p.size()-1; i++)
				for (int j = i+1; j <p.size(); j ++)
					createAntennas(p.get(i), p.get(j), true);
		}
		part1Result = countAntinode+countAntinodeAntenna;
		setResultPart1(part1Result);
	}

	public void doPart2()
	{
		antennas = new HashedMap<String, List<Pair<Integer>>>();
		countAntinode = 0;
		retrieveData();
		
		countAntinodeAntenna = 0;
		viewedAntennas = new HashMap<Pair<Integer>, String>();
		for (String a : antennas.keySet()) 
		{
			List<Pair<Integer>> p = antennas.get(a);
			for (int i = 0; i < p.size()-1; i++)
				for (int j = i+1; j <p.size(); j++)
					createAntennas(p.get(i), p.get(j), false);
		}
		setResultPart2(countAntinode);
	}
	
	public boolean putAntinode(Pair<Integer> antinode)
	{
		boolean antinodeDone = false;
		// check if antinode can be in matrix
		if (antinode.getV1() >= 0 && antinode.getV1() < puzzleMatrix.length
				&& antinode.getV2() >= 0 && antinode.getV2() < puzzleMatrix.length)
		{
			char currentValue = puzzleMatrix[antinode.getV1().intValue()][antinode.getV2().intValue()];
			if (currentValue != '#') 
			{
				if (currentValue == '.') 
				{
					puzzleMatrix[antinode.getV1().intValue()][antinode.getV2().intValue()] = '#';
					countAntinode++;
				}
				else
				{
					String s = viewedAntennas.get(antinode);
					if (s == null) 
					{
						viewedAntennas.put(antinode, String.valueOf(currentValue));
						countAntinodeAntenna++;
					}
				}
			}
			antinodeDone = true;
		}
		
		return antinodeDone;
	}
	
	public void createAntennas(Pair<Integer> p1, Pair<Integer> p2, boolean part1)
	{
		/*
			xR +0,+1   a1 +0,-1->p1  a2 +0,+1->p2
			xL +0,-1   a1 +0,+1->p1  a2 +0,-1->p2
			Dx +1,+0   a1 -1,+0->p1  a2 +1,+0->p2
			DR +1,+1   a1 -1,-1->p1  a2 +1,+1->p2
			DL +1,-1   a1 -1,+1->p1  a2 +1,-1->p2
			Ux -1,+0   a1 +1,+0->p1  a2 -1,+0->p2
			UL -1,-1   a1 +1,+1->p1  a2 -1,-1->p2
			UR -1,+1   a1 +1,-1->p1  a2 -1,+1->p2
		 */
		String direction = getDirection(p1, p2);
		Pair<Integer> p1Current = p1;
		Pair<Integer> p2Current = p2;
		
		Pair<Integer> an1 = null;
		Pair<Integer> an2 = null;
		
		Integer index1L = Integer.valueOf(Math.abs(p1.getV1()-p2.getV1()));
		Integer index1C = Integer.valueOf(Math.abs(p1.getV2()-p2.getV2()));
		
		Integer index2L = Integer.valueOf(Math.abs(p1.getV1()-p2.getV1()));
		Integer index2C = Integer.valueOf(Math.abs(p1.getV2()-p2.getV2()));

		boolean an1Done = false;
		boolean an2Done = false;
		boolean moreAntennas = true;
		
		if (direction.equals("DR"))
		{
			while (moreAntennas)
			{
				an1 = Pair.<Integer>builder().v1(p1Current.getV1()-index1L).v2(p1Current.getV2()-index1C).build();
				an2 = Pair.<Integer>builder().v1(p2Current.getV1()+index2L).v2(p2Current.getV2()+index2C).build();
				an1Done = putAntinode(an1);
				if (moreAntennas) p1Current = an1;
				
				an2Done = putAntinode(an2);
				if (moreAntennas) p2Current = an2;
				
				if (an1Done || an2Done) moreAntennas = true;
				else moreAntennas = false;
				
				if (part1) moreAntennas = false;
			}
		}
		else if (direction.equals("DL"))
		{
			while (moreAntennas)
			{
				an1 = Pair.<Integer>builder().v1(p1Current.getV1()-index1L).v2(p1Current.getV2()+index1C).build();
				an2 = Pair.<Integer>builder().v1(p2Current.getV1()+index2L).v2(p2Current.getV2()-index2C).build();
				
				an1Done = putAntinode(an1);
				if (moreAntennas) p1Current = an1;
				
				an2Done = putAntinode(an2);
				if (moreAntennas) p2Current = an2;
				
				if (an1Done || an2Done) moreAntennas = true;
				else moreAntennas = false;
				
				if (part1) moreAntennas = false;
			}
		}
	}

	public String getDirection(Pair<Integer> p1, Pair<Integer> p2)
	{
		String direction = "";
		if (p1.getV1().equals(p2.getV1())) direction = "x";
		else if (p1.getV1() > p2.getV1())  direction = "U";
		else if (p1.getV1() < p2.getV1())  direction = "D";

		if (p1.getV2().equals(p2.getV2())) direction += "x";
		else if (p1.getV2() > p2.getV2())  direction += "L";
		else if (p1.getV2() < p2.getV2())  direction += "R";

		return direction;
	}
	
	public Pair<Integer> calculateDistance(Pair<Integer> p1, Pair<Integer> p2)
	{
		String o = new String();
		if (p1.getV1() > p2.getV1()) o = "D";
		else o = "U";
		
		if (p1.getV2() > p2.getV2()) o += "L";
		else o += "R";
		
		return Pair.<Integer>builder().v1(p1.getV1() - p2.getV1()).v2(p1.getV2() - p2.getV2()).o(o).build();
	}
}
