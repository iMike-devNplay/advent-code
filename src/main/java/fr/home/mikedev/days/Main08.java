package fr.home.mikedev.days;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.map.HashedMap;

import fr.home.mikedev.days.utils.MatrixUtils;
import fr.home.mikedev.days.utils.Pair;


public class Main08 {

	private final String dataFileName = "data-day08.txt";
	private char[][] puzzleMatrix = new char[50][50];
	//private char[][] puzzleMatrix = new char[12][12];
	private Map<String, List<Pair<Integer>>> antennas;
	int countAntinode;
	int countAntinodeAntenna;
	private Map<Pair<Integer>, String> viewedAntennas;
	
	public static void main(String[] args) throws Exception 
	{
		Main08 m = new Main08();
		//m.doFirst();
		m.doSecond();
	}
	
	public void doFirst() throws Exception
	{
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		String line = null;
		antennas = new HashedMap<String, List<Pair<Integer>>>();
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
				}
			}
			l++;
		}
		reader.close();
		
		System.out.println(MatrixUtils.matrixToString(puzzleMatrix));
		System.out.println(antennas);
		
		countAntinode = 0;
		countAntinodeAntenna = 0;
				
		viewedAntennas = new HashMap<Pair<Integer>, String>();
		for (String a : antennas.keySet()) 
		{
			System.out.println(a);
			List<Pair<Integer>> p = antennas.get(a);
			for (int i = 0; i < p.size()-1; i++)
			{
				for (int j = i+1; j <p.size(); j ++)
				{
					//System.out.println(p.get(i) + "----->" + p.get(j));
					calculateDistance2(p.get(i), p.get(j), true);
				}
			}
		}
		System.out.println(MatrixUtils.matrixToString(puzzleMatrix));
		System.out.println(countAntinode);
		System.out.println(countAntinodeAntenna);
	}
		
	public void doSecond() throws Exception
	{
		BufferedReader reader = Files.newBufferedReader(Paths.get(this.getClass().getClassLoader().getResource(dataFileName).toURI()));
		String line = null;
		antennas = new HashedMap<String, List<Pair<Integer>>>();
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
		reader.close();
		
		System.out.println(MatrixUtils.matrixToString(puzzleMatrix));
		System.out.println(antennas);
		
		//countAntinode = 0;
		countAntinodeAntenna = 0;
				
		viewedAntennas = new HashMap<Pair<Integer>, String>();
		for (String a : antennas.keySet()) 
		{
			System.out.println(a);
			List<Pair<Integer>> p = antennas.get(a);
			for (int i = 0; i < p.size()-1; i++)
			{
				for (int j = i+1; j <p.size(); j ++)
				{
					//System.out.println(p.get(i) + "----->" + p.get(j));
					calculateDistance2(p.get(i), p.get(j), false);
				}
			}
		}
		System.out.println(MatrixUtils.matrixToString(puzzleMatrix));
		System.out.println(countAntinode);
		System.out.println(countAntinodeAntenna);
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
	
	public Pair<Integer> calculateDistance2(Pair<Integer> p1, Pair<Integer> p2, boolean part1)
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
		Pair<Integer> an1 = null;
		Pair<Integer> an2 = null;
		boolean an1Done = false;
		boolean an2Done = false;
		switch(direction)
		{
			case "xR":
				System.out.println("xR=" + p1 + "->" + p2);
				an1 = Pair.<Integer>builder().v1(p1.getV1()).v2(p1.getV2()-Integer.valueOf(Math.abs(p1.getV2()-p2.getV2()))).build();
				an2 = Pair.<Integer>builder().v1(p2.getV1()).v2(p2.getV2()+Integer.valueOf(Math.abs(p1.getV2()-p2.getV2()))).build();
				an1Done = putAntinode(an1);
				an2Done = putAntinode(an2);
				break;
			case "xL":
				System.out.println("xL=" + p1 + "->" + p2);
				an1 = Pair.<Integer>builder().v1(p1.getV1()).v2(p1.getV2()+Integer.valueOf(Math.abs(p1.getV2()-p2.getV2()))).build();
				an2 = Pair.<Integer>builder().v1(p2.getV1()).v2(p2.getV2()-Integer.valueOf(Math.abs(p1.getV2()-p2.getV2()))).build();
				an1Done = putAntinode(an1);
				an2Done = putAntinode(an2);
				break;
			case "Dx":
				System.out.println("Dx=" + p1 + "->" + p2);
				an1 = Pair.<Integer>builder().v1(p1.getV1()-Integer.valueOf(Math.abs(p1.getV1()-p2.getV1()))).v2(p1.getV2()).build();
				an2 = Pair.<Integer>builder().v1(p2.getV1()+Integer.valueOf(Math.abs(p1.getV1()-p2.getV1()))).v2(p2.getV2()).build();
				an1Done = putAntinode(an1);
				an2Done = putAntinode(an2);
				break;
			case "DR":
				System.out.println("DR=" + p1 + "->" + p2);
				Pair<Integer> p1Current = p1;
				Pair<Integer> p2Current = p2;
				boolean moreAntennas = true;
				while (moreAntennas)
				{
					Integer index1L = Integer.valueOf(Math.abs(p1.getV1()-p2.getV1()));
					Integer index1C = Integer.valueOf(Math.abs(p1.getV2()-p2.getV2()));
					an1 = Pair.<Integer>builder().v1(p1Current.getV1()-index1L).v2(p1Current.getV2()-index1C).build();
				
					Integer index2L = Integer.valueOf(Math.abs(p1.getV1()-p2.getV1()));
					Integer index2C = Integer.valueOf(Math.abs(p1.getV2()-p2.getV2()));
					an2 = Pair.<Integer>builder().v1(p2Current.getV1()+index2L).v2(p2Current.getV2()+index2C).build();
					
					an1Done = putAntinode(an1);
					if (moreAntennas) p1Current = an1;
					
					an2Done = putAntinode(an2);
					if (moreAntennas) p2Current = an2;
					
					if (an1Done || an2Done) moreAntennas = true;
					else moreAntennas = false;
					
					if (part1) moreAntennas = false;
				}
				break;
			case "DL":
				System.out.println("DL=" + p1 + "->" + p2);
				p1Current = p1;
				p2Current = p2;
				moreAntennas = true;
				while (moreAntennas)
				{
					Integer index1L = Integer.valueOf(Math.abs(p1.getV1()-p2.getV1()));
					Integer index1C = Integer.valueOf(Math.abs(p1.getV2()-p2.getV2()));
					an1 = Pair.<Integer>builder().v1(p1Current.getV1()-index1L).v2(p1Current.getV2()+index1C).build();
				
					Integer index2L = Integer.valueOf(Math.abs(p1.getV1()-p2.getV1()));
					Integer index2C = Integer.valueOf(Math.abs(p1.getV2()-p2.getV2()));
					an2 = Pair.<Integer>builder().v1(p2Current.getV1()+index2L).v2(p2Current.getV2()-index2C).build();
					
					an1Done = putAntinode(an1);
					if (moreAntennas) p1Current = an1;
					
					an2Done = putAntinode(an2);
					if (moreAntennas) p2Current = an2;
					
					if (an1Done || an2Done) moreAntennas = true;
					else moreAntennas = false;
					
					if (part1) moreAntennas = false;
				}
				
				/*an1 = Pair.<Integer>builder().v1(p1.getV1()-Integer.valueOf(Math.abs(p1.getV1()-p2.getV1()))).v2(p1.getV2()+Integer.valueOf(Math.abs(p1.getV2()-p2.getV2()))).build();
				an2 = Pair.<Integer>builder().v1(p2.getV1()+Integer.valueOf(Math.abs(p1.getV1()-p2.getV1()))).v2(p2.getV2()-Integer.valueOf(Math.abs(p1.getV2()-p2.getV2()))).build();
				an1Done = putAntinode(an1);
				an2Done = putAntinode(an2);*/
				break;
			case "Ux":
				System.out.println("Ux=" + p1 + "->" + p2);
				an1 = Pair.<Integer>builder().v1(p1.getV1()+Integer.valueOf(Math.abs(p1.getV1()-p2.getV1()))).v2(p1.getV2()).build();
				an2 = Pair.<Integer>builder().v1(p2.getV1()-Integer.valueOf(Math.abs(p1.getV1()-p2.getV1()))).v2(p2.getV2()).build();
				an1Done = putAntinode(an1);
				an2Done = putAntinode(an2);
				break;
			case "UR":
				System.out.println("UR=" + p1 + "->" + p2);
				an1 = Pair.<Integer>builder().v1(p1.getV1()+(p1.getV1()-p2.getV1())).v2(p1.getV2()-(p1.getV2()-p2.getV2())).build();
				an2 = Pair.<Integer>builder().v1(p2.getV1()-(p1.getV1()-p2.getV1())).v2(p2.getV2()+(p1.getV2()-p2.getV2())).build();
				an1Done = putAntinode(an1);
				an2Done = putAntinode(an2);
				break;
			case "UL":
				System.out.println("UL=" + p1 + "->" + p2);
				an1 = Pair.<Integer>builder().v1(p1.getV1()+(p1.getV1()-p2.getV1())).v2(p1.getV2()+(p1.getV2()-p2.getV2())).build();
				an2 = Pair.<Integer>builder().v1(p2.getV1()-(p1.getV1()-p2.getV1())).v2(p2.getV2()-(p1.getV2()-p2.getV2())).build();
				an1Done = putAntinode(an1);
				an2Done = putAntinode(an2);
				break;
		}
		
		/*if (!part1)
		{
			if (an1Done && an2Done) return null;
			else if (an1Done && !an2Done) revertAn(an1);
			else if (!an1Done && an2Done) revertAn(an2);
		}*/
		return null;
	}
	
	public void revertAn(Pair<Integer> an)
	{
		System.out.println("Removed => " + an);
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
