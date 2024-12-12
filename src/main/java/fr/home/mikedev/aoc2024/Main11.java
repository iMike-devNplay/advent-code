package fr.home.mikedev.aoc2024;

import java.io.BufferedReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import fr.home.mikedev.common.MainDay;

public class Main11 extends MainDay 
{
	Long[] stones = new Long[8];
	int nbBlinkTodoPart1 = 25;
	int nbBlinkTodoPart2 = 75;
	long countStones;
	Map<Long, Map<Long, Long>> iterations;
	
	public Main11(String title, String year) {super(title, year, "11");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
			line = reader.readLine();
			StringTokenizer st = new StringTokenizer(line);
			for (int i = 0 ; i < stones.length; i++)
				stones[i] = Long.valueOf(st.nextToken());
		}
		catch (Exception ex)
		{
			log("retrieveData() : Unable to retrieve data from file");
			ex.printStackTrace();
		}
	}
		
	public void doPart1()
	{
		retrieveData();
		
		countStones = 0;
		//long before = Instant.now().toEpochMilli();
		for (int i = 0 ; i < stones.length; i++)
			blink(nbBlinkTodoPart1, stones[i]);
		//long after = Instant.now().toEpochMilli();
		//log (after-before);
		displayResultPart1(countStones);
	}
	
	public void doPart2()
	{
		countStones = 0;
		//long before = Instant.now().toEpochMilli();
		
		Map<Long, Long> part2Stones = new HashMap<Long, Long>();
		for (int i = 0 ; i < stones.length; i++)
			part2Stones.put(stones[i], Long.valueOf(1));
		
		iterations = new HashMap<Long, Map<Long, Long>>();
		for (int blink = 0 ; blink < nbBlinkTodoPart2; blink++)
		{
			Map<Long, Long> currentBlinkStones = new HashMap<Long, Long>();
			for (Long stone : part2Stones.keySet())
			{
				Long currAmount = part2Stones.get(stone);
				String stoneStr = String.valueOf(stone);
				if (stoneStr.equals("0")) blinkImproved(Long.valueOf(1), currAmount, currentBlinkStones);
				else if (stoneStr.length() % 2 == 0)
				{
					blinkImproved(Long.parseLong(stoneStr.substring(0, stoneStr.length()/2)), currAmount, currentBlinkStones);
					blinkImproved(Long.parseLong(stoneStr.substring(stoneStr.length()/2)), currAmount, currentBlinkStones);
				}
				else blinkImproved(stone*2024, currAmount, currentBlinkStones);
			}
			part2Stones = currentBlinkStones;
		}
		
		//long after = Instant.now().toEpochMilli();
		//log (after-before);
		
		//log(part2Stones);
		for (Long c : part2Stones.keySet())
			countStones += part2Stones.get(c);
		
		displayResultPart2(countStones);
	}
	
	void blink(int nbBlinks, Long stone)
	{
		if (nbBlinks == 0)countStones++;
		else
		{
			nbBlinks--;
			String stoneString = String.valueOf(stone);
			if (stoneString.equals("0")) blink(nbBlinks, Long.valueOf(1));
			else if (stoneString.length() % 2 == 0) 
			{
				blink(nbBlinks, Long.parseLong(stoneString.substring(0, stoneString.length()/2)));
				blink(nbBlinks, Long.parseLong(stoneString.substring(stoneString.length()/2)));
			}
			else blink(nbBlinks, stone*2024);
		}
	}

	
	void blinkImproved(Long stone, Long amount, Map<Long, Long> stones)
	{
		Long match = stones.get(stone);
		if (match != null) stones.put(stone, match+amount);
		else stones.put(stone,  amount);
	}
}
