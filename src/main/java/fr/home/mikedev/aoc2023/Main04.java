package fr.home.mikedev.aoc2023;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.home.mikedev.common.MainDay;

public class Main04 extends MainDay 
{
    Map<Integer, List<Integer>> winningNumbers;
    Map<Integer, List<Integer>> playerNumbers;
    Map<Integer, Integer>       cardInstances;
    Map<Integer, Integer>       cardWinning;
    
	public Main04(String title, String year) {super(title, year, "04");}
	
	public void retrieveData()
	{
		String line = null;	
		try(BufferedReader reader = this.getReader())
		{
			while((line = reader.readLine()) != null)
			{
			    String[] cardLine = line.split(":");
			    String[] numbers = cardLine[1].split("\\|");
			    
			    String[] w = numbers[0].trim().split("\\s+");
			    String[] p = numbers[1].trim().split("\\s+");
			    
			    List<Integer> wlist = new ArrayList<Integer>();
			    Arrays.asList(w).forEach(i -> wlist.add(Integer.valueOf(i)));
			    winningNumbers.put(Integer.parseInt(cardLine[0].substring(5).trim()), wlist);
			    
			    List<Integer> plist = new ArrayList<Integer>();
                Arrays.asList(p).forEach(i -> plist.add(Integer.valueOf(i)));
                playerNumbers.put(Integer.parseInt(cardLine[0].substring(5).trim()), plist);
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
	    winningNumbers = new HashMap<Integer, List<Integer>>();
	    playerNumbers = new HashMap<Integer, List<Integer>>();
		retrieveData();
		
		long allCardValue = 0;
		for (Integer card : playerNumbers.keySet())
		{
		    int cardValue = 0;
		    for (Integer p : playerNumbers.get(card))
		    {
		        if (winningNumbers.get(card).contains(p))
		            if (cardValue == 0) cardValue = 1;
		            else cardValue *= 2;
		    }
		    allCardValue += cardValue;
		}
		
		setResultPart1(allCardValue); // 21558
	}
	
	public void doPart2()
	{
	    cardInstances = new HashMap<Integer, Integer>();
	    cardWinning = new HashMap<Integer, Integer>();
		//retrieveData();
	    for (Integer currentCardNumber : playerNumbers.keySet())
	        cardInstances.put(currentCardNumber, 1);
	    
	    for (Integer currentCardNumber : playerNumbers.keySet())
	        drawScratchCard(currentCardNumber);
	    
	    int totalCard = 0;
	    for (Integer card : cardInstances.keySet())
	        totalCard += cardInstances.get(card);
	    
	    setResultPart2(totalCard); //10425665
	}
	
	void drawScratchCard(Integer currentCardNumber)
	{
	    int score = getWinningScore(currentCardNumber);
	    if (score > 0)
	    {
            for (int c = currentCardNumber+1 ; c <= currentCardNumber+score; c++)
            {
                Integer updatedCard = cardInstances.get(c);
                cardInstances.put(c, updatedCard+1);
                drawScratchCard(c);
            }
	    }
	}
	
	int getWinningScore(Integer currentCardNumber)
	{
	    Integer winningScore = cardWinning.get(currentCardNumber);
	    if (winningScore == null)
	    {
    	    winningScore = Integer.valueOf(0);
            for (Integer p : playerNumbers.get(currentCardNumber))
                if (winningNumbers.get(currentCardNumber).contains(p))
                    winningScore++;
            cardWinning.put(currentCardNumber, winningScore);
	    }
        return winningScore.intValue();
	}
}
