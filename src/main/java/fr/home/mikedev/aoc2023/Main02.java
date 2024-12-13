package fr.home.mikedev.aoc2023;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.home.mikedev.common.MainDay;
import fr.home.mikedev.common.Pair;

public class Main02 extends MainDay 
{
	Map<Integer, List<List<Pair<String>>>> games;
	
	public Main02(String title, String year) {super(title, year, "02");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
			while ((line = reader.readLine()) != null)
			{
				String[] firstSplit = line.split(":");
				Integer gameId = Integer.parseInt(firstSplit[0].substring(5));
				
				String[] revealed = firstSplit[1].split(";");
				
				List<List<Pair<String>>> revealedList = new ArrayList<List<Pair<String>>>();
				for (int i = 0; i < revealed.length; i++)
				{
					String[] color = revealed[i].trim().split(", ");
					List<Pair<String>> colors = new ArrayList<Pair<String>>();
					for (int j = 0; j < color.length; j++)
					{
						String[] value = color[j].split(" ");
						Pair<String> p = Pair.<String>builder().v1(value[0]).v2(value[1]).build();
						colors.add(p);
					}
					revealedList.add(colors);
				}
				games.put(gameId, revealedList);
			}

		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
		}
	}
		
	public void doPart1()
	{
		games = new HashMap<Integer, List<List<Pair<String>>>>();
		retrieveData();

		int gameOk = 0;
		
		for (Integer gameId : games.keySet())
			if (checkGames(games.get(gameId), 12, 13, 14)) gameOk += gameId;
		
		displayResultPart1(gameOk);
	}
	
	public void doPart2()
	{
		//retrieveData();
		
		long gamePower = 0;
		for (Integer gameId : games.keySet())
			gamePower += gamePower(games.get(gameId));
		
		displayResultPart2(gamePower);
	}
	
	boolean checkGames(List<List<Pair<String>>> game, int maxRed, int maxGreen, int maxBlue)
	{
		for (List<Pair<String>> rev : game)
			for (Pair<String> color : rev)
			{
				if (color.getV2().equals("blue") && Integer.parseInt(color.getV1()) > maxBlue) return false;
				else if (color.getV2().equals("red") && Integer.parseInt(color.getV1()) > maxRed) return false;
				else if (color.getV2().equals("green") && Integer.parseInt(color.getV1()) > maxGreen) return false;
			}
		return true;
	}
	
	long gamePower(List<List<Pair<String>>> game)
	{
		int minBlue = 0;
		int minRed = 0;
		int minGreen = 0;
		
		for (List<Pair<String>> rev : game)
			for (Pair<String> color : rev)
			{
				if (color.getV2().equals("blue") && Integer.parseInt(color.getV1()) >= minBlue) minBlue = Integer.parseInt(color.getV1());
				else if (color.getV2().equals("red") && Integer.parseInt(color.getV1()) >= minRed) minRed = Integer.parseInt(color.getV1());
				else if (color.getV2().equals("green") && Integer.parseInt(color.getV1()) >= minGreen) minGreen = Integer.parseInt(color.getV1());
			}
		return minBlue*minRed*minGreen;
	}
}
