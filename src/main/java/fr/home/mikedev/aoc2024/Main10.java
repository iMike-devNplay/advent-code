package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.Pair;

public class Main10 extends MainDay 
{
	private char[][] puzzleMatrix = new char[49][49];
	//private char[][] puzzleMatrix = new char[8][8];
	private List<Pair<Integer>> trailTracks;
	private Map<Pair<Pair<Integer>>, List<Pair<Integer>>> uniqueTracks;
	Pair<Integer> point0;
	
	public Main10(String title, String year) {super(title, year, "10");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
			int j = 0;
			while ((line = reader.readLine()) != null) 
			{
				puzzleMatrix[j++] = line.toCharArray();
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
		trailTracks = new ArrayList<Pair<Integer>>();
		//log(MatrixUtils.matrixToString(puzzleMatrix));
		uniqueTracks = new HashMap<Pair<Pair<Integer>>, List<Pair<Integer>>>();
		
		initTrack(0, 0, puzzleMatrix.length, puzzleMatrix[0].length);

		//log(uniqueTracks.keySet());
		setResultPart1(uniqueTracks.keySet().size());
	}
	
	public void doPart2()
	{
	    setResultPart2(trailTracks.size());
	}
	
	private void initTrack(int minL, int minC, int maxL, int maxC)
	{
		for (int l = minL; l < maxL; l++)
			for (int c = minC; c < maxC; c++)
				if (puzzleMatrix[l][c] == '0')
				{
					point0 = Pair.<Integer>builder().v1(l).v2(c).o(String.valueOf('0')).build();
					Pair<Integer> start = Pair.<Integer>builder().v1(l).v2(c).o(String.valueOf('0')).build();
					searchTrack(start, '1');
				}
	}
	
	private void searchTrack(Pair<Integer> start, char item)
	{
		//log(start);
		if (item == 'X')
		{
			Pair<Pair<Integer>> track = Pair.<Pair<Integer>>builder().v1(point0).v2(start).build();
			uniqueTracks.put(track, null);
			trailTracks.add(start);
		}
		else
		{
			// Search all 4 possibilities if possible (matrix edges)
			if (start.getV1() == 0 && start.getV2() == 0)
			{
				if (puzzleMatrix[0][1] == item) searchTrack(Pair.<Integer>builder().v1(0).v2(1).o(String.valueOf(item)).build(), getNextChar(item));
				if (puzzleMatrix[1][0] == item) searchTrack(Pair.<Integer>builder().v1(1).v2(0).o(String.valueOf(item)).build(), getNextChar(item));
			}
			else if (start.getV1() == puzzleMatrix.length-1 && start.getV2() == puzzleMatrix[0].length-1)
			{
				if (puzzleMatrix[puzzleMatrix.length-1][puzzleMatrix[0].length-2] == item) searchTrack(Pair.<Integer>builder().v1(puzzleMatrix.length-1).v2(puzzleMatrix[0].length-2).o(String.valueOf(item)).build(), getNextChar(item));
				if (puzzleMatrix[puzzleMatrix.length-2][puzzleMatrix[0].length-1] == item) searchTrack(Pair.<Integer>builder().v1(puzzleMatrix.length-2).v2(puzzleMatrix[0].length-1).o(String.valueOf(item)).build(), getNextChar(item));
			}
			else if (start.getV1() == 0 && start.getV2() == puzzleMatrix[0].length-1)
			{
				if (puzzleMatrix[0][puzzleMatrix[0].length-2] == item) searchTrack(Pair.<Integer>builder().v1(0).v2(puzzleMatrix[0].length-2).o(String.valueOf(item)).build(), getNextChar(item));
				if (puzzleMatrix[1][puzzleMatrix[0].length-1] == item) searchTrack(Pair.<Integer>builder().v1(1).v2(puzzleMatrix[0].length-1).o(String.valueOf(item)).build(), getNextChar(item));
			}
			else if (start.getV1() == puzzleMatrix.length-1 && start.getV2() == 0)
			{
				if (puzzleMatrix[puzzleMatrix.length-1][1] == item) searchTrack(Pair.<Integer>builder().v1(puzzleMatrix.length-1).v2(1).o(String.valueOf(item)).build(), getNextChar(item));
				if (puzzleMatrix[puzzleMatrix.length-2][0] == item) searchTrack(Pair.<Integer>builder().v1(puzzleMatrix.length-2).v2(0).o(String.valueOf(item)).build(), getNextChar(item));
			}
			else if (start.getV1() == 0) 
			{
				if (puzzleMatrix[0][start.getV2()-1] == item)
					searchTrack(Pair.<Integer>builder().v1(0).v2(start.getV2()-1).o(String.valueOf(item)).build(), getNextChar(item));
				if (puzzleMatrix[0][start.getV2()+1] == item)
					searchTrack(Pair.<Integer>builder().v1(0).v2(start.getV2()+1).o(String.valueOf(item)).build(), getNextChar(item));
				if (puzzleMatrix[start.getV1()+1][start.getV2()] == item)
					searchTrack(Pair.<Integer>builder().v1(start.getV1()+1).v2(start.getV2()).o(String.valueOf(item)).build(), getNextChar(item));
			}
			else if (start.getV2() == 0) 
			{
				if (puzzleMatrix[start.getV1()-1][0] == item)
					searchTrack(Pair.<Integer>builder().v1(start.getV1()-1).v2(0).o(String.valueOf(item)).build(), getNextChar(item));
				if (puzzleMatrix[start.getV1()+1][0] == item)
					searchTrack(Pair.<Integer>builder().v1(start.getV1()+1).v2(0).o(String.valueOf(item)).build(), getNextChar(item));
				if (puzzleMatrix[start.getV1()][start.getV2()+1] == item)
					searchTrack(Pair.<Integer>builder().v1(start.getV1()).v2(start.getV2()+1).o(String.valueOf(item)).build(), getNextChar(item));
			}
			else if (start.getV1() == puzzleMatrix.length-1)
			{
				if (puzzleMatrix[puzzleMatrix.length-1][start.getV2()-1] == item)
					searchTrack(Pair.<Integer>builder().v1(puzzleMatrix.length-1).v2(start.getV2()-1).o(String.valueOf(item)).build(), getNextChar(item));
				if (puzzleMatrix[puzzleMatrix.length-1][start.getV2()+1] == item)
					searchTrack(Pair.<Integer>builder().v1(puzzleMatrix.length-1).v2(start.getV2()+1).o(String.valueOf(item)).build(), getNextChar(item));
				if (puzzleMatrix[puzzleMatrix.length-2][start.getV2()] == item)
					searchTrack(Pair.<Integer>builder().v1(puzzleMatrix.length-2).v2(start.getV2()).o(String.valueOf(item)).build(), getNextChar(item));
			}
			else if (start.getV2() == puzzleMatrix[0].length-1)
			{
				if (puzzleMatrix[start.getV1()-1][puzzleMatrix[0].length-1] == item)
					searchTrack(Pair.<Integer>builder().v1(start.getV1()-1).v2(puzzleMatrix[0].length-1).o(String.valueOf(item)).build(), getNextChar(item));
				if (puzzleMatrix[start.getV1()+1][puzzleMatrix[0].length-1] == item)
					searchTrack(Pair.<Integer>builder().v1(start.getV1()+1).v2(puzzleMatrix[0].length-1).o(String.valueOf(item)).build(), getNextChar(item));
				if (puzzleMatrix[start.getV1()][puzzleMatrix[0].length-2] == item)
					searchTrack(Pair.<Integer>builder().v1(start.getV1()).v2(puzzleMatrix[0].length-2).o(String.valueOf(item)).build(), getNextChar(item));
			}
			else
			{
				if (puzzleMatrix[start.getV1()-1][start.getV2()] == item)
					searchTrack(Pair.<Integer>builder().v1(start.getV1()-1).v2(start.getV2()).o(String.valueOf(item)).build(), getNextChar(item));
				if (puzzleMatrix[start.getV1()][start.getV2()-1] == item)
					searchTrack(Pair.<Integer>builder().v1(start.getV1()).v2(start.getV2()-1).o(String.valueOf(item)).build(), getNextChar(item));
				if (puzzleMatrix[start.getV1()][start.getV2()+1] == item)
					searchTrack(Pair.<Integer>builder().v1(start.getV1()).v2(start.getV2()+1).o(String.valueOf(item)).build(), getNextChar(item));
				if (puzzleMatrix[start.getV1()+1][start.getV2()] == item)
					searchTrack(Pair.<Integer>builder().v1(start.getV1()+1).v2(start.getV2()).o(String.valueOf(item)).build(), getNextChar(item));
			}
		}
	}
	
	
	private char getNextChar(char item)
	{
		if (item == '0') item = '1';
		else if (item == '1') item = '2';
		else if (item == '2') item = '3';
		else if (item == '3') item = '4';
		else if (item == '4') item = '5';
		else if (item == '5') item = '6';
		else if (item == '6') item = '7';
		else if (item == '7') item = '8';
		else if (item == '8') item = '9';
		else item = 'X';
		
		return item;
	}
}
